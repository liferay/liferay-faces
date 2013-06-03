#!/bin/sh
mvn surefire-report:report -P tck,liferay,geronimo
