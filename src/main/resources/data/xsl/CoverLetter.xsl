<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:letter="http://localhost:8080/CoverLetter"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:template match="/">
        <body>
            <div style="display: flex; justify-content: center; flex-direction: column;">
                <h2>Authors data:</h2>
                
                <table border="1">
                    <tr bgcolor="#0377fc">
                        <th>
                            <b>Full name</b>
                        </th>
                        <th>
                            <b>Email</b>
                        </th>
                        <th>
                            <b>Phone</b>
                        </th>
                        <th>
                            <b>Address</b>
                        </th>
                    </tr>
                    
                    <xsl:for-each select="letter:CoverLetter/letter:AuthorData">
                        <tr bgcolor="#c8e3d7">
                            <td>
                                <xsl:value-of select="letter:authorsName"/>
                            </td>
                            <td>
                                <xsl:value-of select="letter:authorsEmail"/>
                            </td>
                            <td>
                                <xsl:value-of select="@authorsPhone"/>
                            </td>
                            <td>
                                <xsl:value-of select="@authorsAddress"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                
                <h1>Paper title: <xsl:value-of select="letter:CoverLetter/@paperTitle"/></h1>
                <h1>Manuscript title: <xsl:value-of select="letter:CoverLetter/@manuscriptTitle"/></h1>
                <h1>Content: </h1>
                <h1> <xsl:value-of select="letter:CoverLetter/letter:Content"/> </h1>
            </div>
        </body>
    </xsl:template>

</xsl:stylesheet>