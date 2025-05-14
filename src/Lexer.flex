%%

%class Lexer
%unicode
%cup
%line
%column

%{

%}

IF        = "if"
NUM       = [0-9]+
ID        = [a-zA-Z_][a-zA-Z0-9_]*
WHITESPACE = [ \t\r\n]+

%%

{WHITESPACE}         { /* ignorar */ }
"if"                 { return sym(Symbol.IF); }
"("                  { return sym(Symbol.PAR_A); }
")"                  { return sym(Symbol.PAR_C); }
";"                  { return sym(Symbol.PUNTOYCOMA); }

"=="                 { return sym(Symbol.OP_EQ); }
"<="                 { return sym(Symbol.OP_LE); }
">="                 { return sym(Symbol.OP_GE); }
"<"                  { return sym(Symbol.OP_LT); }
">"                  { return sym(Symbol.OP_GT); }

"true"               { return sym(Symbol.TRUE); }
"false"              { return sym(Symbol.FALSE); }

{ID}                 { return sym(Symbol.ID, yytext()); }
{NUM}                { return sym(Symbol.NUM, Integer.valueOf(yytext())); }

.                    { System.out.println("Error léxico: " + yytext()); return sym(Symbol.error); }