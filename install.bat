@echo off
call start /d "%~dp0software"  WinPcap_4_1_3.exe
call start /d "%~dp0software"  JpcapSetup-0.7.exe

echo "请将software目录下的Jpcap.dll和jpcap.jar文件分别复制到jdk安装目录下的jre/bin和jre/lib/ext目录下."
pause