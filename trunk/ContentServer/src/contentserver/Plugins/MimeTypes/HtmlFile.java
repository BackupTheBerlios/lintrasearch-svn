/*
 * HtmlFile.java
 *
 * Created on 2. Januar 2006, 16:53
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentserver.Plugins.MimeTypes;

// Import some lintrasearch classes
import contentserver.MimeTypePlugin;
import contentserver.MimeType;
import org.linoratix.base64.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class HtmlFile extends MimeTypePlugin {
    
    // Method to register a mimetype to the server
    public ArrayList getMimeType() {
        ArrayList l = new ArrayList();
        l.add(new MimeType("text/html", "html"));
        l.add(new MimeType("text/html", "htm"));
        l.add(new MimeType("text/html", "shtml"));
        l.add(new MimeType("text/html", "html"));
        
        return l;
    }
    
    // method to get the content of the document in text format 
    public String getContent(String _content) {
        StringBuffer sb = new StringBuffer();
        byte[] inhalt = Base64.decode(_content);
        for(int i = 0; i < inhalt.length; i++) {
            sb.append((char)inhalt[i]);
        }
        
        Properties replacingProperties = new Properties ();
        try {
            replacingProperties.load (
                new BufferedInputStream (new FileInputStream(konfiguration.get("/lintra/files/configuration/plugins") + "/html2txt.rpl")));
        } catch (final IOException exitException) {
            System.err.println ("Fehler beim Laden der Ersetzungstabelle." + exitException);
            System.exit (-1);
        }
        
        StringBuffer inputString = sb;
        Enumeration replacingStrings = replacingProperties.keys();

        while (replacingStrings.hasMoreElements()) {
            final String codingString = replacingStrings.nextElement().toString();
            final Pattern p = Pattern.compile (codingString);
            Matcher m = p.matcher(inputString);
            if (m.find()) {
                inputString = new StringBuffer (m.replaceAll (replacingProperties.getProperty(codingString)));
            }
        }
        
        Pattern htmlTagPattern = Pattern.compile("<[^>]*>|\\s{2,}");
        String [] preText = htmlTagPattern.split (inputString);
        StringBuffer retBuf = new StringBuffer();
        
        for (int i = 0; i < preText.length; i++) {
            retBuf.append(preText[i]);
        }
        
        return retBuf.toString();
    }
    
    public boolean isMultipleContent() {
        return false;
    }
}
