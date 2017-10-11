@echo off
setLocal EnableDelayedExpansion
set CLASSPATH="
for /R ./lib %%a in (*.jar) do (
  set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
 
rem echo %CLASSPATH%
rem java -classpath %CLASSPATH% -jar JavaNetCap.jar
rem java -Djava.ext.dirs=./lib -cp . -jar JavaNetCap.jar
java -jar JavaNetCap.jar
 
rem pause
exit