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
        
        String patt = "<.*?>";
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(sb);
        
        m.reset();
        while(m.find()) {
            m.appendReplacement(sb, "");
        }
        
        m.appendTail(sb);
        
        return sb.toString();
    }
    
    public boolean isMultipleContent() {
        return false;
    }
}
