<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:user="http://localhost:8080/User"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs" version="2.0">
    
    <xsl:template match="/">
        <body>
            <div style="display: flex; justify-content: center; flex-direction: column;">
                <h2>User info</h2>
                
                <table border="1">
                    <tr bgcolor="#0377fc">
                        <th>
                            <b>Username</b>
                        </th>
                        <th>
                            <b>Full name</b>
                        </th>
                        <th>
                            <b>Email</b>
                        </th>
                        <th>
                            <b>Role in system</b>
                        </th>
                    </tr>
                    
                    <tr bgcolor="#c8e3d7">
                        <td>
                            <xsl:value-of select="user:user/user:username"/>
                        </td>
                        <td>
                            <xsl:value-of select="user:user/user:fullName"/>
                        </td>
                        <td>
                            <xsl:value-of select="user:user/user:email"/>
                        </td>
                        <td>
                            <xsl:value-of select="user:user/user:role"/>
                        </td>
                    </tr>
                </table>
            </div>
        </body>
    </xsl:template>
    
</xsl:stylesheet>