grammar Expression;
import Configuration;

// Root rule
root : expr EOF;

// Expressions
expr : term ('+' term)* ; // Addition/Subtraction
term : factor ('*' factor)* ; // Multiplication
factor : NUMBER | VARIABLE | '(' expr ')' ; // Numbers, Variables, Parentheses

// Terminals
NUMBER : [0-9]+ ('.' [0-9]+)? ; // Integer or Decimal
VARIABLE : [a-zA-Z]+ ; // Variable names

// Whitespace
SPACES : [ \t\r\n]+ -> skip ;
