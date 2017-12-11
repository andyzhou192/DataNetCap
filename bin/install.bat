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
echo %basedir%\software
call start /d "%basedir%\software"  WinPcap_4_1_3.exe
call start /d "%basedir%\software"  JpcapSetup-0.7.exe

echo "�뽫softwareĿ¼�µ�Jpcap.dll��jpcap.jar�ļ��ֱ��Ƶ�jdk��װĿ¼�µ�jre/bin��jre/lib/extĿ¼��."
pause