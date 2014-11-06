FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git maven

ADD settings.xml /root/.m2/settings.xml

WORKDIR /opt
RUN git clone https://github.com/MehrCurry/blacklist-jee.git

EXPOSE 8080

COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

CMD ["/opt/startup.sh"]

