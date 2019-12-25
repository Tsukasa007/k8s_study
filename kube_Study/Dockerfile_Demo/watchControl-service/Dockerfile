FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=watchControl-service-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 16700


docker build -t watchcontrol-service:0.0.1 .

docker run -dit -m 1024M --name watchcontrol-service -p 16700:16700 -v /data/catt/microservice2/docker/watchcontrol-service/logs:/app/logs \
            -v /data/catt/microservice2/docker/watchcontrol-service/bootstrap.yml:/app/bootstrap.yml \
           -v /data/catt/microservice2/docker/watchcontrol-service/watchControl-service-0.0.1-SNAPSHOT.jar:/app/app.jar \
           watchcontrol-service:0.0.1
