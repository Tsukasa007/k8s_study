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

###3.启动Harbor12345
```shell script
sh install.sh
```



#中转接点 192.168.3.108

###1.创建nginx.conf
```shell script
vim nginx.conf
```
```text
user nginx;
worker_processes 1;

error_log /var/log/nginx/error.log warn;

pid /var/run/nginx.pid;

events {
    worker_connections 1024;
}

stream {

    upstream hub {
        server  192.168.3.109:80;
    }
    server {
        listen 80;
        proxy_pass hub;
        proxy_timeout 300s;
        proxy_connect_timeout 5s;
    }
}
```


###2.创建启动脚本
```shell script
vim restart.sh
```
```shell script
#!/bin/bash
docker stop harbornginx

docker rm harbornginx

docker run -idt --net=host --name harbornginx -v /usr/local/src/nginx/nginx.conf:/etc/nginx/nginx.conf nginx:1.17.2
```



###3. 添加允许 https(所有机都要)
```shell script
vim /etc/docker/daemon.json
```

```json
"insecure-registries":[
    "hub.tsukasa.pro"
],
```

###4. 添加hosts(所有机都要) 地址为部署nginx负载的ip
```shell script
vim /etc/hosts
192.168.3.108 hub.tsukasa.pro
```


###5.设置相互转发
```text
即 109  <->  110
```

