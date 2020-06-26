//The IntegerExpression grammar
@skip whitespace{
    root ::= sum;
    sum ::= product ('+' product)*;
    product ::= primitive ('*' primitive)*;
    primitive ::= number | '(' sum ')';
}
whitespace ::= [ \t\r\n];
number ::= [0-9]+ ('.' [0-9]+)?;
