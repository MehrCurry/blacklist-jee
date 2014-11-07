FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git maven

ADD settings.xml /root/.m2/settings.xml

WORKDIR /opt
RUN mvn dependency:copy -Dartifact=prototype:blacklist-spring-boot:1.0.0-SNAPSHOT

EXPOSE 8080

COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

CMD ["/opt/startup.sh"]

