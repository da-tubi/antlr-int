grammar Sample;

singleStatement
    : statement ';'* EOF
    ;

statement
    : MODEL optionList # debugModel
    | .*?              # passThrough
    ;

qualifiedName: identifier ('.' identifier)*;

identifier
    : IDENTIFIER		# unquotedIdentifier
    | quotedIdentifier	# quotedIdentifierAlternative
    | nonReserved		# unquotedIdentifier
    ;

quotedIdentifier
    : BACKQUOTED_IDENTIFIER
    ;

nonReserved
    : CREATE | DESC | DESCRIBE | MODEL | MODELS | OPTIONS | REPLACE
    ;

ARRAY: 'ARRAY';
AS: 'AS';
CREATE: 'CREATE';
DESC : 'DESC';
DESCRIBE : 'DESCRIBE';
DROP: 'DROP';
EXISTS: 'EXISTS';
FALSE: 'FALSE';
FLAVOR: 'FLAVOR';
IF: 'IF';
LIKE: 'LIKE';
MODEL: 'MODEL';
MODELS: 'MODELS';
NOT: 'NOT';
OPTIONS: 'OPTIONS';
OR: 'OR';
POSTPROCESSOR: 'POSTPROCESSOR';
PREPROCESSOR: 'PREPROCESSOR';
REPLACE: 'REPLACE';
RETURNS: 'RETURNS';
SHOW: 'SHOW';
STRUCT: 'STRUCT';
TRUE: 'TRUE';
USING: 'USING';

EQ: '=' | '==';

STRING
    : '\'' ( ~('\''|'\\') | ('\\' .) )* '\''
    | '"' ( ~('"'|'\\') | ('\\' .) )* '"'
    ;

IDENTIFIER
    : (LETTER | DIGIT | '_')+
    ;

BACKQUOTED_IDENTIFIER
    : '`' ( ~'`' | '``' )* '`'
    ;

optionList: '(' option (',' option)* ')';

option
    : key=optionKey EQ value=optionValue
    ;

optionKey
    : qualifiedName
    ;

optionValue
    : INTEGER_VALUE
    | FLOATING_VALUE
    | booleanValue
    | STRING
    ;

INTEGER_VALUE
    : MINUS? DIGIT+
    ;

FLOATING_VALUE
    : MINUS? DECIMAL_DIGITS
    ;

booleanValue
    : TRUE | FALSE
    ;

fragment DECIMAL_DIGITS
    : DIGIT+ '.' DIGIT*
    | '.' DIGIT+
    ;

fragment EXPONENT
    : 'E' [+-]? DIGIT+
    ;

fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;

MINUS
    : '-'
    ;
