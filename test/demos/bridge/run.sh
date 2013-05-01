
rm -f maven.log issues
mvn -fae clean package | tee -a maven.log

echo "# 1. prepend each test failure in this file with the 'FACES' issue number covering that failure" > issues
echo "# 2. rename this file to 'faces-issues', and put it in the directory with gen.pl" >> issues
echo "# 3. run the gen.pl script to regenerate the test classes" >> issues
echo "# Re-run the tests and you should find no issues in this file" >> issues

grep "\.Jsf2PortletTest):" maven.log >> issues
grep "\.Jsf2JspPortletTest):" maven.log >> issues
grep "\.Icefaces3PortletTest):" maven.log >> issues
grep "\.Richfaces4PortletTest):" maven.log >> issues
grep "\.Primefaces3PortletTest):" maven.log >> issues
grep "\.Icefaces3CompatPortletTest):" maven.log >> issues

cat issues | perl -lpe 's/Failed tests:\s+//; s/^\s+//;' > /tmp/issues
mv /tmp/issues ./issues

