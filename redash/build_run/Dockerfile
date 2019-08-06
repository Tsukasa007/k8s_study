FROM node:10 as frontend-builder

WORKDIR /frontend
COPY package.json package-lock.json /frontend/
RUN npm install  --registry=https://registry.npm.taobao.org

COPY client /frontend/client
COPY webpack.config.js /frontend/
RUN npm run build

FROM redash/base:debian

RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

# Controls whether to install extra dependencies needed for all data sources.
ARG skip_ds_deps

# We first copy only the requirements file, to avoid rebuilding on every file
# change.
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


COPY requirements.txt requirements_bundles.txt requirements_dev.txt requirements_all_ds.txt ./
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt -r requirements_dev.txt
RUN if [ "x$skip_ds_deps" = "x" ] ; then pip install -i https://pypi.tuna.tsinghua.edu.cn/simple  -r requirements_all_ds.txt ; else echo "Skipping pip install  -i https://pypi.tuna.tsinghua.edu.cn/simple  -r requirements_all_ds.txt " ; fi

COPY . /app
COPY --from=frontend-builder /frontend/client/dist /app/client/dist
RUN chown -R redash /app
USER redash

ENTRYPOINT ["/app/bin/docker-entrypoint"]
CMD ["server"]
