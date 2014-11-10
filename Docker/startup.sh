#!/bin/bash

cd /opt
$MVN_HOME/bin/mvn dependency:copy -Dartifact=prototype:blacklist-spring-boot:1.0.0-SNAPSHOT

java -jar -Dspring.profiles.active=docker /opt/blacklist-*.jar

