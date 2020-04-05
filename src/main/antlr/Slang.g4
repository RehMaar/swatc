grammar Slang;

program
    : block EOF
    ;

block
    : statements+=statement (statements+=statement)*
    ;

statement
    : whileStmt
    | ifStmt
    | assignStmt
    | reassignStmt
    ;

whileStmt
    : WHILEKW LPAR cond=expr RPAR LBRA body=block RBRA
    ;

ifStmt
    : IFKW LPAR cond=expr RPAR LBRA thenC=block RBRA (ELSEKW LBRA elseC=block RBRA)?
    ;

assignStmt
     : ASSIGNKW IDENT ASSNG expr
     ;

reassignStmt
     : IDENT ASSNG expr
     ;

// Better when EQ and NEQ has no assoc at all but I didn't get how to do it with ANTLR.
expr
    : left=expr op=(MULT | DIV) right=expr # BinaryOp
    | left=expr op=(EQ | GT | GE | LT | LE | NEQ) right=expr # BinaryOp
    | left=expr op=(PLUS | MINUS) right=expr # BinaryOp
    | LPAR exprInPar=expr RPAR # ParExpr
    | LITERAL # Literal
    | IDENT # Identifier
    ;

// Lexer part.

LPAR
    : '('
    ;

RPAR
    : ')'
    ;

LBRA
    : '{'
    ;

RBRA
    : '}'
    ;

ASSNG
    : '='
    ;

NEQ
    : '!='
    ;

LE
    : '<='
    ;

LT
    : '<'
    ;

GE
    : '>='
    ;

GT
    : '>'
    ;

EQ
    : '=='
    ;

DIV
    : '/'
    ;

MULT
    : '*'
    ;

MINUS
    : '-'
    ;

PLUS
    : '+'
    ;

WHILEKW
    : 'while'
    ;

IFKW
    : 'if'
    ;

ELSEKW
    : 'else'
    ;

ASSIGNKW
    : 'let'
    ;

IDENT
	: '_'* ([a-zA-Z]) ('_' | [0-9] |  [a-zA-Z])*
	;

LITERAL
    : '-'? ([1-9]) ([0-9])*
    | '-'? '0'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;

NEWLINE
	: [\n]+
	;