/*
 * ConfigFileReader.java
 *
 * Created on December 19, 2005, 5:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.linoratix.configfilereader;

import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.input.SAXBuilder;

import java.util.*;
import java.io.*;

/**
 *
 * @author jfried
 */
public class ConfigFileReader {
    
    protected Document doc = null;
    
    /** Creates a new instance of ConfigFileReader */
    public ConfigFileReader(String filename) {
        SAXBuilder builder = new SAXBuilder();
        
        try {
            doc = builder.build(filename);
        } catch(JDOMException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e);
        } catch(IOException e) {
            System.err.println("Allgemeiner Lesefehler: " + e);
        }
    }
    
    public String get(String path) {
        Element e = null;
        
        try {
            e = (Element)XPath.selectSingleNode(doc, path);
        } catch(JDOMException ex) {
            System.err.println("Fehler beim verarbeiten der XML Abfrage: " + ex);
        }
        
        return e.getText();
    }
    
    public List getList(String path) {
        List l = null;
        
        try {
            l = XPath.selectNodes(doc, path);
        } catch(JDOMException e) {
            System.err.println("Fehler beim verarbeiten der XML Abfrage: " + e);
        }
        
        return l;
    }
}
