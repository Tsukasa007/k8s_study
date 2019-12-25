FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=config-server-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8758


docker build -t config-server:0.0.1 .

docker run -dit -m 1024M --name config -p 8758:8758 -v /data/catt/microservice2/docker/config/logs:/app/logs \
           -v /data/catt/microservice2/docker/config/application.yml:/app/application.yml  config-server:0.0.1
