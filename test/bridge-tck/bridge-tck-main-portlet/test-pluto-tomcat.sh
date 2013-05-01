#!/bin/sh
mvn surefire-report:report -P tck,pluto,tomcat
