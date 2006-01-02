/*
 * TextFile.java
 *
 * Created on December 21, 2005, 8:36 AM
 *
 * Index Textfiles
 */

package contentserver.Plugins.MimeTypes;

// Import some lintrasearch classes
import contentserver.MimeTypePlugin;
import contentserver.MimeType;
import org.linoratix.base64.*;

import java.util.*;

public class TextFile extends MimeTypePlugin {
    
    // Method to register a mimetype to the server
    public ArrayList getMimeType() {
        ArrayList l = new ArrayList();
        l.add(new MimeType("text/plain", "txt"));
        
        return l;
    }
    
    // method to get the content of the document in text format 
    public String getContent(String _content) {
        StringBuilder sb = new StringBuilder();
        byte[] inhalt = Base64.decode(_content);
        for(int i = 0; i < inhalt.length; i++) {
            sb.append((char)inhalt[i]);
        }
        
        return sb.toString();
    }
    
    public boolean isMultipleContent() {
        return false;
    }
}
