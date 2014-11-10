FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git

WORKDIR /opt
RUN wget -O maven.tar.gz http://mirror.softaculous.com/apache/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.tar.gz && tar xvzf maven.tar.gz
ENV MVN_HOME /opt/apache-maven-3.2.3
RUN $MVN_HOME/bin/mvn dependency:copy -Dartifact=prototype:blacklist-spring-boot:1.0.0-SNAPSHOT -DoutputDirectory=/opt

EXPOSE 8080

COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

CMD ["/opt/startup.sh"]

