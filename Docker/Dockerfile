FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

WORKDIR /opt
COPY server-1.0.0-SNAPSHOT.jar /opt/blacklist-spring-boot.jar
COPY start.sh /opt/
RUN chmod +x /opt/start.sh

ENV PROFILE docker

EXPOSE 8080

ENTRYPOINT [ "/opt/start.sh" ]

