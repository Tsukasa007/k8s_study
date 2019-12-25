FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=vue-common-resources-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 6300


docker build -t vue-common-resources:0.0.1 .

docker run -dit -m 1024M --name vue-common-resources -p 6300:6300 -v /data/catt/microservice2/docker/vue-common-resources/logs:/app/logs \
           -v /data/catt/microservice2/docker/vue-common-resources/bootstrap.yml:/app/bootstrap.yml  vue-common-resources:0.0.1
