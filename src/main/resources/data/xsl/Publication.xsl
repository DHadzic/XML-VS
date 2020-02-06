<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:publication="http://foo.bar"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:template match="/">
        <body>
            <h1 ><xsl:value-of select="publication:SciencePaper/publication:basicInformations/publication:title"/></h1>
            <xsl:for-each select="publication:SciencePaper/publication:basicInformations/publication:authors">
            	<h3><xsl:value-of select="publication:authorUsename"/></h3>
            	<h3><xsl:value-of select="publication:authorInstitution"/></h3>
            </xsl:for-each>
            <xsl:for-each select="publication:SciencePaper/publication:paragraph">
            	<h2><xsl:value-of select="publication:paragraphTitle"/></h2>
            	<p>
	            	<xsl:for-each select="publication:element">
	            		<xsl:choose>
	            			<xsl:when test="boolean(publication:text)">
	            				<xsl:value-of select="publication:text"/>
	            			</xsl:when>
	            			<xsl:when test="boolean(publication:citation)">
	            				<xsl:value-of select="publication:citation/@id"/>
	            				<xsl:value-of select="publication:citation/publication:text"/>
	            				<xsl:for-each select="publication:citation/publication:authorName">
	            					<xsl:value-of select="."/>
	            					,
	            				</xsl:for-each>
	            				(<xsl:value-of select="publication:citation/publication:year"/>)
	            				
	            			</xsl:when>
	            		</xsl:choose>
	           		</xsl:for-each>
           		</p>
            </xsl:for-each >
            <xsl:for-each select="publication:SciencePaper/publication:reference">
            	<p>
            		<xsl:value-of select="publication:paperTitle"/>. 
            		<xsl:value-of select="publication:authorName"/>
					(<xsl:value-of select="publication:year"/>)
            	</p>
            </xsl:for-each >
            keywords:
            <xsl:for-each select="publication:SciencePaper/publication:basicInformations/publication:keywords">
            	<p><xsl:value-of select="."/></p> 
            	
            </xsl:for-each>
        </body>
    </xsl:template>

</xsl:stylesheet>