@echo off
::启用命令扩展功能
SETLOCAL ENABLEEXTENSIONS
if not defined JAVA_HOME (
	echo The machine does not install Java or does not set the Java environment variable. &goto end
) 
echo %JAVA_HOME%
cd ..
echo %cd%
::启用变量延迟
SETLOCAL ENABLEDELAYEDEXPANSION
for %%i in (%cd%\lib\*.jar) do  set CLASSPATH=!CLASSPATH!;%%i
echo !CLASSPATH!
set path==%path%;%cd%\lib
java -classpath %CLASSPATH% com.Main
::java -classpath !CLASSPATH! -jar %cd%\lib\DataNetCap.jar
:end
pause
::exit