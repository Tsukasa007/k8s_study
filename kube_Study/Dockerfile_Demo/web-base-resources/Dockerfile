FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=web-base-resources-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 9875


docker build -t web-base-resources:0.0.1 .

docker run -dit -m 1024M --name web-base-resources -p 9875:9875 -v /data/catt/microservice2/docker/web-base-resources/logs:/app/logs \
           -v /data/catt/microservice2/docker/web-base-resources/bootstrap.yml:/app/bootstrap.yml  web-base-resources:0.0.1
