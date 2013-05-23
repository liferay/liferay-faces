#!/bin/sh

################################################################################
#
# Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
#
# This library is free software; you can redistribute it and/or modify it under
# the terms of the GNU Lesser General Public License as published by the Free
# Software Foundation; either version 2.1 of the License, or (at your option)
# any later version.
#
# This library is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
# details.
#
################################################################################

################################################################################
#
# This shell script is a convenience utility that exists in the top-level folder
# of the liferay-faces project. It provides a simple set of command line options
# for deploying portlet WARs to various portals and application servers.
#
################################################################################
#
# Author: Neil Griffin
#
################################################################################
#
# Usage:
#
#    deploy.sh [myfaces] [ee] [pluto] [glassfish|jboss|tomcat|weblogic|websphere]
#
# Options:
#
#    myfaces    Include MyFaces Core Runtime JARs rather than Mojarra (the default)
#    ee         Deploy to Liferay Portal Enterprise Edition rather than Community
#               Edition (the default)
#    pluto      Deploy to Apache Pluto rather than Liferay (the default)
#    glassfish  Deploy to Oracle GlassFish Server
#    jboss      Deploy to JBoss Application Server (a.k.a WildFly)
#    tomcat     Deploy to Apache Tomcat (the default)
#    weblogic   Deploy to Oracle WebLogic Server
#    websphere  Deploy to IBM WebSphere Application Server 
#
# Examples:
#
# 1) Deploy the jsf2-portlet to Liferay+Tomcat (the default)
#
#     cd demos/bridge/jsf2-portlet
#     deploy.sh
#
# 2) Deploy the jsf2-portlet to Liferay EE + GlassFish
#
#     cd demos/bridge/jsf2-portlet
#     deploy.sh ee glassfish
#
# 3) Deploy the jsf-portlet to Apache Pluto + Tomcat
#
#     cd demos/bridge/jsf2-portlet
#     deploy.sh pluto
#
# 4) Deploy all of the demos to Liferay+Tomcat using MyFaces 
#
#     cd demos
#     deploy.sh myfaces
#
################################################################################

LIFERAY_FACES_VERSION=`pwd | sed -e 's/^.*liferay-faces-//g' | sed -e 's/\/.*$//g'`
REBUILD="false"
FACES_IMPL=""
PORTAL_PROFILE_NAME="liferay"
SERVER_PROFILE_NAME="tomcat"
EXTRA_PROFILE_NAMES=""
until [ -z $1 ] ; do
	if [ "$1" = "myfaces" ]; then
		FACES_IMPL=$1
	elif [ "$1" = "pluto" ] ; then
		PORTAL_PROFILE_NAME=$1
	elif [ "$1" = "glassfish" ] ; then
		SERVER_PROFILE_NAME=$1
	elif [ "$1" = "jboss" ] ; then
		SERVER_PROFILE_NAME=$1
	elif [ "$1" = "weblogic" ] ; then
		SERVER_PROFILE_NAME=$1
	elif [ "$1" = "websphere" ] ; then
		SERVER_PROFILE_NAME=$1
	elif [ "$1" = "rebuild" ] ; then
		REBUILD="true"
	else
		if [ -z $EXTRA_PROFILE_NAMES ] ; then
			EXTRA_PROFILE_NAMES="$1"
		else
			EXTRA_PROFILE_NAMES="$EXTRA_PROFILE_NAMES,$1"
		fi
	fi
	shift
done
PORTLET_MVN_CMD="mvn -Dfaces-impl=$FACES_IMPL -P $PORTAL_PROFILE_NAME,$SERVER_PROFILE_NAME,$EXTRA_PROFILE_NAMES clean install"
if [ "$PORTAL_PROFILE_NAME" = "liferay" ] ; then
	if [ "$LIFERAY_FACES_VERSION" = "3.0.x-legacy" ] ; then
		# liferay-maven-plugin not supported for Liferay 5.2.x
		PORTLET_MVN_CMD="$PORTLET_MVN_CMD"
	else
		PORTLET_MVN_CMD="$PORTLET_MVN_CMD liferay:deploy"
	fi
fi
echo "==============================================================================================================="
echo "[INFO: deploy.sh] FACES_IMPL=$FACES_IMPL"
echo "[INFO: deploy.sh] REBUILD=$REBUILD"
echo "[INFO: deploy.sh] PORTAL_PROFILE_NAME=$PORTAL_PROFILE_NAME"
echo "[INFO: deploy.sh] SERVER_PROFILE_NAME=$SERVER_PROFILE_NAME"
echo "[INFO: deploy.sh] EXTRA_PROFILE_NAMES=$EXTRA_PROFILE_NAMES"
echo "[INFO: deploy.sh] PORTLET_MVN_CMD=$PORTLET_MVN_CMD"
echo "==============================================================================================================="
echo " "
if [ "$REBUILD" = "true" ] ; then
	if [ -z $EXTRA_PROFILE_NAMES ] ; then
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/util; mvn clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/alloy; mvn clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/bridge-api; mvn clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/bridge-impl; mvn clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/portal; mvn clean install; popd
	else
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/util; mvn -P $EXTRA_PROFILE_NAMES clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/alloy; mvn -P $EXTRA_PROFILE_NAMES clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/bridge-api; mvn -P $EXTRA_PROFILE_NAMES clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/bridge-impl; mvn -P $EXTRA_PROFILE_NAMES clean install; popd
		pushd $PROJECTS_HOME/liferay.com/liferay-faces-$LIFERAY_FACES_VERSION/portal; mvn -P $EXTRA_PROFILE_NAMES clean install; popd
	fi
fi
$PORTLET_MVN_CMD
