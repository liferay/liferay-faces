<?xml version="1.0" encoding="UTF-8"?>
<!-- http://www.jenitennison.com/blog/node/36 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://java.sun.com/xml/ns/javaee"
	xpath-default-namespace="http://java.sun.com/xml/ns/javaee">
	<xsl:param name="projectVersion" select="unknown"/>
	<xsl:param name="taglibPackage" select="unknown"/>
	<xsl:template match="/">
		<taglib xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd"
			version="2.1">
			<tlib-version><xsl:value-of select="replace($projectVersion,'-.*$','')" /></tlib-version>
			<short-name><xsl:value-of select="substring-after(/facelet-taglib/namespace,'http://liferay.com/faces/')" /></short-name>
			<uri><xsl:value-of select="/facelet-taglib/namespace" /></uri>
			<xsl:apply-templates select="/facelet-taglib/tag"/>
		</taglib>
	</xsl:template>
	<xsl:template match="/facelet-taglib/tag">
		<xsl:param name="tagName" select="tag-name"/>
		<xsl:param name="handlerClass" select="handler-class"/>
		<tag>
			<description><xsl:value-of select="description" /></description>
			<name><xsl:value-of select="tag-name" /></name>
			<xsl:choose>
				<xsl:when test="starts-with($tagName,'convert')">
					<tag-class><xsl:value-of select="concat($taglibPackage,'.',upper-case(substring($tagName,1,1)),substring($tagName,2))" />Tag</tag-class>		
				</xsl:when>
				<xsl:when test="(string-length($handlerClass) > 0)">
					<tag-class><xsl:value-of select="$handlerClass" /></tag-class>		
				</xsl:when>
				<xsl:otherwise>
					<tag-class><xsl:value-of select="replace(normalize-space(component),'.*[.]',concat($taglibPackage,'.'))" />Tag</tag-class>
				</xsl:otherwise>
			</xsl:choose>
			<body-content>JSP</body-content>
			<xsl:apply-templates select="attribute"/>
		</tag>
	</xsl:template>
	<xsl:template match="/facelet-taglib/tag/attribute">
		<xsl:param name="name" select="name"/>
		<attribute>
			<name><xsl:value-of select="name" /></name>
			<required><xsl:value-of select="required" /></required>
			<xsl:choose>
				<xsl:when test="$name = 'id'">
					<rtexprvalue>true</rtexprvalue>
				</xsl:when>
				<xsl:otherwise>
					<deferred-value>
						<type><xsl:value-of select="type" /></type>
					</deferred-value>
				</xsl:otherwise>
			</xsl:choose>
		</attribute>
	</xsl:template>
</xsl:stylesheet>
