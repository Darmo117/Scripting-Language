@echo off

set fname=script_lang
set package=net.darmo_creations.scripting
set dir=src\main\java\%package:.=\%\
set symbols=Tokens

java -jar libs\jflex-1.6.1.jar scanner_grammar\%fname%.jflex -d %dir%
java -jar libs\java-cup-11a.jar -package %package% -symbols %symbols% -parser Parser scanner_grammar\%fname%.cup
rem Déplacement des fichiers Parser.java et Tokens.java
move *.java %dir%
rem Suppression du fichier de sauvegarde Lexer.java~
del %dir%*.java~

cd %dir%

set lexer_file=Lexer.java

rem Ajout du package au début du lexer
echo package %package%;> tmp.txt
type %lexer_file% >> tmp.txt
del %lexer_file%
ren tmp.txt %lexer_file%

rem Remplacement de la classe sym par Tokens lors de l'appel à sym.EOF
set search=sym.
set replace=%symbols%.

for /f "delims=" %%i in ('type "%lexer_file%" ^& break ^> "%lexer_file%" ') do (
  set line=%%i
  setlocal enabledelayedexpansion
  >>"%lexer_file%" echo(!line:%search%=%replace%!
  endlocal
)
