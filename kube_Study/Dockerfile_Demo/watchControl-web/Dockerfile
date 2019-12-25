FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=watchControl-web-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar
ADD static /app/static

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 15876


docker build -t watchcontrol-web:0.0.1 .

docker run -dit -m 1024M --name watchcontrol-web -p 15876:15876 -v /data/catt/microservice2/docker/watchcontrol-web/logs:/app/logs \
           -v /data/catt/microservice2/docker/watchcontrol-web/bootstrap.yml:/app/bootstrap.yml  \
           -v /data/catt/microservice2/docker/watchcontrol-web/static:/app/static  \
            watchcontrol-web:0.0.1
