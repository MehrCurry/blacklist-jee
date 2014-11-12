FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git

ADD settings.xml /root/.m2/settings.xml

WORKDIR /opt
ADD blacklist-spring-boot-1.0.0-SNAPSHOT.jar /opt/blacklist-spring-boot.jar

EXPOSE 8080

ENTRYPOINT [ "/usr/bin/java", "-Dspring.profiles.active=docker", "-jar", "/opt/blacklist-spring-boot.jar"]

