<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE components>
<!-- http://www.jenitennison.com/blog/node/36 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://java.sun.com/xml/ns/j2ee"
	xpath-default-namespace="http://java.sun.com/xml/ns/j2ee">
	<xsl:template match="/">
		<components xmlns="" extends="../util/common-components.xml">
			<xsl:apply-templates select="/taglib/tag" />
		</components>
	</xsl:template>
	<xsl:template match="/taglib/tag">
		<xsl:variable name="tagClass" select="replace(tag-class, '.*[.]', '')" />
		<xsl:variable name="componentClass" select="replace($tagClass, 'Tag', '')" />
		<component xmlns="" name="{$componentClass}" generateJavaComponent="true" generateJavaRenderer="false" extendsTags="Styleable"
			parentClass="javax.faces.component.UIComponent" parentRendererClass="com.liferay.faces.portal.render.internal.PortalTagRenderer&lt;{$componentClass}, {$tagClass}&gt;">
			<description>
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[TODO.]]&gt;</xsl:text>
			</description>
			<attributes>
				<xsl:apply-templates select="attribute" />
			</attributes>
		</component>
	</xsl:template>
	<xsl:template match="/taglib/tag/attribute">
		<attribute>
			<description>
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[TODO.]]&gt;</xsl:text>
			</description>
			<name><xsl:value-of select="name" /></name>
			<type>String</type>
		</attribute>
	</xsl:template>
</xsl:stylesheet>
