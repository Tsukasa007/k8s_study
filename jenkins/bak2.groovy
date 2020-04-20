#!groovy

def createVersion() {
// 定义一个版本号作为当次构建的版本，输出结果 20191210175842_69
    return new Date().format('yyyyMMddHHmmss')
}

pipeline {
    agent any

    environment {
        NACOS_IP = "192.168.0.112"
        NACOS_PORT = "8848"
        NACOS_ID = "f90545fb-ec0a-43b4-a060-97daa8bf1b95"
        DOCKER_BUILD_PATH = "/home/jenkins_home/docker_build"
        REPOSITORY = "http://192.168.0.112:10080/Yangjiahui/mb-admin-cloud.git" //项目地址
        PROJECT = "mb"                 //项目名，用于镜像区分项目
        // MO = "authority"
        MODULE_SUFFIX = "${MO.split(';')[0]}-server"               //镜像模块名  去除前缀
        PACKAGE = "${PROJECT}-${MO.split(';')[0]}"
        PORT = "${MO.split(';')[1]}"                         //项目端口
        HARBOR_REGISTRY = "192.168.0.112:8888"
//        TIME = createVersion()
        TIME = 'latest'

        SSH_HOST = "192.168.0.112"    //远程IP  （）
        remoteDirectory = "/jenkins_home/target_jar/${PROJECT}/${MODULE_SUFFIX}/"  //远程目录
    }
    stages {

        stage('git 获取代码') {
            steps {
                echo " 从git上拉取代码 :${REPOSITORY}"

                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'db03885c-ed03-403e-ad29-ef9229acf69c', url: 'http://192.168.0.112:10080/Yangjiahui/mb-admin-cloud.git']]])
            }
        }

        stage('mvn 构建指定模块') {
            steps {
                echo "maven 构建"
                dir("${PACKAGE}") {
                    sh "pwd"
                    sh "mvn clean install -T8 -Dmaven.test.skip=true"
                }
            }
        }
        stage('复制jar,准备docker build') {
            steps {
                dir("${PACKAGE}") {
                    echo "复制jar"
                    sh 'pwd'
                    sh 'cd ./${PROJECT}-${MODULE_SUFFIX} && cp  ./target/${PROJECT}-${MODULE_SUFFIX}.jar .'
                }
            }
        }
        stage('准备docker部署') {
            steps {
                echo "准备docker部署"
                dir("${PACKAGE}/${PROJECT}-${MODULE_SUFFIX}") {
                    sh 'pwd'
                    sh 'sudo docker build --build-arg JAR_FILE=target/${PROJECT}-${MODULE_SUFFIX}.jar -t ${PROJECT}/${MODULE_SUFFIX}:${TIME} .'
                    sh 'sudo docker tag ${PROJECT}/${MODULE_SUFFIX}:${TIME} ${HARBOR_REGISTRY}/${PROJECT}/${MODULE_SUFFIX}:${TIME}'
                    sh 'sudo docker push ${HARBOR_REGISTRY}/${PROJECT}/${MODULE_SUFFIX}:${TIME}'
                    sh 'sudo docker rmi "sudo docker images | grep none | awk `{print $3}`" 1>/dev/null 2>&1 | exit 0'
                    sh 'sudo docker rmi ${PROJECT}/${MODULE_SUFFIX}:${TIME} 1>/dev/null 2>&1 | exit 0'
                    sh 'sudo docker stop ${PROJECT}-${MODULE_SUFFIX} 1>/dev/null 2>&1 | exit 0'
                    sh 'sudo docker rm ${PROJECT}-${MODULE_SUFFIX} 1>/dev/null 2>&1 | exit 0'
                }
            }
        }
        stage('docker 启动') {
            steps {
                echo "docker 启动"
                dir("${PACKAGE}") {
                    sh 'sudo docker run -idt --name ${PROJECT}-${MODULE_SUFFIX} --restart=always -m 512M \\\n' +
                            '        -v /home/${PROJECT}-${MODULE_SUFFIX}/:/data/projects \\\n' +
                            '        -p $PORT:$PORT \\\n' +
                            '        -e NACOS_IP=${NACOS_IP} \\\n' +
                            '        -e NACOS_PORT=${NACOS_PORT} \\\n' +
                            '        -e NACOS_ID=${NACOS_ID} \\\n' +
                            '        ${HARBOR_REGISTRY}/${PROJECT}/${MODULE_SUFFIX}:${TIME} '
                }
            }
        }
//        stage('远程推送部署') {
//            steps {
//                echo "远程推送部署"
//                dir("${PACKAGE}") {
//                    sh 'pwd'
//                    sshPublisher(publishers: [sshPublisherDesc(configName: "${SSH_HOST}", transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "bash -x -s < ${DOCKER_BUILD_PATH}/server_build_deploy_docker.sh ${PROJECT} ${MODULE_SUFFIX} ${PORT}", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: "${remoteDirectory}", remoteDirectorySDF: false, removePrefix: "${PROJECT}-${MODULE_SUFFIX}/", sourceFiles: "${PROJECT}-${MODULE_SUFFIX}/${PROJECT}-${MODULE_SUFFIX}.tgz,${PROJECT}-${MODULE_SUFFIX}/Dockerfile")], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
//                }
//            }
//        }
    }
}
