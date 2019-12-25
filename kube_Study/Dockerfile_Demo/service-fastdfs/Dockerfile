FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=service-fastdfs-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8771


docker build -t service-fastdfs:0.0.1 .

docker run -dit -m 1024M --name service-fastdfs -p 8771:8771 -v /data/catt/microservice2/docker/service-fastdfs/logs:/app/logs \
           -v /data/catt/microservice2/docker/service-fastdfs/bootstrap.yml:/app/bootstrap.yml  service-fastdfs:0.0.1
