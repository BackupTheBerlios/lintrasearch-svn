/*
 * MimeType.java
 *
 * Created on December 21, 2005, 8:43 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentserver;

/**
 *
 * @author jfried
 */
public class MimeType {
    
    protected String mimetype;
    protected String suffix;
    
    /** Creates a new instance of MimeType */
    public MimeType(String _mimetype, String _suffix) {
        mimetype = _mimetype;
        suffix = _suffix;
    }
    
    public String getMimeType() {
        return mimetype;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public String toString() {
        return mimetype + " <" + suffix + ">";
    }
}
