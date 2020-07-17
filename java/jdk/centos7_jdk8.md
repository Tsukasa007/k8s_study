###1.下载jdk targz
```http request
http://www.oracle.com/technetwork/java/javase/downloads/index.html
```

###2.rz解压 
```shell script
cd /usr/local
rz 
tar -zxvf jdk-8u221-linux-x64.tar.gz
mv jdk1.8.0_221 jdk
```

###3.配置JDK环境变量
```shell script
vim /etc/profile

//加入

export JAVA_HOME=/usr/local/jdk
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin


//生效
source /etc/profile

//验证
java -version
```