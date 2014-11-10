FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git

WORKDIR /opt

EXPOSE 8080

ADD blacklist-spring-boot-1.0.0-SNAPSHOT.jar blacklist-spring-boot.jar
COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

CMD ["/opt/startup.sh"]

