<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:reviews="http://localhost:8080/Reviews"
    xmlns:review="http://localhost:8080/Review"
    exclude-result-prefixes="xs"
    version="2.0">
    
    <xsl:template match="/">
        <xsl:for-each select="reviews:reviews">
            <p>Review num. <xsl:value-of select="position()"/> </p>
            <body>
                <div style="display: flex; justify-content: center; flex-direction: column;">
                    <h2>Authors :</h2>
                    <xsl:for-each select="review:Review/review:authors">
                        <p> <xsl:value-of select="review:username"/> </p>
                    </xsl:for-each>
                    
                    <h1>Comment : <xsl:value-of select="review:Review/review:comment"/></h1>
                    <h1>Rates: </h1>
                    <h1>Subject rate : <xsl:value-of select="review:Review/review:rateSubject"/></h1>
                    <h1>Readability rate : <xsl:value-of select="review:Review/review:rateReadability"/></h1>
                    <h1>Originality rate : <xsl:value-of select="review:Review/review:rateOriginality"/></h1>
                    <h1>Reviwed by: <xsl:value-of select="review:Review/@reviewedBy"/> </h1>
                </div>
            </body>
        </xsl:for-each>
    </xsl:template>
    
    
</xsl:stylesheet>