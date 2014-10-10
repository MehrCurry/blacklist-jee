#!/bin/bash

cd /opt/blacklist-jee
git pull
mvn package
cp target/*.war /opt/glassfish/glassfish4/glassfish/domains/domain1/autodeploy/

while true; do
	/opt/glassfish/glassfish4/bin/asadmin start-domain --verbose=true
	sleep 5
done

