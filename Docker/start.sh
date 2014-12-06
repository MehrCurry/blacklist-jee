#!/bin/sh

echo java $VM_ARGS -Dspring.profiles.active=$PROFILE -jar /opt/blacklist-spring-boot.jar $@
java $VM_ARGS -Dspring.profiles.active=$PROFILE -jar /opt/blacklist-spring-boot.jar $@
