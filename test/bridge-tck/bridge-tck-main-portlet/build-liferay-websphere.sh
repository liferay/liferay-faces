#!/bin/sh
# Note that it is critical to have the "liferay" goal so that the WEB-INF/web-liferay.xml descriptor will be
# used in the WAR artifact as WEB-INF/web.xml
mvn -P liferay,websphere clean install
echo " "
echo "************************************************************************************"
echo "NEXT STEPS:"
echo "Now copy target/bridge-tck-main-portlet.war to $LIFERAY_HOME/deploy on WebSphere and"
echo "then deploy the massaged war in $LIFERAY_HOME/deploy-websphere in the Admin Panel"
echo "************************************************************************************"
