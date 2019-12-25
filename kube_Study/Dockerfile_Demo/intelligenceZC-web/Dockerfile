FROM openjdk:8u181-jdk-alpine
MAINTAINER "Yang"


ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /app

ARG JAR_FILE=intelligenceZC-web-*.jar
WORKDIR /app

ADD ${JAR_FILE} /app/app.jar
ADD static /app/static

ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
EXPOSE 9876


docker build -t intelligencezc-web:0.0.1 .

docker run -dit -m 1024M --name intelligencezc-web -p 9876:9876 -v /data/catt/microservice2/docker/intelligencezc-web/logs:/app/logs \
           -v /data/catt/microservice2/docker/intelligencezc-web/bootstrap.yml:/app/bootstrap.yml  \
           -v /data/catt/microservice2/docker/intelligencezc-web/static:/app/static  \
            intelligencezc-web:0.0.1
