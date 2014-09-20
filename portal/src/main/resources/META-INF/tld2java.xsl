<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE components>
<!-- http://www.jenitennison.com/blog/node/36 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://java.sun.com/xml/ns/j2ee"
	xpath-default-namespace="http://java.sun.com/xml/ns/j2ee">
	<xsl:template match="/">
		<xsl:text>&#xa;</xsl:text>
		<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
		<xsl:text>&#xa;</xsl:text>
		<xsl:text>public class Renderer {</xsl:text>
		<xsl:text>&#xa;</xsl:text>
		<xsl:apply-templates select="/taglib/tag" />
		<xsl:text>&#xa;</xsl:text>
		<xsl:text>}</xsl:text>
		<xsl:text>&#xa;</xsl:text>
		<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
		<xsl:text>&#xa;</xsl:text>
	</xsl:template>
	<xsl:template match="/taglib/tag">
		<xsl:variable name="tagClass" select="replace(tag-class, '.*[.]', '')" />
		<xsl:variable name="tagVar" select="concat(lower-case(substring($tagClass,1,1)),substring($tagClass,2))" />
		<xsl:variable name="componentClass" select="replace($tagClass, 'Tag', '')" />
		<xsl:variable name="componentVar" select="concat(lower-case(substring($componentClass,1,1)),substring($componentClass,2))" />
		<xsl:text>&#x9;public void copyNonFrameworkAttributes(</xsl:text>
		<xsl:value-of select="concat($componentClass, ' ', $componentVar)" />
		<xsl:text>, </xsl:text>
		<xsl:value-of select="concat($tagClass, ' ', $tagVar)" />
		<xsl:text>) {</xsl:text>
		<xsl:text>&#xa;</xsl:text>
		<xsl:apply-templates select="attribute">
			<xsl:with-param name="componentVar" select="$componentVar" />
			<xsl:with-param name="tagVar" select="$tagVar" />
		</xsl:apply-templates>
		<xsl:text>&#x9;}</xsl:text>
		<xsl:text>&#xa;</xsl:text>
	</xsl:template>
	<xsl:template match="/taglib/tag/attribute">
		<xsl:param name="componentVar" select="componentVar"/>
		<xsl:param name="tagVar" select="tagVar"/>
		<xsl:variable name="attributeMethod" select="concat(upper-case(substring(name,1,1)),substring(name,2))" />
		<xsl:value-of select="concat('&#x9;&#x9;', $tagVar, '.set', $attributeMethod, '(', $componentVar, '.get', $attributeMethod, '());')" />
		<xsl:text>&#xa;</xsl:text>
	</xsl:template>
</xsl:stylesheet>
