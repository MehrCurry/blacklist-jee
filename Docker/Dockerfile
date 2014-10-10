FROM dockerfile/java:oracle-java8
MAINTAINER Guido Zockoll

RUN apt-get update
RUN apt-get install -y git maven

# create glassfish user (pass: glassfish)
RUN useradd -b /opt -m -s /bin/bash glassfish && echo glassfish:glassfish | chpasswd

# add GlassFish to /opt
RUN cd /opt/glassfish && wget -qO- -O tmp.zip http://dlc.sun.com.edgesuite.net/glassfish/4.1/release/glassfish-4.1.zip && unzip tmp.zip && rm tmp.zip 
RUN cd /opt/glassfish/glassfish4/glassfish/domains/domain1/autodeploy && wget -O jolokia.war http://labs.consol.de/maven/repository/org/jolokia/jolokia-war/1.2.2/jolokia-war-1.2.2.war
# set owner of /opt/glassfish


WORKDIR /opt
RUN git clone https://github.com/MehrCurry/blacklist-jee.git

RUN chown -R glassfish:glassfish /opt/*

# default glassfish ports
EXPOSE 4848 8080 8181

COPY startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

# set glassfish user
USER glassfish

WORKDIR /opt/glassfish/glassfish4/bin

# User: admin / Pass: glassfish
RUN echo "admin;{SSHA256}80e0NeB6XBWXsIPa7pT54D9JZ5DR5hGQV1kN1OAsgJePNXY6Pl0EIw==;asadmin" > /opt/glassfish/glassfish4/glassfish/domains/domain1/config/admin-keyfile
RUN echo "AS_ADMIN_PASSWORD=glassfish" > pwdfile

# Default to admin/glassfish as user/pass
RUN \
  ./asadmin start-domain && \
  ./asadmin --user admin --passwordfile pwdfile enable-secure-admin && \
  ./asadmin stop-domain

# default command to run on container boot
# CMD ["/opt/glassfish/glassfish4/bin/asadmin", "start-domain", "--verbose=true"]


CMD ["/opt/startup.sh"]

