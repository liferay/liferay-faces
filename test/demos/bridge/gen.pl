#!/usr/bin/perl -w

use strict;

# primitives
my $issueFile = "faces-issues";
my $template = "./job-application-portlet/src/test/java/com/liferay/faces/test/JobPortletTest.java";
my $dateValidationXpathModifier;
my $variable;
my $javaFile;
my($issue, $affected, $test, $method, $assertion);
my $annotation;
my $testClass;
my $xpath;
my $lineNumber = 0;
my $line;
my $dir;
my $url;
my $foo;

# hashes
my %in = (
   Jsf2PortletTest => "./jsf2-portlet/src/test/java/com/liferay/faces/test/Jsf2.java",
   Jsf2JspPortletTest => "./jsf2-jsp-portlet/src/test/java/com/liferay/faces/test/Jsf2Jsp.java",
   Icefaces3PortletTest => "./icefaces3-portlet/src/test/java/com/liferay/faces/test/Icefaces3.java",
   Richfaces4PortletTest => "./richfaces4-portlet/src/test/java/com/liferay/faces/test/Richfaces4.java",
   Primefaces3PortletTest => "./primefaces3-portlet/src/test/java/com/liferay/faces/test/Primefaces3.java",
   Icefaces3CompatPortletTest => "./icefaces3-compat-portlet/src/test/java/com/liferay/faces/test/Icefaces3Compat.java",
);
my %variables = ();
my %xpaths = ();
my %annotations = ();
my %methods = ();
my %out = ();

# lists
my @class = keys %in;
my @path;
my @bar;

# parse the issues from the issues file
if ( -f "$issueFile" ) {
   open(ISSUES, $issueFile) or die "cannot open $issueFile: $!\n";
   while (<ISSUES>) {
      chomp;
      next unless /^FACES/;
      ($issue, $affected, @bar) = split;
      $_ = $affected;
      /(\w+)\(/; $method = $1;
      /\.(\w+)\):/; $testClass= $1;
      $methods{$testClass . $method} = $issue;
   }
   close ISSUES;
} else {
   print "no issuesFile ... \n";
}

# parse the Xpath variable names in from the template .java file
open(TEMPLATE, $template) or die "cannot open $template: $!\n";
while (<TEMPLATE>) {
   $lineNumber += 1;
   chomp;
   if (/private..*Xpath/) {
      ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
      $xpath = join " ", @path;
      $variables{$variable} = 1;
      # print "$variable\n";
   }
   if (/\@Test/) {
      $annotation = $lineNumber;
   }
   if (/public void \w+\(\) throws Exception/) {
      # public void jobApplicantRenderViewMode() throws Exception
      ($foo,$foo,$method,@bar) = split;
      $_ = $method;
      s/\(\)//;
      $method = $_;
      # print "method = $method\n";
      if (defined $annotation) {
         $annotations{$annotation} = $method;
      }
   }
}
close TEMPLATE;

# iterate through the Test.java classes
foreach my $class (@class) {

   # get the output file name
   print "class = $class\n";
   $javaFile = "${class}.java";

   # get the output directory name
   $_ = $in{$class};
   s/\w+\.java//;
   $dir = $_;

   # get the full path to the output file
   $out{$class} = $dir . $javaFile;
   # print "$dir $javaFile\n";

   # parse the Xpaths in that are specific to the component library being tested
   print "$in{$class}\n";
   open(IN, $in{$class}) or die "cannot open $in{$class}: $!\n";
   while (<IN>) {
      chomp;
      if(/String url =/) {
         $url = $_;
      }
      if (/private..*Xpath/) {
         ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
         $xpath = join " ", @path;
         $xpaths{$variable} = $xpath;
         # print "$variable $xpath\n";
      }
      if (/int dateValidationXpathModifier/) {
         $dateValidationXpathModifier = $_;
      }
   }
   close IN;

   # write the output file, using the TEMPLATE file
   open(TEMPLATE, $template) or die "cannot open $template: $!\n";
   open(OUT, ">$out{$class}") or die "cannot open $out{$class} for writing: $!\n";
   $lineNumber = 0;
   while (<TEMPLATE>) {
      ++$lineNumber;
      chomp;
      if (/public class/) {
         print "$_\n";
         print OUT "public class $class \{\n";
      } elsif (/private final static Logger/) {
         print OUT "	private final static Logger logger = Logger.getLogger(${class}.class.getName());\n";
      } elsif (/String url =/) {
         print "$url\n";
         print OUT "$url\n";
      } elsif (/private..*Xpath/) {
         ($foo,$foo,$foo,$foo,$variable,$foo,@path) = split;
         print OUT "	private static final String $variable = $xpaths{$variable}\n";
      } elsif (/int dateValidationXpathModifier/) {
         print OUT "$dateValidationXpathModifier\n";
      } elsif (defined $annotations{$lineNumber}) {
         if (defined $methods{$class . $annotations{$lineNumber}}) {
            $issue = $methods{$class . $annotations{$lineNumber}};
            $method = $annotations{$lineNumber};
            print OUT "// $issue covers an issue tested by ${method}.  Please refer to $issue for clarification.\n";
            print OUT "// $_\n";
         } else {
            print OUT "$_\n";
         }
      } else {
         print OUT "$_\n";
      }
   }
   close OUT;
   close TEMPLATE;
   print "\n";
}

