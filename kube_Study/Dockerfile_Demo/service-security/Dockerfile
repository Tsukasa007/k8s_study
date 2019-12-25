FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=service-security-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8081


docker build -t service-security:0.0.1 .

docker run -dit -m 1024M --name service-security -p 8081:8081 -v /data/catt/microservice2/docker/service-security/logs:/app/logs \
           -v /data/catt/microservice2/docker/service-security/bootstrap.yml:/app/bootstrap.yml  service-security:0.0.1
