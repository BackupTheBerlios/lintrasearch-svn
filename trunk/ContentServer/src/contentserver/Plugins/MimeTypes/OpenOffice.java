/*
 * OpenOffice.java
 *
 * Created on 15. Juni 2006, 16:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package contentserver.Plugins.MimeTypes;

// Import some lintrasearch classes
import contentserver.MimeTypePlugin;
import contentserver.MimeType;
import org.linoratix.base64.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 *
 * @author jfried
 */
public class OpenOffice extends MimeTypePlugin {
    // Method to register a mimetype to the server
    public ArrayList getMimeType() {
        ArrayList l = new ArrayList();
        l.add(new MimeType("application/x-zip", "odt"));
        l.add(new MimeType("application/x-zip", "odg"));
        l.add(new MimeType("application/x-zip", "ods"));
        l.add(new MimeType("application/x-zip", "odp"));
        l.add(new MimeType("application/x-zip", "odf"));
        l.add(new MimeType("application/x-zip", "odb"));
        l.add(new MimeType("application/x-zip", "odm"));
        
        return l;
    }
    
    // method to get the content of the document in text format 
    public String getContent(String _content) {
        String readDoc = null;
        StringBuffer sb = new StringBuffer();
        
        Base64.decodeToFile(_content, "/tmp/TMP1.odt");
        
        Runtime r = Runtime.getRuntime();
        
        try {
            Process p = r.exec("oo2txt /tmp/TMP1.odt");
            BufferedReader procout = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
                    );

            while((readDoc = procout.readLine()) != null)
            {
                sb.append(readDoc);
            }

            p.waitFor();
        } catch(Exception e) {
            System.err.println("Fehler: " +e);
        }
        
        return sb.toString();
    }
    
    public boolean isMultipleContent() {
        return false;
    }
}
