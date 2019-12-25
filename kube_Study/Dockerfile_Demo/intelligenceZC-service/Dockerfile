FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=intelligenceZC-service-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8700


docker build -t intelligencezc-service:0.0.1 .

docker run -dit -m 1024M --name intelligencezc-service -p 8700:8700 -v /data/catt/microservice2/docker/intelligencezc-service/logs:/app/logs \
           -v /data/catt/microservice2/docker/intelligencezc-service/bootstrap.yml:/app/bootstrap.yml  intelligencezc-service:0.0.1
