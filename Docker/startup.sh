#!/bin/bash

cd /opt/blacklist-jee
git fetch && git checkout spring-boot
mvn package

java -jar /opt/blacklist-jee7*.jar

