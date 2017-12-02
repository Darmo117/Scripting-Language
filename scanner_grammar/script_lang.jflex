import java_cup.runtime.*;

%%

%class Lexer
%unicode
%cup
%line
%column

%{
  private StringBuilder sb = new StringBuilder();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

Identifier = [a-zA-Z][a-zA-Z0-9_\$]*
// TODO add exponent notation (e.g.: 1e6)
Number = [0-9]+(\.[0-9]*)?
WhiteSpace = [ \t\n\r]+

%state STRING_DOUBLE
%state STRING_SIMPLE

%%

<YYINITIAL> {
  "let"         { return symbol(Tokens.KW_LET); }
  "function"    { return symbol(Tokens.KW_FUNCTION); }
  "return"      { return symbol(Tokens.KW_RETURN); }
  "if"          { return symbol(Tokens.KW_IF); }
  "else"        { return symbol(Tokens.KW_ELSE); }
  "while"       { return symbol(Tokens.KW_WHILE); }
  "until"       { return symbol(Tokens.KW_UNTIL); }
  "for"         { return symbol(Tokens.KW_FOR); }
  "break"       { return symbol(Tokens.KW_BREAK); }
  "continue"    { return symbol(Tokens.KW_CONTINUE); }
  "null"        { return symbol(Tokens.KW_NULL); }
  ";"           { return symbol(Tokens.SEMI_COLON); }
  ":"           { return symbol(Tokens.COLON); }
  ","           { return symbol(Tokens.COMMA); }
  "("           { return symbol(Tokens.LPAREN); }
  ")"           { return symbol(Tokens.RPAREN); }
  "["           { return symbol(Tokens.LBRACKET); }
  "]"           { return symbol(Tokens.RBRACKET); }
  "{"           { return symbol(Tokens.LCURLY); }
  "}"           { return symbol(Tokens.RCURLY); }
  "+="          { return symbol(Tokens.PLUS_EQ); }
  "-="          { return symbol(Tokens.MINUS_EQ); }
  "*="          { return symbol(Tokens.STAR_EQ); }
  "/="          { return symbol(Tokens.SLASH_EQ); }
  "%="          { return symbol(Tokens.PERCENT_EQ); }
  "&="          { return symbol(Tokens.AND_EQ); }
  "|="          { return symbol(Tokens.PIPE_EQ); }
  "~="          { return symbol(Tokens.TILDE_EQ); }
  "^="          { return symbol(Tokens.CIRCUM_EQ); }
  "=="          { return symbol(Tokens.DOUBLE_EQ); }
  "!="          { return symbol(Tokens.EXCL_EQ); }
  "="           { return symbol(Tokens.EQ); }
  "+"           { return symbol(Tokens.PLUS); }
  "-"           { return symbol(Tokens.MINUS); }
  "**"          { return symbol(Tokens.DOUBLE_STAR); }
  "*"           { return symbol(Tokens.STAR); }
  "/"           { return symbol(Tokens.SLASH); }
  "%"           { return symbol(Tokens.PERCENT); }
  "!"           { return symbol(Tokens.EXCL); }
  "&&"          { return symbol(Tokens.DOUBLE_AND); }
  "||"          { return symbol(Tokens.DOUBLE_PIPE); }
  "&"           { return symbol(Tokens.AND); }
  "|"           { return symbol(Tokens.PIPE); }
  "~"           { return symbol(Tokens.TILDE); }
  "^"           { return symbol(Tokens.CIRCUMFLEX); }
  "<="          { return symbol(Tokens.LANGLE_EQ); }
  ">="          { return symbol(Tokens.RANGLE_EQ); }
  "<"           { return symbol(Tokens.LANGLE); }
  ">"           { return symbol(Tokens.RANGLE); }
  \" /*"*/      { sb.setLength(0); yybegin(STRING_DOUBLE); }
  \' /*'*/      { sb.setLength(0); yybegin(STRING_SIMPLE); }
  {Number}      { return symbol(Tokens.NUMBER, Double.parseDouble(yytext())); }
  {Identifier}  { return symbol(Tokens.ID, yytext()); }
  {WhiteSpace}  { /* ignored */ }
}

<STRING_DOUBLE> {
  \"          /*"*/ { yybegin(YYINITIAL); return symbol(Tokens.STRING, sb.toString()); }
  [^\n\r\"\\] /*"*/ { sb.append(yytext()); }
  \\\"        /*"*/ { sb.append("\""); }
  \\\\              { sb.append("\\"); }
  \\t               { sb.append("\t"); }
  \\n               { sb.append("\n"); }
  \\r               { sb.append("\r"); }
}

<STRING_SIMPLE> {
  \'          /*'*/ { yybegin(YYINITIAL); return symbol(Tokens.STRING, sb.toString()); }
  [^\n\r\'\\] /*'*/ { sb.append(yytext()); }
  \\\'        /*'*/ { sb.append("'"); }
  \\\\              { sb.append("\\"); }
  \\t               { sb.append("\t"); }
  \\n               { sb.append("\n"); }
  \\r               { sb.append("\r"); }
}

[^]           { throw new RuntimeException("Illegal character '" + yytext() + "'"); }
