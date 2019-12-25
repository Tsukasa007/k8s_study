FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=intelligencezc-api-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8800


docker build -t intelligencezc-api:0.0.1 .

docker run -dit -m 1024M --name intelligencezc-api -p 8800:8800 -v /data/catt/microservice2/docker/intelligencezc-api/logs:/app/logs \
           -v /data/catt/microservice2/docker/intelligencezc-api/bootstrap.yml:/app/bootstrap.yml  intelligencezc-api:0.0.1
