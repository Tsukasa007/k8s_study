FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=service-ukey-login-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8877


docker build -t service-ukey-login:0.0.1 .

docker run -dit -m 1024M --name service-ukey-login -p 8877:8877 -v /data/catt/microservice2/docker/service-ukey-login/logs:/app/logs \
           -v /data/catt/microservice2/docker/service-ukey-login/bootstrap.yml:/app/bootstrap.yml  service-ukey-login:0.0.1
