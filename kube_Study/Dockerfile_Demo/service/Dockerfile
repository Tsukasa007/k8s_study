FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"

#-Xmx512M -Xms512M
ENV PARAMS=""
#
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#
#
## 添加一个存储空间
VOLUME /tmp
#
ARG JAR_FILE=xxl-job-admin-*.jar
#
ADD ${JAR_FILE} app.jar
#
ENTRYPOINT ["sh","-c","java -jar /app.jar $PARAMS"]
EXPOSE 9998
#docker run -d -p 9998:9998 --restart=always   --name xxl-job   xxljob:2.1.0
