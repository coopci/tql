grammar TQL;
//options {output=AST;} // build trees
/** Match things like "call foo;" */
and: AND;
or: OR;

fieldname: LITERAL;

num: NUM;

kvgrep: fieldname  ':' STRING; //  grep against a field.
kvnumequal: fieldname  '='  num; //  Cast the field to a number then test equality between the field value and the given constant.
kvstrequal: fieldname  ':='  STRING; // Test equality between the field value and the given string.

kvatom: kvgrep | kvnumequal | kvstrequal;
kw: LITERAL | STRING; // grep over the whole text.
atom: kw | kvatom;




conjuction: atom
    | conjuction and atom; // and vs AND: and generated code is easier to use.



disjuction: conjuction
    | disjuction or conjuction;

query: disjuction;







//////////////////////////////////////////////////////////////////////



parsejson: 'parsejson';
 parseregex:  'parseregex';  // find field(s) with regular expression.
cut: 'cut';       // cut raw text by given delimiter and name the resulting fields
parsekv: 'pareskv'; // similar to cut, but pick the fields' name from raw text.

parse: parsejson | parsekv | parseregex | cut;
pipe: '|';

pipeline: query (pipe (parse|query))* ;

//////////////////////////////////////////////////////////////////////
AND: 'AND';
OR: 'OR';
NUM  : DIGIT+;
LITERAL:  PRIINTABLE+;
WS: (' ' |'\n' |'\r' )+ -> channel(HIDDEN) ; // ignore whitespace

STRING: '"' (ESC|.)*? '"' ;


fragment
ESC : '\\"' | '\\\\' ; // 2-char sequences \" and \\
PRIINTABLE: ~[' ''\n''\r''"'];   //anything but whitespace and '"'

fragment DECIMAL_INTEGER   : NON_ZERO_DIGIT DIGIT* | '0'+ ;

fragment NON_ZERO_DIGIT   : [1-9]    ;
/// digit          ::=  "0"..."9"
fragment DIGIT : [0-9];