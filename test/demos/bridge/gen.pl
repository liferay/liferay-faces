#!/usr/bin/perl -w

use strict;

my %in = (
   Icefaces3CompatPortletTest => "./icefaces3-compat-portlet/src/test/java/com/liferay/faces/test/Icefaces3Compat.java",
   Icefaces3PortletTest => "./icefaces3-portlet/src/test/java/com/liferay/faces/test/Icefaces3.java",
   Jsf2JspPortletTest => "./jsf2-jsp-portlet/src/test/java/com/liferay/faces/test/Jsf2Jsp.java",
   Jsf2PortletTest => "./jsf2-portlet/src/test/java/com/liferay/faces/test/Jsf2.java",
   Primefaces3PortletTest => "./primefaces3-portlet/src/test/java/com/liferay/faces/test/Primefaces3.java",
   Richfaces4PortletTest => "./richfaces4-portlet/src/test/java/com/liferay/faces/test/Richfaces4.java",
);

my $template = "./job-application-portlet/src/test/java/com/liferay/faces/test/JobPortletTest.java";

my @class = keys %in;
my %xpaths = ();
my $foo;
my $variable;
my @path;
my $xpath;
my $dir;
my $javaFile;
my $url;
my $dateValidationXpathModifier;
my %out = ();

my %variables = ();
open(TEMPLATE, $template) or die "cannot open $template: $!\n";
while (<TEMPLATE>) {
   chomp;
   /private..*Xpath/ && do {
      ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
      $xpath = join " ", @path;
      $variables{$variable} = 1;
      # print "$variable\n";
   };
}
close TEMPLATE;

foreach my $class (@class) {
   print "class = $class\n";
   $javaFile = "${class}.java";

   $_ = $in{$class};
   s/\w+\.java//;
   $dir = $_;

   $out{$class} = $dir . $javaFile;
#  print "$dir $javaFile\n";

   print "$in{$class}\n";
   open(IN, $in{$class}) or die "cannot open $in{$class}: $!\n";
   while (<IN>) {
      chomp;
      /String url =/ && do {
         $url = $_;
      };
      /private..*Xpath/ && do {
         ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
         $xpath = join " ", @path;
         $xpaths{$variable} = $xpath;
         # print "$variable $xpath\n";
      };
      /int dateValidationXpathModifier/ && do {
         $dateValidationXpathModifier = $_;
      };
   }
   close IN;

   open(TEMPLATE, $template) or die "cannot open $template: $!\n";
   open(OUT, ">$out{$class}") or die "cannot open $out{$class}: $!\n";
   while (<TEMPLATE>) {
      chomp;
      if (/public class/) {
         print "$_\n";
         print OUT "	public class $class \{\n";
      } elsif (/private final static Logger/) {
         print "	private final static Logger logger = Logger.getLogger(${class}.class.getName());\n";
         print OUT "	private final static Logger logger = Logger.getLogger(${class}.class.getName());\n";
      } elsif (/String url =/) {
         print "$url\n";
         print OUT "$url\n";
      } elsif (/private..*Xpath/) {
         ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
         print OUT "	private static final String $variable = $xpaths{$variable}\n";
      } elsif (/int dateValidationXpathModifier/) {
         print "$dateValidationXpathModifier\n";
         print OUT "$dateValidationXpathModifier\n";
      } else {
         print OUT "$_\n";
      }
   }
   close OUT;
   close TEMPLATE;
   print "\n";
}

