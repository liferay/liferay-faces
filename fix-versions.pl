#!/usr/bin/perl -w

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
# This Perl script is a convenience utility that exists in the top-level folder
# of the liferay-faces project. It parses the project version information from
# the parent-most pom.xml file and fixes version number mistakes and
# inconsistencies in the following types of files:
#
# (1) pom.xml (child modules)
# (2) liferay-display.xml
# (3) liferay-portlet.xml
# (4) liferay-plugin-package.properties
# (5) book.xml
#
################################################################################
#
# Author: Vernon Singleton
#
################################################################################

use strict;
use File::Find;
use POSIX qw(strftime);

#
# Primitives
#
my($liferayFacesVersion,$liferayFacesVersionShort,$liferayFacesVersionShortMajor1DotMajor2,$major1,$major2,$minor);
my($portalVersion,$portalVersions,$portalDtdDisplay,$portalDtdUrl,$bookVersion);
my($facesVersion,$facesVersionURL,$facesMajor,$facesMinor);
my($liferayFacesMajor1,$liferayFacesMajor2,$liferayFacesMinor,);
my $year= strftime "%Y", localtime;

#
# Parse the project version information from the parent-most POM file.
#
open POM, "pom.xml" or die "cannot open pom.xml: $!\n";
while(<POM>) {

	if (/artifactId>liferay-faces</) {

		$_ = <POM>;
		/<version>(.*)</;
		$liferayFacesVersion = $1;
		print "liferayFacesVersion = $liferayFacesVersion\n";

		$_ = $liferayFacesVersion;
		s/-SNAPSHOT//;
		$bookVersion = $_;
		print "bookVersion = $bookVersion\n";

		$_ = $bookVersion;
		s/-(.*)//;
		$liferayFacesVersionShort = $_;
		print "liferayFacesVersionShort = $liferayFacesVersionShort\n";

		($liferayFacesMajor1,$liferayFacesMajor2,$liferayFacesMinor) = split /\./;
		$liferayFacesVersionShortMajor1DotMajor2 = "${liferayFacesMajor1}.${liferayFacesMajor2}";
		print "liferayFacesVersionShortMajor1DotMajor2 = $liferayFacesVersionShortMajor1DotMajor2\n";

	}

	if (/liferay.version>/) {

		/version>(.*)</;
		$portalVersion = $1;
		print "portalVersion = $portalVersion\n";

		$_ = $portalVersion;

		($major1,$major2,$minor) = split /\./;
		$portalVersions = "${major1}.${major2}.*";
		print "portalVersions = $portalVersions\n";

		$portalDtdDisplay = "${major1}.${major2}.0";
		print "portalDtdDisplay = $portalDtdDisplay\n";

		$portalDtdUrl = "${major1}_${major2}_0";
		print "portalDtdUrl = $portalDtdUrl\n";

	}

	if(/faces.spec.version>/) {

		/version>(.*)</;
		$facesVersion = $1;
		print "facesVersion = $facesVersion\n";

		$_ = $facesVersion;

		($facesMajor,$facesMinor) = split /[._]/;

		$facesVersionURL = "${facesMajor}_${facesMinor}";
		print "facesVersionURL = $facesVersionURL\n";

	}

}
close POM;

#
# For each file in the hierarchy:
#
find(\&do_inplace_edits, ".");

sub do_inplace_edits {

	my $file = $_;

	#
	# If the current file is named "pom.xml" and is not the parent-most POM, then potentially fix the
	# version number specified in the <parent><version>...</version></parent> section.
	#
	if ($file eq "pom.xml" and $File::Find::name ne "./pom.xml" and $File::Find::name !~ /\/target/) {
		print "$File::Find::name\n";

		my $inParent = 0;
		open OUT, ">pom.xml.tmp" or die "cannot open >pom.xml.tmp: $!\n";
		open IN, "pom.xml" or die "cannot open pom.xml: $!\n";
		while(<IN>) {
			if (/<parent/) { $inParent = 1; }
			if ($inParent == 1 and /<version/) {
				s/ersion>..*</ersion>$liferayFacesVersion</;
				$inParent = 0;
			}
			print OUT;
		}
		close IN;
		close OUT;
		rename("pom.xml.tmp", "pom.xml");
	}

	#
	# Otherwise, if the current file is named "liferay-portlet.xml" then potentially fix the version
	# numbers specified in DOCTYPE line for the DTD.
	#
	elsif ($file eq "liferay-portlet.xml" and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/DTD Portlet Application ..*\\/\\/EN/DTD Portlet Application $portalDtdDisplay\\/\\/EN/' $file`;
		`perl -pi -e 's/-portlet-app_..*\\.dtd/-portlet-app_$portalDtdUrl\\.dtd/' $file`;
	}
 
	#
	# Otherwise, if the current file is named "liferay-display.xml" then potentially fix the version
	# numbers specified in DOCTYPE line for the DTD.
	#
	elsif ($file eq "liferay-display.xml" and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/DTD Display ..*\\/\\/EN/DTD Display $portalDtdDisplay\\/\\/EN/' $file`;
		`perl -pi -e 's/-display_..*\\.dtd/-display_$portalDtdUrl\\.dtd/' $file`;
	}

	#
	# Otherwise, if the current file is named "liferay-plugin-package.properties" then potentially fix
	# the version wildcard that indicates compatible versions of Liferay Portal.
	#
	elsif ($file eq "liferay-plugin-package.properties" and ($File::Find::name =~ /demos\/portal\/.*\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/liferay-versions=..*/liferay-versions=$portalVersions/' $file`;
	}

	#
	# Otherwise, if the current file is named "book.xml" then potentially fix the version number that
	# will appear in the generated PDF documentation.
	#
	elsif ($File::Find::name eq "./doc/src/main/docbook/en-US/book.xml") {
		print "$File::Find::name\n";
		`perl -pi -e 's/ENTITY versionNumber "..*"/ENTITY versionNumber "$bookVersion"/' $file`;
		`perl -pi -e 's/ENTITY copyrightYear "2000-..* "/ENTITY copyrightYear "2000-${year} "/' $file`;
	}

	#
	# Otherwise, if the current file is named "faces-config.xml" then potentially fix the version number that
	# will appear in the version attribute faces config tag and potentially fix the version number that will appear in
	# the xsi:schemaLocation attribute URL of the faces config tag.
	#
	elsif ($file eq "faces-config.xml" and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/faces-config version=\"[0-9.]+\"/faces-config version=\"$facesVersion\"/' $file`;
		`perl -pi -e 's/web-facesconfig[0-9_]+/web-facesconfig_$facesVersionURL/' $file`;
		if ($facesMajor > 2 or ($facesMajor == 2 and $facesMinor > 1)) {
			`perl -pi -e 's/java.sun.com/xmlns.jcp.org/g' $file`;
		} else {
			`perl -pi -e 's/xmlns.jcp.org/java.sun.com/g' $file`;
		}
	}

	#
	# Otherwise, if the current file is named with .tld extension, then potentially fix the version number that will
	# appear in the tlib-version attribute tag.
	#
	elsif (($file =~ m/.*\.tld/) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/tlib-version>[0-9.]+/tlib-version>$liferayFacesVersionShort/' $file`;
	}

	#
	# Otherwise, if the current file is a chapter or section in the docbook, then potentially fix the version number that will
	# appear in the URL to the VDLDocs.
	#
	elsif ((($file =~ m/chapter\-.+\.xml/) or ($file =~ m/sect1\-.+\-uicomponent-tags\.xml/)) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/\\/faces\\/[0-9.]+\\/vdldoc\\//\\/faces\\/$liferayFacesVersionShortMajor1DotMajor2\\/vdldoc\\//g' $file`;
	}
	
	#
	# Otherwise, if the current file is named with .taglib.xml extension, then potentially fix the version number that will
	# appear in the vdldoc:since element.
	#
	elsif (($file =~ m/.*\.taglib\.xml/) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/vdldoc:since>[0-9]\\.[0-9]/vdldoc:since>$liferayFacesVersionShortMajor1DotMajor2/' $file`;
	}

	#
	# Otherwise, if the current file is named with .xhtml extension, then potentially fix the version number that will
	# appear in the vdldoc:since element.
	#
	elsif (($file =~ m/.*\.xhtml/) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/vdldoc:since value=\\"[0-9]\\.[0-9]/vdldoc:since value=\\"$liferayFacesVersionShortMajor1DotMajor2/' $file`;
	}

	#
	# Otherwise, if the current file is named NOTICE.txt, then potentially fix the copyright year.
	#
	elsif (($file =~ m/NOTICE.txt/) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/2000-..* Liferay/2000-${year} Liferay/' $file`;
	}

	#
	# Otherwise, if the current file is named with .java extension, then potentially fix the copyright year.
	#
	elsif (($file =~ m/.*\.java/) and ($File::Find::name =~ /\/src/)) {
		print "$File::Find::name\n";
		`perl -pi -e 's/2000-..* Liferay/2000-${year} Liferay/' $file`;
	}
}

