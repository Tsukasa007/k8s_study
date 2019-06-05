##Centos7 更换阿里源

*  备份本地yum源
```bash
cp /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo_bak
```
* 获取阿里yum源配置文件
```bash
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
```

* 更新cache
```bash
yum makecache
```

* 查看
```bash
yum -y update
```
