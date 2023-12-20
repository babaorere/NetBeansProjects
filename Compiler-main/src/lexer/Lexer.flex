import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column

%{
// Función auxiliar para crear tokens
private Token token(String lexeme, String lexicalComp, int line, int column) {
    return new Token(lexeme, lexicalComp, line + 1, column + 1);
}
%}

// Definición para Letra
/*:jletter: "letras" según la especificación Unicode.*/
Letra = [A-Za-z]

// Definición para los dígitos
Digito = [0-9]

// Definición para los números
Entero = 0 | [1-9] | [1-9][0-9]*
    
// Definición para identificador
Identificador = (_*{Letra})+(_*{Digito}*{Letra}*)*

// Definición para comentarios de bloque
CommentBlock = "/*"[^*]*"*/"

// Definición para comentarios de una línea
CommentLine = "//" [^\r\n]* [\r\n]

// Definición para espacios en blanco
WhiteSpace = [ \t\r\n\f]

%%

{WhiteSpace} { /* Ignorar espacios en blanco */ }
{CommentBlock} { /* Ignorar comentarios de bloque */ }
{CommentLine} { /* Ignorar comentarios de una línea */ }

// Reglas que utilizan las macros definidas
{Entero} { return token(yytext(), "NUMERO", yyline, yycolumn); }

'[^'\n]*'       { return new Token(yytext(), "CADENA", yyline, yycolumn); }
\"[^\"\n]*\"    { return new Token(yytext(), "CADENA", yyline, yycolumn); }


/*Tipo de datos*/
String {return token(yytext(), "STRING", yyline, yycolumn);}
int {return token(yytext(), "INT", yyline, yycolumn);}
float {return token(yytext(), "FLOAT", yyline, yycolumn);}
char {return token(yytext(), "CHAR", yyline, yycolumn);}
boolean {return token(yytext(), "BOOLEAN", yyline, yycolumn);}

/*Operadores Aritmeticos*/
"+" {return token(yytext(), "SUMA", yyline, yycolumn);}
"*" {return token(yytext(), "MULTI", yyline, yycolumn);}
"/" {return token(yytext(), "DIV", yyline, yycolumn);}
"-" {return token(yytext(), "RESTA", yyline, yycolumn);}
"%" {return token(yytext(), "RESIDUO", yyline, yycolumn); }
"++" {return token(yytext(), "INCREMENTO", yyline, yycolumn); }
"--" {return token(yytext(), "DECREMENTO", yyline, yycolumn); }

/*Operadores de comparacion*/
"==" {return token(yytext(), "OPLOG", yyline, yycolumn); }
"!=" {return token(yytext(), "OPLOG", yyline, yycolumn); }
"===" {return token(yytext(), "OPLOG", yyline, yycolumn); }
"!==" {return token(yytext(), "OPLOG", yyline, yycolumn); }
">" {return token(yytext(), "OPLOG", yyline, yycolumn); }
"<" {return token(yytext(), "OPLOG", yyline, yycolumn); }
">=" {return token(yytext(), "OPLOG", yyline, yycolumn); }
"<=" {return token(yytext(), "OPLOG", yyline, yycolumn); }

// Asignación
"=" { return token(yytext(), "ASIGNACION", yyline, yycolumn); }

/*Operadores logicos*/
"&&" {return token(yytext(), "AND", yyline, yycolumn); }
"||" {return token(yytext(), "OR", yyline, yycolumn); }
"!" {return token(yytext(), "NOT", yyline, yycolumn); }

// Palabras clave y condicionales
"var" { return token(yytext(), "VAR", yyline, yycolumn); }
"const" { return token(yytext(), "CONST", yyline, yycolumn); }
"let" { return token(yytext(), "LET", yyline, yycolumn); }
"true" { return token(yytext(), "VAL_LOG", yyline, yycolumn); }
"false" { return token(yytext(), "VAL_LOG", yyline, yycolumn); }
"if" { return token(yytext(), "IF", yyline, yycolumn); }
"else" { return token(yytext(), "ELSE", yyline, yycolumn); }
"switch" { return token(yytext(), "SWITCH", yyline, yycolumn); }
"for" { return token(yytext(), "FOR", yyline, yycolumn); }
"while" { return token(yytext(), "WHILE", yyline, yycolumn); }
"class" { return token(yytext(), "CLASS", yyline, yycolumn); }

";" { return token(yytext(), "PUNTO_COMA", yyline, yycolumn); }
"," { return token(yytext(), "COMA", yyline, yycolumn); }
"." { return token(yytext(), "PUNTO", yyline, yycolumn); }

/*Operadores de agrupacion*/
"(" {return token(yytext(), "PAREN_A", yyline, yycolumn);}
")" {return token(yytext(), "PAREN_C", yyline, yycolumn);}
"{" {return token(yytext(), "LLAVE_A", yyline, yycolumn);}
"}" {return token(yytext(), "LLAVE_C", yyline, yycolumn);}
"[" {return token(yytext(), "CORC_A", yyline, yycolumn); }
"]" {return token(yytext(), "CORC_C", yyline, yycolumn); }

// Identificadores y errores
{Identificador} { return token(yytext(), "IDENTIFICADOR", yyline, yycolumn); }
. { return token(yytext(), "ERROR", yyline, yycolumn); }

