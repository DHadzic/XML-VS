<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:r="http://localhost:8080/Reviews"
    exclude-result-prefixes="xs"
    version="2.0">
    
    <xsl:template match="/">
       <body>
       		<xsl:for-each select="r:reviews/r:reviews">
            	<p>Review num. <xsl:value-of select="position()"/> </p>
                <div style="display: flex; justify-content: center; flex-direction: column;">
                    <h2>Authors :</h2>
                    <xsl:for-each select="r:authors">
                        <p> <xsl:value-of select="r:username"/> </p>
                    </xsl:for-each>
                    
                    <h1>Comment : <xsl:value-of select="r:comment"/></h1>
                    <h1>Rates: </h1>
                    <h1>Subject rate : <xsl:value-of select="r:rateSubject"/></h1>
                    <h1>Readability rate : <xsl:value-of select="r:rateReadability"/></h1>
                    <h1>Originality rate : <xsl:value-of select="r:rateOriginality"/></h1>
                    <h1>Reviwed by: <xsl:value-of select="@reviewedBy"/> </h1>
                </div>
    	    </xsl:for-each>
	   </body>
    </xsl:template>
    
    
</xsl:stylesheet>