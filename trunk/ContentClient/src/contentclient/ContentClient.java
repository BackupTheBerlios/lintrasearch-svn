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

import org.jdom.*;
import org.jdom.xpath.*;

import contentclient.MainWindow;
import org.linoratix.configfilereader.ConfigFileReader;

/**
 *
 * @author jfried
 */
public class ContentClient {
    protected ConfigFileReader konfiguration = null;
    
    protected MainWindow mWindow = null;
    
    /** Creates a new instance of ContentClient */
    public ContentClient(String konfigurations_datei) {
        konfiguration = new ConfigFileReader(konfigurations_datei);
        
        mWindow = new MainWindow(konfigurations_datei);
        mWindow.setVisible(true);
        new Indexer().start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ContentClient(args[0]);
    }
    
    
    class Indexer extends Thread {
        protected ArrayList indexFeatures = null;
        
        public void run() {
            checkFeatures();
            
            List verzeichnisse = konfiguration.getList("/lintra/indexer/path");
            
            for(int i=0; i<verzeichnisse.size(); i++) {
                Element e = (Element)verzeichnisse.get(i);
                indexVerzeichnis(e.getTextTrim());
            }
        }
        
        private void indexVerzeichnis(String dir) {
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println(">> Indexing: " + dir);
            }
        }
        
        private void checkFeatures() {
            Element root = new Element("lintra");
            Element action = new Element("action");
            action.setText("indexfeatures");
            root.addContent(action);

            Document doc = new Document(root);

            Document recDoc = mWindow.sendRequest(doc);

            try {
                List mimetypes = XPath.selectNodes(recDoc, "/lintra/indexfeatures/mimetype");
                Iterator iter = mimetypes.iterator();

                while(iter.hasNext()) {
                    Element child = (Element)iter.next();
                    Element suffix = child.getChild("suffix");
                    System.out.println("Suffix: " + suffix.getText());
                }
            } catch(JDOMException e) {
                System.err.println("Fehler beim XML: " + e);
            }
        }
    }
}
