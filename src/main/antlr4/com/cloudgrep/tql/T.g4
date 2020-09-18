grammar T;
//options {output=AST;} // build trees
/** Match things like "call foo;" */
r : 'call' ID ';' ;//  {System.out.println("invoke "+$ID.text);} ;
ID: 'a'..'z' + ;
WS: (' ' |'\n' |'\r' )+ -> channel(HIDDEN) ; // ignore whitespace