# DataNetCap
1、安装Java环境：jdk安装;
2、安装Winpcap: software目录下的WinPcap_4_1_3.exe;
3、安装Jpcap： software目录下的JpcapSetup-0.7.exe，安装后将libs目录下的jpcap.jar拷贝到你本地Java目录下的...\jre\lib\ext目录下，再将libs下的Jpcap.dll文件拷贝到...\jre\bin目录下；
4、完成基础安装；

编译打包命令：mvn assembly:assembly