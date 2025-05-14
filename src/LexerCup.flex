import java_cup.runtime.Symbol;

%%
%public
%class LexerCup
%unicode
%cup
%line
%column

%{
// Métodos auxiliares que irán dentro de la clase
private Symbol symbol(int type) {
    return new Symbol(type, yyline + 1, yycolumn + 1);
}

private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline + 1, yycolumn + 1, value);
}
%}

IF          = "if"
NUM         = [0-9]+
ID          = [a-zA-Z_][a-zA-Z0-9_]*
WHITESPACE  = [ \t\r\n]+

%%

{WHITESPACE}         { /* ignorar */ }
{IF}                 { return symbol(sym.IF); }
"("                  { return symbol(sym.PAR_A); }
")"                  { return symbol(sym.PAR_C); }
";"                  { return symbol(sym.PUNTOYCOMA); }

"=="                 { return symbol(sym.OP_EQ); }
"<="                 { return symbol(sym.OP_LE); }
">="                 { return symbol(sym.OP_GE); }
"<"                  { return symbol(sym.OP_LT); }
">"                  { return symbol(sym.OP_GT); }

"true"               { return symbol(sym.TRUE); }
"false"              { return symbol(sym.FALSE); }

{ID}                 { return symbol(sym.ID, yytext()); }
{NUM}                { return symbol(sym.NUM, Integer.valueOf(yytext())); }

.                    { return symbol(sym.error, "Caracter no reconocido: " + yytext()); }