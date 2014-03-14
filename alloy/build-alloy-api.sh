#!/bin/bash

################################################################################
#
# Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
# This shell script is a convenience utility that exists in the alloy folder of
# the liferay-faces project. It provides a simple method for downloading and
# building alloyUI's api docs from the alloyUI source on github. This script is
# meant to be used to get alloyUI docs for versions 2.0.0 and beyond. It will
# not work for versions before 2.0.0.
#
# Note: To run this script, you must have npm installed. You can download npm
# from: http://nodejs.org/download/. Also, before running this script, you will
# need to run the following command:
#
# sudo npm install -g grunt-cli shifter yogi yuidocjs phantomjs grover istanbul
#
#
################################################################################
#
# Author: Kyle Stiemann 
#
################################################################################
#
# Usage:
#
#	build-alloy-api.sh [alloyUIVersion]
#
# Examples:
#
# 1) Build alloy's api docs for the 2.0.0 tag:
#
#	build-alloy-api.sh
#
# 2) Build alloy's api docs for the master branch:
#
#	build-alloy-api.sh master
#
################################################################################

ALLOY_UI_VERSION="2.0.0"
if [ $# -ne 0 ] ; then
	ALLOY_UI_VERSION=$1
fi

if [ ! -d "$HOME/.alloyui" ] ; then
	mkdir $HOME/.alloyui
	mkdir $HOME/.alloyui/alloy-build
elif [ ! -d "$HOME/.alloyui/alloy-build" ] ; then
	mkdir $HOME/.alloyui/alloy-build
fi

cd $HOME/.alloyui/
echo "Getting $ALLOY_UI_VERSION.zip..."
curl -L https://github.com/liferay/alloy-ui/archive/$ALLOY_UI_VERSION.zip -o $ALLOY_UI_VERSION.zip
echo "Done getting $ALLOY_UI_VERSION.zip."
echo "Unzipping $ALLOY_UI_VERSION.zip..."
yes | unzip $ALLOY_UI_VERSION.zip -d alloy-build/
echo "Done unzipping $ALLOY_UI_VERSION.zip."
echo "Initializing dependencies for alloyUI $ALLOY_UI_VERSION..."
cd alloy-build/alloy-ui-$ALLOY_UI_VERSION/
npm install
grunt init
echo "Done initializing dependencies for alloyUI $ALLOY_UI_VERSION."
echo "Building docs for alloyUI $ALLOY_UI_VERSION..."
if [ "$ALLOY_UI_VERSION" = "2.0.0" ] ; then

	# Changes in this file from 
	# https://github.com/liferay/alloy-apidocs-theme/commit/51f0e38aab92f0552e4e640ab31cb8dcb09590d6#diff-b3fb1962982568042e2b262821f8017a
	# break the grunt api-build task on the AlloyUI 2.0.0 tag, so the file must
	# be reverted to the state it was in previous to this commit before running
	# the task
	sed -e "s/\"themedir\": \"\.\.\/\.\.\/alloy-apidocs-theme\",/\"themedir\": \"\.\.\/alloy-apidocs-theme\",/g" ../alloy-apidocs-theme/yuidoc.json > ../alloy-apidocs-theme/yuidoc.json.tmp && mv ../alloy-apidocs-theme/yuidoc.json.tmp ../alloy-apidocs-theme/yuidoc.json
	sed -e "s/\"paths\": \[ \"yui3\/src\", \"alloy-ui\/src\" \]/\"paths\": \[ \"\.\.\/yui3\/src\", \"src\" \]/g" ../alloy-apidocs-theme/yuidoc.json > ../alloy-apidocs-theme/yuidoc.json.tmp && mv ../alloy-apidocs-theme/yuidoc.json.tmp ../alloy-apidocs-theme/yuidoc.json

	grunt api-build

	# Remove the changes just in case
	sed -e "s/\"themedir\": \"\.\.\/alloy-apidocs-theme\",/\"themedir\": \"\.\.\/\.\.\/alloy-apidocs-theme\",/g" ../alloy-apidocs-theme/yuidoc.json > ../alloy-apidocs-theme/yuidoc.json.tmp && mv ../alloy-apidocs-theme/yuidoc.json.tmp ../alloy-apidocs-theme/yuidoc.json
	sed -e "s/\"paths\": \[ \"\.\.\/yui3\/src\", \"src\" \]/\"paths\": \[ \"yui3\/src\", \"alloy-ui\/src\" \]/g" ../alloy-apidocs-theme/yuidoc.json > ../alloy-apidocs-theme/yuidoc.json.tmp && mv ../alloy-apidocs-theme/yuidoc.json.tmp ../alloy-apidocs-theme/yuidoc.json
else
	grunt api
fi
echo "Done building docs for alloyUI $ALLOY_UI_VERSION."
