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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

import javax.activation.MimetypesFileTypeMap;

import org.jdom.*;
import org.jdom.xpath.*;

import contentclient.MainWindow;
import org.linoratix.configfilereader.ConfigFileReader;
import org.linoratix.base64.*;

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
            try {
                // jedes verzeichnis wird erst mit ner halben sekunde verzoegerung durchwandert
                // die lasst vom system nehmen
                Thread.sleep(500);
            } catch(InterruptedException iEx) {
                System.err.println("Konnte einfach nicht warten...");
            }
            
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println(">> Indexing: " + dir);
            }
            
            File[] dateien = new java.io.File(dir).listFiles(new OnlyAcceptedFiles());
            
            for(int i = 0; i < dateien.length; i++) {
                if(dateien[i].isDirectory()) {
                    indexVerzeichnis(dateien[i].getPath());
                } else {
                    Document doc = null;
                    
                    Element root = new Element("lintra");
                    Element action = new Element("action");
                    action.setText("indexfile");
                    root.addContent(action);
                    
                    Element indexfile = new Element("indexfile");
                    Element dateiname = new Element("dateiname");
                    Element vonBenutzer = new Element("vonBenutzer");
                    Element inhaltBinaer = new Element("inhaltBinaer");
                    Element contentType = new Element("contentType");
                    
                    dateiname.setText(dateien[i].getName());
                    vonBenutzer.setText(System.getProperty("user.name"));
                    
                    try {
                        InputStream in = new Base64.InputStream(
                                new FileInputStream(dateien[i].getPath()));
                        StringBuffer sb = new StringBuffer();
                        int ch;
                        while((ch = in.read()) > -1) {
                            sb.append((char)ch);
                        }
                        in.close();
                        inhaltBinaer.setText(sb.toString());
                    } catch(IOException e) {
                        System.err.println("Konnte Datei nicht lesen: " + e);
                    }
                    
                    contentType.setText(new MimetypesFileTypeMap().getContentType(dateien[i]));
                    
                    indexfile.addContent(dateiname);
                    indexfile.addContent(vonBenutzer);
                    indexfile.addContent(inhaltBinaer);
                    indexfile.addContent(contentType);
                    
                    root.addContent(indexfile);
                    
                    doc = new Document(root);
                    Document recDoc = mWindow.sendRequest(doc);
                }
            }
        }
        
        private void checkFeatures() {
            indexFeatures = new ArrayList();
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
                    indexFeatures.add(suffix.getText());
                }
            } catch(JDOMException e) {
                System.err.println("Fehler beim XML: " + e);
            }
        }
        
        class OnlyAcceptedFiles implements FilenameFilter {
            public boolean accept(File dir, String s) {
                String teile[] = s.split("\\.");

                Iterator iter = indexFeatures.iterator();
                while(iter.hasNext()) {
                    String endung = (String)iter.next();
                    File f = new File(dir.getAbsolutePath() + "/" +  s);
                    if(endung.equalsIgnoreCase(teile[teile.length-1])
                        || f.isDirectory()) {
                        return true;
                    }
                }
                
                return false;
            }
        }

    }
}
