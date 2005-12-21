/*
 * ContentClient.java
 *
 * Created on December 18, 2005, 1:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentclient;

import java.util.*;
import java.io.*;

import org.jdom.Element;

import contentclient.MainWindow;
import org.linoratix.configfilereader.ConfigFileReader;

/**
 *
 * @author jfried
 */
public class ContentClient {
    protected ConfigFileReader konfiguration = null;
    
    /** Creates a new instance of ContentClient */
    public ContentClient(String konfigurations_datei) {
        konfiguration = new ConfigFileReader(konfigurations_datei);
        
        new Indexer().start();
        new MainWindow(konfigurations_datei).setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ContentClient(args[0]);
    }
    
    
    class Indexer extends Thread {
        public void run() {
            List verzeichnisse = konfiguration.getList("/lintra/indexer/path");
            
            for(int i=0; i<verzeichnisse.size(); i++) {
                Element e = (Element)verzeichnisse.get(i);
                indexVerzeichnis(e.getTextTrim());
            }
        }
        
        protected void indexVerzeichnis(String dir) {
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println(">> Indexing: " + dir);
            }
            
            
        }
    }
}
