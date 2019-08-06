#192.168.3.109 192.168.3.110  两台work分别进行以下

###1.下载二进制
```bash
https://github.com/goharbor/harbor/releases
tar -xvf harbor-offline-installer-v1.8.2-rc1.tgz
```

###2.修改配置
```bash
cd harbor && vim harbor.yml
修改 hostname: reg.mydomain.com   为当机ip  
hostname: 192.168.3.109
```

###3.启动
```shell script
sh install.sh
```








