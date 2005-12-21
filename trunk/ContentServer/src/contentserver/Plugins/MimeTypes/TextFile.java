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

/**
 *
 * @author jfried
 */
public class TextFile extends MimeTypePlugin {
    public ArrayList getMimeType() {
        ArrayList l = new ArrayList();
        l.add(new MimeType("text/text", "txt"));
        
        return l;
    }
    
    public String getContent(byte[] _content) {
        String c = new String(_content);
        return c;
    }
}
