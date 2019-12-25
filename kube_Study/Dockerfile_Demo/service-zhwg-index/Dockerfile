FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=service-zhwg-index-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 8876


docker build -t service-zhwg-index:0.0.1 .

docker run -dit -m 1024M --name service-zhwg-index -p 8876:8876 -v /data/catt/microservice2/docker/service-zhwg-index/logs:/app/logs \
           -v /data/catt/microservice2/docker/service-zhwg-index/bootstrap.yml:/app/bootstrap.yml  service-zhwg-index:0.0.1
