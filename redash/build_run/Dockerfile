#指定基础镜像（Redash的基础镜像）
FROM redash/base:latest

#更新源
RUN  sed -i s@/archive.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list
RUN  apt-get clean
RUN apt-get update

#libaio1 libaio-dev包的作用是为了支持同步I/O。对于数据库之类的系统特别重要，因此在Ubuntu在上安张数据库软件，比如oracle之前，需要安装该包。
RUN apt-get install -y libaio1 libaio-dev unixodbc wget alien locales

#复制oracle客户端安装文件
COPY *.rpm ./

#将rpm包转换为deb包
RUN alien -c *.rpm

#安装deb包
RUN dpkg -i *.deb
