/*
 * TextFile.java
 *
 * Created on December 21, 2005, 8:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentserver.Plugins.MimeTypes;

import contentserver.MimeTypePlugin;
import contentserver.MimeType;

import java.util.*;
import java.io.*;

import org.linoratix.base64.*;

/**
 *
 * @author jfried
 */
public class TextFile extends MimeTypePlugin {
    public ArrayList getMimeType() {
        ArrayList l = new ArrayList();
        l.add(new MimeType("text/plain", "txt"));
        
        return l;
    }
    
    public String getContent(String _content) {
        StringBuilder sb = new StringBuilder();
        byte[] inhalt = Base64.decode(_content);
        for(int i = 0; i < inhalt.length; i++) {
            sb.append((char)inhalt[i]);
        }
        
        return sb.toString();
    }
}
