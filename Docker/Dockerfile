FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git

ADD settings.xml /root/.m2/settings.xml

WORKDIR /opt
RUN wget -O maven.tar.gz http://mirror.softaculous.com/apache/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.tar.gz && tar xvz maven.tar.gz
ENV MVN_HOME /opt/apache-maven-3.2.3

EXPOSE 8080

COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

CMD ["/opt/startup.sh"]

