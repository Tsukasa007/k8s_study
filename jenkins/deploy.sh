#!/usr/bin/env bash
# 初始化核心参数
# jar包名称
echo 'run container '${app_name}' success!'
app_name=${app_name}
app_file_name=${app_file_name}
# 发布版本
version='latest'
# harbor镜像仓库域名地址
harbor_registry='192.168.0.112:8888'
# 镜像仓库
image_prefix=${image_prefix}
# maven构建版本
maven_version=${maven_version}
# 初始端口
INIT_EXPOSE=${INIT_EXPOSE}
# 对外服务端口
EXPOSE=${EXPOSE}
# jenkins任务构建原路径
jenkins_jar_path='/var/jenkins_home/workspace'
# 构建镜像路径
projects_path='/var/jenkins_home/docker_images/'
Mounts=${Mounts}
data=`date '+%Y%m%d%H%M%S'`
# 停止删除容器
{
sudo docker stop ${app_name}
echo 'stop container '${app_name}' success!'
sudo docker rm ${app_name}
echo 'delete container '${app_name}' success!'

} || {
echo 'stop container '${app_name}' Error!!!!'
}
# 复制jar包到指定目录
sudo mkdir -p ${projects_path}${app_name}
sudo cp ${jenkins_jar_path}/${app_file_name}/target/${app_name}-${maven_version}.jar  ${projects_path}${app_name}/
sudo cp ${jenkins_jar_path}/${app_file_name}/Dockerfile ${projects_path}${app_name}/

# 构建推送镜像
sudo docker login --username=admin --password=harbor http://${harbor_registry}

sudo docker build -t ${image_prefix}/${app_name}:${maven_version} -f ${projects_path}${app_name}/Dockerfile ${projects_path}${app_name}/.

sudo docker tag ${image_prefix}/${app_name}:${maven_version} ${harbor_registry}/${image_prefix}/${app_name}:${version}
sudo docker push ${harbor_registry}/${image_prefix}/${app_name}:${version}

sudo docker tag ${image_prefix}/${app_name}:${maven_version} ${harbor_registry}/${image_prefix}/${app_name}:${data}
sudo docker push ${harbor_registry}/${image_prefix}/${app_name}:${data}

sudo docker rmi `sudo docker images|grep none | awk '{print $3}'`
sudo docker rmi ${image_prefix}/${app_name}:${maven_version}
# 运行容器
echo 'stop run shell:  'docker run -p ${EXPOSE}:${INIT_EXPOSE} --name ${app_name} ${Mounts}  -v /etc/localtime:/etc/localtime -v ${projects_path}${app_name}/logs:/var/logs -dit ${harbor_registry}/${image_prefix}/${app_name}:${version}''

sudo docker run -p ${EXPOSE}:${INIT_EXPOSE} --name ${app_name}  ${Mounts}  -v /etc/localtime:/etc/localtime -v ${projects_path}${app_name}/logs:/var/logs -dit ${harbor_registry}/${image_prefix}/${app_name}:${version}
echo 'run container '${app_name}' success!'

sudo rm -rf ${projects_path}${app_name}

