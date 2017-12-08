# DataNetCap
编译打包命令：mvn assembly:assembly

Windows环境安装：
1、安装Java环境：jdk安装;
2、安装Winpcap: software目录下的WinPcap_4_1_3.exe;
3、安装Jpcap(本地开发时需要设置)： software目录下的JpcapSetup-0.7.exe，安装后将libs目录下的jpcap.jar拷贝到你本地Java目录下的...\jre\lib\ext目录下，再将libs下的Jpcap.dll文件拷贝到...\jre\bin目录下；
4、完成基础安装；

Windows环境使用：
1、解压DataNetCap-bin.zip到本地目录；
2、首次使用请先执行install.bat脚本安装winpcap和jpcap；
3、执行Startup.bat启动应用；
4、在项目的下拉框中选择对应的项目；
5、在Ethernet下的下拉选择框中选择抓包的网卡；
6、在Capture URL下的文本域中输入待抓取的ip或url，多个ip或url用英文逗号隔开，不填写时表示抓取这个网卡的所有HTTP包；
7、点击start按钮开始抓包（点击后下面的状态栏会显示为运行状态）。

Linux环境安装：
1、安装Java环境：jdk安装（1.7版本或以上）
2、执行bin目录下的install.sh脚本，安装libpcap和jpcap及其相关组件（build-essential包）；
3、完成基础安装后，执行startup.sh脚本启动抓包。

Linux环境使用：
1、解压DataNetCap-bin.zip到指定目录；
2、首次使用请先执行install.sh脚本安装；
3、执行Startup.sh启动应用；
4、根据提示从网卡列表中选择抓包的网卡，并输入网卡前面的序号后回车；
5、根据提示从项目列表中选择项目，并输入项目序号后回车；
6、根据提示输入待抓取的ip或url，多个ip或url用英文逗号隔开，不填写时表示抓取这个网卡的所有HTTP包，回车；
6、开始抓包。