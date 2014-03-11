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

if [ ! -d ".alloyui" ] ; then
	mkdir .alloyui
	mkdir .alloyui/alloy-build
elif [ ! -d ".alloyui/alloy-build" ] ; then
	mkdir .alloyui/alloy-build
fi

cd .alloyui/
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
	grunt api-build
else
	grunt api
fi
echo "Done building docs for alloyUI $ALLOY_UI_VERSION."
