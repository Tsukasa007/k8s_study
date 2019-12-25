FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=service-gateway-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8769


docker build -t service-gateway:0.0.1 .

docker run -dit -m 1024M --name service-gateway -p 8769:8769 -v /data/catt/microservice2/docker/service-gateway/logs:/app/logs \
           -v /data/catt/microservice2/docker/service-gateway/application.yml:/app/application.yml   \
           -v /data/catt/microservice2/docker/service-gateway/bootstrap.yml:/app/bootstrap.yml   \
             service-gateway:0.0.1
