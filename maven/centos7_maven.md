###1.下载
```shell script
https://maven.apache.org/download.cgi
```

###2.rz解压
```shell script
cd /usr/local
rz 
tar -zxvf apache-maven-3.6.1-bin.tar.gz

mv apache-maven-3.6.1  maven
```

###3.环境变量
```shell script
vim /etc/profile

//加入

export M2_HOME=/usr/local/maven
export PATH=$PATH:$M2_HOME/bin

//立即生效
source /etc/profile

mvn -v
```