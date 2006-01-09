/*
 * MimeTypePlugin.java
 *
 * Created on December 21, 2005, 12:31 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentserver;

import org.linoratix.configfilereader.ConfigFileReader;
import java.util.*;

/**
 *
 * @author jfried
 */
public abstract class MimeTypePlugin {
    public ConfigFileReader konfiguration = null;
    
    // In dieser Methode werden die MimeTypes bekannt gegeben
    public ArrayList getMimeType() {
        return null;
    }
    
    public String getContent(String _content) {
        return null;
    }
    
    public String[] getMultipleContent(String _content) {
        return null;
    }
    
    public String[] getMultipleBinaryContent(String _content) {
        return null;
    }
    
    public boolean isMultipleContent() {
        return false;
    }
    
    public void setConfiguration(ConfigFileReader k) {
        konfiguration = k;
    }
}
