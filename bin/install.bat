@echo off
echo ************************************************************  
echo *                                                          *  
echo *                    DataNetCap install                    *  
echo *                                                          *  
echo ************************************************************  
echo.  
cd..
echo %cd%
set "basedir=%cd%"
echo "install WinPcap..."
call start /d "%basedir%\software\windows"  WinPcap_4_1_3.exe
::echo "install Jpcap..."
::call start /d "%basedir%\software\windows"  JpcapSetup-0.7.exe
::echo "copy dll..."
::copy /y %basedir%\software\windows\Jpcap.dll %JAVA_HOME%\jre\bin\
pause