#!/bin/sh
echo "************************************************************"
echo "*                                                          *"
echo "*                    DataNetCap install                    *"
echo "*                                                          *"
echo "************************************************************"
 
#install build-essential
yum groupinstall "Development Tools"
yum install -y gcc g++ kernel-devel

#compile and install libpcap
#wget ftp://ftp.gnu.org/gnu/m4/m4-latest.tar.gz  #解压后文件夹名称为m4-1.4.17
tar zxvf ../software/linux/m4-latest.tar.gz -C ./
cd m4-1.4.17
sudo ./configure
sudo make
sudo make install
cd ..

#wget ftp://ftp.gnu.org/gnu/bison/bison-3.0.tar.xz
tar Jxvf ../software/linux/bison-3.0.tar.xz -C ./
cd bison-3.0
sudo ./configure
sudo make
sudo make install
cd ..

#wget https://github.com/westes/flex/files/981163/flex-2.6.4.tar.gz
tar zxvf ../software/linux/flex-2.6.4.tar.gz -C ./
cd flex-2.6.4
sudo ./configure
sudo make
sudo make install
cd ..

#wget http://www.tcpdump.org/release/libpcap-1.8.1.tar.gz
tar zxvf ../software/linux/libpcap-1.8.1.tar.gz -C ./
cd libpcap-1.8.1
sudo ./configure
sudo make
sudo make install
sudo ln -s /usr/local/lib/libpcap.so.1 /usr/lib/libpcap.so.1
cd ..

#compile and install jpcap
#wget https://github.com/mgodave/Jpcap/archive/master.zip
unzip ../software/linux/master.zip -d ./ #解压后的文件夹名称为Jpcap-master
cd Jpcap-master/src/main/c
sudo make
#将生成的libjpcap.so文件拷贝到$JAVA_HOME/jre/lib/<arch>目录中。其中，arch对应的是计算机架构，如i386、sparc、amd64等等
cp libjpcap.so $JAVA_HOME/jre/lib/amd64
#将Jpcap-master/lib/jpcap.jar拷贝到$JAVA_HOME/jre/lib/ext/目录下
cd ../../../lib
cp jpcap.jar $JAVA_HOME/jre/lib/ext
#rm -rf master.zip

rm -rf m4-1.4.17 bison-3.0 flex-2.6.4 libpcap-1.8.1 
