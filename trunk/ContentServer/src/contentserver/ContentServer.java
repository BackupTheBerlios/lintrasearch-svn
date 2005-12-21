/*
 * ContentServer.java
 *
 * Created on December 17, 2005, 12:05 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package contentserver;

import java.net.*;
import java.io.*;
import java.util.*;

import java.sql.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import org.linoratix.configfilereader.ConfigFileReader;

/**
 *
 * @author jfried
 */
public class ContentServer {
    public static final int SEARCH_EVERYTHING = 1;
    public static final int SEARCH_CONTENT = 2;
    public static final int SEARCH_USER = 3;
    public static final int SEARCH_MIMETYPE = 4;
    
    protected Connection sql_connection = null;
    
    protected ConfigFileReader konfiguration = null;
    
    /**
     * Creates a new instance of ContentServer 
     */
    public ContentServer(String konfigurations_datei, boolean only_list) {
        konfiguration = new ConfigFileReader(konfigurations_datei);
        
        if(only_list) {
            listPlugins();
            System.exit(0);
        } else {
            // MySQL Treiber laden
            try {
                Class.forName("com."+ konfiguration.get("/lintra/db/type") +".jdbc.Driver").newInstance();
            } catch (Exception e) {
                System.err.println("Fehler bei Treiberinitialisierung: " + e);
            }
            try {
                sql_connection = DriverManager.getConnection("jdbc:"+
                        konfiguration.get("/lintra/db/type")+"://"+
                        konfiguration.get("/lintra/db/server")+"/"+
                        konfiguration.get("/lintra/db/name")+"?user="+
                        konfiguration.get("/lintra/db/user")+"&password="+
                        konfiguration.get("/lintra/db/password"));
            } catch(SQLException e) {
                System.err.println("SQL Fehler: " + e);
            }
        }
    }
    
    public void runServer() {
        ServerSocket sock = null;
        Socket clientSocket = null;
        
        try {
            sock = new ServerSocket(new Integer(konfiguration.get("/lintra/server/port")));
            
            System.out.println("ContentServer started...");
            
            while(true) {
                clientSocket = sock.accept();
                new Handler(clientSocket).start();
            }
        } catch(IOException e) {
            System.err.println("Konnte keine Verbindung herstellen: " + e);
            System.exit(1);
        }
    }
    
    private Document processRequest(Document doc) {
        Document retDocument = null;
        
        Element root = doc.getRootElement();
        List children = root.getContent();
        
        Element action = (Element)children.get(0);
        
        if(action.getText().equalsIgnoreCase("search") == true) {
            // es soll gesucht werden
           retDocument = searchDB((Element)children.get(1));
        } else if(action.getText().equalsIgnoreCase("indexfeatures") == true) {
           retDocument = getIndexFeatures();
        }
        
        return retDocument;
    }
    
    private Document searchDB(Element search_elem) {
        List children = search_elem.getContent();
        
        Document retDocument = null;
        
        int i = new Integer(((Element)children.get(1)).getText());
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            switch(i) {
                case SEARCH_EVERYTHING:
                    stmt = sql_connection.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM dokumente WHERE dateiname LIKE '%"
                            + ((Element)children.get(0)).getText() 
                            + "%' OR inhaltText LIKE '%"
                            + ((Element)children.get(0)).getText() 
                            + "%' OR vonBenutzer LIKE '%"
                            + ((Element)children.get(0)).getText() 
                            + "%' OR vonIP='"
                            + ((Element)children.get(0)).getText() 
                            + "' OR contentType LIKE '%"
                            + ((Element)children.get(0)).getText() 
                            + "%'");
                    break;
            }
            
            // jetzt die ergebnisse aufbereiten
            Element root = new Element("lintra");
            Element action = new Element("action");
            action.setText("search");
            root.addContent(action);
            
            Element search = new Element("search");
            
            while(rs.next()) {
                Element result = new Element("result");
                Element docID = new Element("docID");
                Element dateiname = new Element("dateiname");
                Element vonIP = new Element("vonIP");
                Element vonBenutzer = new Element("vonBenutzer");
                Element inhaltText = new Element("inhaltText");
                Element contentType = new Element("contentType");
                
                docID.setText(Integer.toString(rs.getInt("docID")));
                dateiname.setText(rs.getString("dateiname"));
                vonIP.setText(rs.getString("vonIP"));
                vonBenutzer.setText(rs.getString("vonBenutzer"));
                inhaltText.setText(rs.getString("inhaltText"));
                contentType.setText(rs.getString("contentType"));
                
                result.addContent(contentType);
                result.addContent(inhaltText);
                result.addContent(vonBenutzer);
                result.addContent(vonIP);
                result.addContent(dateiname);
                result.addContent(docID);
                
                search.addContent(result);
            }
            
            root.addContent(search);
            retDocument = new Document(root);
        } catch(SQLException sqlEx) {
            System.err.println("Fehler im SELECT: " + sqlEx);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException sqlEx) {
                    // sollte nicht auftrehten und wenn auch erstmal egal
                }
                
                rs = null;
            }
            
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException sqlEx) {
                    // auch ignorieren
                }
                
                stmt = null;
            }
        }
        
        return retDocument;
    }
    
    private void listPlugins() {
        List plugin_list = konfiguration.getList("/lintra/plugins/mimetype/plugin");
        Iterator iter = plugin_list.iterator();
        
        System.out.println("MimeTypePlugins:");
        System.out.println("--------------------------------------------------------------------------------");
        
        while(iter.hasNext()) {
            Element plugin = (Element)iter.next();
            MimeTypePlugin mtp = null;
            
            try {
                Class MimeTypePluginClass = Class.forName(plugin.getText());
                Object MimeTypePluginObject = MimeTypePluginClass.newInstance();
                mtp = (MimeTypePlugin)MimeTypePluginObject;
            } catch(Exception e) {
                System.err.println("Konnte Plugin nicht laden: " + e);
            }
            
            System.out.println("Name: " + plugin.getText());
            System.out.println("MimeType(s): " + mtp.getMimeType().toString());
        }
        
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");
    }
    
    private void IndexDocument(Element docToIndex) {
        
    }
    
    private Document getIndexFeatures() {
        Document retDocument = null;
        
        Element root = new Element("lintra");
        Element action = new Element("action");
        action.setText("indexfeatures");
        root.addContent(action);
        
        Element indexfeatures = new Element("indexfeatures");
        
        List plugin_list = konfiguration.getList("/lintra/plugins/mimetype/plugin");
        Iterator iter = plugin_list.iterator();
        
        while(iter.hasNext()) {
            Element plugin = (Element)iter.next();
            MimeTypePlugin mtp = null;
            
            try {
                Class MimeTypePluginClass = Class.forName(plugin.getText());
                Object MimeTypePluginObject = MimeTypePluginClass.newInstance();
                mtp = (MimeTypePlugin)MimeTypePluginObject;
            } catch(Exception e) {
                System.err.println("Konnte Plugin nicht laden: " + e);
            }
            
            Element eMimeType = new Element("mimetype");
            ArrayList l = (ArrayList)mtp.getMimeType();
            Iterator i = l.iterator();
            
            while(i.hasNext()) {
                MimeType m = (MimeType)i.next();
                
                Element rSuffix = new Element("suffix");
                rSuffix.setText(m.getSuffix());
                Element rMimeType = new Element("mimetype");
                rMimeType.setText(m.getMimeType());
                
                eMimeType.addContent(rSuffix);
                eMimeType.addContent(rMimeType);
            }
            
            indexfeatures.addContent(eMimeType);
            
            eMimeType = null;
        }
        
        root.addContent(indexfeatures);
        
        retDocument = new Document(root);
        
        return retDocument;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length > 1) {
            if(args[1].equalsIgnoreCase("--listPlugins") == true)
                new ContentServer(args[0], true).runServer();
        } else {
            new ContentServer(args[0], false).runServer();
        }
    }  

    /********************************************/
    /********************************************/
    /**** Handler Klasse
     * nimmt die Clientverbindungen entgegen und verarbeitet sie
     */
    class Handler extends Thread {
        protected Socket sock;
        
        Handler(Socket s) {
            sock = s;
        }
        
        public void run() {
            SAXBuilder xml_builder = null;
            xml_builder = new SAXBuilder();
        
            System.out.println("Socket gestartet...: " + sock);
            Document doc;
            
            try {
                ObjectInputStream is = new ObjectInputStream(
                        sock.getInputStream());
                
                ObjectOutputStream os = new ObjectOutputStream(
                        sock.getOutputStream());
                
                String line;
                
                doc = processRequest((Document)is.readObject());
                
                XMLOutputter serializer = new XMLOutputter();
                /// DEBUG
                System.out.println("SENDE...");
                System.out.println("---------------------------------------------------------");
                serializer.output(doc, System.out);
                System.out.println("---------------------------------------------------------");
                /// -----------                

                os.writeObject(doc);
                
                is.close();
                os.close();
                sock.close();
            } catch(ClassNotFoundException CNFex) {
                System.err.println("Fehler beim Lesen des Objekts Nachricht: " + CNFex);
            } catch (IOException e) {
                System.err.println("Socketfehler: " + e);
            }
            
            System.out.println("Thread BEENDET! " + sock);
        }
    }
    
}
