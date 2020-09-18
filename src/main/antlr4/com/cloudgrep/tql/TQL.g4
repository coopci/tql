grammar TQL;
//options {output=AST;} // build trees
/** Match things like "call foo;" */
and: AND;
or: OR;
// junction: AND | OR;
atom: LITERAL | STRING;

conjuction: atom
    | conjuction and atom; // and vs AND generated code is easier to use.

disjuction: (atom | conjuction)
    | disjuction or (atom|conjuction);

query: disjuction;


//////////////////////////////////////////////////////////////////////
parsejson: 'parsejson';
parsekv: 'parescsv';
parseregex:  'parseregex';
cut: 'cut';

parse: parsejson | parsekv | parseregex | cut;
pipe: '|';

pipeline: query (pipe (parse|query))* ;

//////////////////////////////////////////////////////////////////////
AND: 'AND';
OR: 'OR';
LITERAL:  PRIINTABLE+;
WS: (' ' |'\n' |'\r' )+ -> channel(HIDDEN) ; // ignore whitespace

STRING: '"' (ESC|.)*? '"' ;
fragment
ESC : '\\"' | '\\\\' ; // 2-char sequences \" and \\
PRIINTABLE: ~[' ''\n''\r'];   //anything but whitespace