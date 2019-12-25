FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=eureka-server-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8761


docker build -t eureka-server:0.0.1 .

docker run -dit -m 1024M --name eureka -p 8761:8761 -v /data/catt/microservice2/docker/eureka/logs:/app/logs \
           -v /data/catt/microservice2/docker/eureka/application.yml:/app/application.yml  eureka-server:0.0.1
