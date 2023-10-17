FROM openjdk:8u212-jdk-alpine
MAINTAINER "Yang"
ADD target/*.jar app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
ENV JAVA_OPTS=" -XshowSettings:vm -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:MaxDirectMemorySize=64m -XX:MaxMetaspaceSize=128m -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app.jar"]
