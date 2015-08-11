#!/bin/bash

JAVA_OPTS="-Xms512M -Xmx1G -Xmn192M \
	-XX:PermSize=64M -XX:MaxPermSize=64M \
	-XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 \
	-XX:+PrintGCDetails -XX:+PrintGCDateStamps -verbose:gc -XX:+DisableExplicitGC \
	-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6666"
