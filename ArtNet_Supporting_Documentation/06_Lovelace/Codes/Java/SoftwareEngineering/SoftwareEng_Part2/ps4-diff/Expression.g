//The Expression grammar
@skip whitespace{
    root ::= sum;
    sum ::= product ('+' product)*;
    product ::= primitive ('*' primitive)*;
    primitive ::= term | '(' sum ')';
}
whitespace ::= [ \t\r\n];
term ::= [a-z|A-Z]+ |  [0-9]+ ('.' [0-9]+)?;