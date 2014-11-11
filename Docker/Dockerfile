FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git

ADD settings.xml /root/.m2/settings.xml

WORKDIR /opt
ADD blacklist-spring-boot-1.0.0-SNAPSHOT.jar blacklist-spring-boot.jar
RUN wget -O maven.tar.gz http://mirror.softaculous.com/apache/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.tar.gz && tar xvzf maven.tar.gz
ENV MVN_HOME /opt/apache-maven-3.2.3
RUN $MVN_HOME/bin/mvn dependency:copy -Dartifact=prototype:blacklist-spring-boot:1.0.0-SNAPSHOT

EXPOSE 8080


ENTRYPOINT [ "/usr/bin/java", "-Dspring.profiles.active=docker", "/opt/blacklist-spring-boot.jar"]

