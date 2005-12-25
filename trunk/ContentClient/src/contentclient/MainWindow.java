/*
 * MainWindow.java
 *
 * Created on December 18, 2005, 1:54 PM
 */

package contentclient;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import org.linoratix.configfilereader.ConfigFileReader;
import org.linoratix.base64.*;

/**
 *
 * @author  jfried
 */
public class MainWindow extends javax.swing.JFrame {
    public static final int SEARCH_EVERYTHING = 1;
    public static final int SEARCH_CONTENT = 2;
    public static final int SEARCH_USER = 3;
    public static final int SEARCH_MIMETYPE = 4;
    
    protected ErgebnisListe e_list = new ErgebnisListe();
    protected ConfigFileReader konfiguration = null;

    protected JScrollBar scrollVert = null;
    
    /** Creates new form MainWindow */
    public MainWindow(String konfigurations_datei) {
        konfiguration = new ConfigFileReader(konfigurations_datei);
        
        initComponents();
        this.setSize(640, 380);
        
        pnlAnswers.setBackground(new Color(255, 255, 255));
        
        selSuchBereich.addItem(makeObj("Alles", SEARCH_EVERYTHING));
        selSuchBereich.addItem(makeObj("Inhalt", SEARCH_CONTENT));
        selSuchBereich.addItem(makeObj("Benutzer", SEARCH_USER));
        selSuchBereich.addItem(makeObj("Dateityp", SEARCH_MIMETYPE));
        
        scrollVert = new JScrollBar(SwingConstants.VERTICAL, 0, 0, 0, 0);
        scrollVert.setSize(15, pnlAnswers.getHeight());
        scrollVert.setLocation(pnlAnswers.getWidth()-15, 0);
        
        scrollVert.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged( AdjustmentEvent event ) {
                pnlScrollAnswers.setLocation(pnlScrollAnswers.getX(), (scrollVert.getValue() * -1));
            }
        });
        
        pnlAnswers.add(scrollVert);
        
        pnlScrollAnswers.setBackground(new Color(255, 255, 255));
        pnlScrollAnswers.setLocation(0, 0);
        pnlScrollAnswers.setSize(400, 100);
        pnlAnswers.repaint();
        
        resizeElements();
    }
    
    private Object makeObj(final String text, final int type)
    {
        return new SearchType(text, type);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblStatusBar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSucheSpeichern = new javax.swing.JButton();
        txtSuchText = new javax.swing.JTextField();
        selSuchBereich = new javax.swing.JComboBox();
        btnSuchen = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();
        scrollPanel = new javax.swing.JScrollPane();
        pnlAnswers = new javax.swing.JPanel();
        pnlScrollAnswers = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuDatei = new javax.swing.JMenu();
        mnuDateiBeenden = new javax.swing.JMenuItem();
        mnuExtras = new javax.swing.JMenu();
        mnuExtrasTestFeatures = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        lblStatusBar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(lblStatusBar, "Linoratix IntraSearch -//- Client");
        getContentPane().add(lblStatusBar, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        btnSucheSpeichern.setIcon(new javax.swing.ImageIcon("/home/jfried/Projects/lIntraSearch/icons/save_search_40x40.gif"));
        org.openide.awt.Mnemonics.setLocalizedText(btnSucheSpeichern, "Suche speichern");
        btnSucheSpeichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucheSpeichernActionPerformed(evt);
            }
        });

        jToolBar1.add(btnSucheSpeichern);

        txtSuchText.setText("Suchtext eingeben...");
        jToolBar1.add(txtSuchText);

        jToolBar1.add(selSuchBereich);

        btnSuchen.setIcon(new javax.swing.ImageIcon("/home/jfried/Projects/lIntraSearch/icons/go_search_40x40.gif"));
        btnSuchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuchenActionPerformed(evt);
            }
        });

        jToolBar1.add(btnSuchen);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.NORTH);

        pnlMain.setLayout(new java.awt.BorderLayout());

        pnlAnswers.setLayout(null);

        pnlScrollAnswers.setLayout(null);

        pnlScrollAnswers.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                pnlScrollAnswersMouseWheelMoved(evt);
            }
        });

        pnlAnswers.add(pnlScrollAnswers);
        pnlScrollAnswers.setBounds(20, 20, 140, 120);

        scrollPanel.setViewportView(pnlAnswers);

        pnlMain.add(scrollPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(pnlMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(mnuDatei, "&Datei");
        mnuDatei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDateiActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(mnuDateiBeenden, "B&eenden");
        mnuDateiBeenden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDateiBeendenActionPerformed(evt);
            }
        });

        mnuDatei.add(mnuDateiBeenden);

        jMenuBar1.add(mnuDatei);

        org.openide.awt.Mnemonics.setLocalizedText(mnuExtras, "&Extras");
        org.openide.awt.Mnemonics.setLocalizedText(mnuExtrasTestFeatures, "&Features Testen");
        mnuExtrasTestFeatures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExtrasTestFeaturesActionPerformed(evt);
            }
        });

        mnuExtras.add(mnuExtrasTestFeatures);

        jMenuBar1.add(mnuExtras);

        setJMenuBar(jMenuBar1);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void mnuExtrasTestFeaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExtrasTestFeaturesActionPerformed
        Element root = new Element("lintra");
        Element action = new Element("action");
        action.setText("indexfeatures");
        root.addContent(action);
        
        Document doc = new Document(root);

        Document recDoc = sendRequest(doc);
        
        Element recRoot = recDoc.getRootElement();
        List recChildren = recRoot.getContent();
        
    }//GEN-LAST:event_mnuExtrasTestFeaturesActionPerformed

    private void pnlScrollAnswersMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_pnlScrollAnswersMouseWheelMoved
        if(evt.getWheelRotation() > 0)
        {
            scrollVert.setValue(scrollVert.getValue() + evt.getWheelRotation() + 10);
        } else {
            scrollVert.setValue(scrollVert.getValue() + evt.getWheelRotation() - 10);            
        }
    }//GEN-LAST:event_pnlScrollAnswersMouseWheelMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resizeElements();
    }//GEN-LAST:event_formComponentResized

    private void btnSuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuchenActionPerformed
        Element root = new Element("lintra");
        Element action = new Element("action");
        action.setText("search");
        Element search = new Element("search");
        Element search_string = new Element("string");
        Element search_type = new Element("type");
        search_type.setText(Integer.toString(((SearchType)selSuchBereich.getSelectedItem()).getType()));
        search_string.setText(txtSuchText.getText());
        search.addContent(search_string);
        search.addContent(search_type);
        root.addContent(action);
        root.addContent(search);
        
        Document doc = new Document(root);
        Document recDoc = sendRequest(doc);
        
        Element recRoot = recDoc.getRootElement();
        List recChildren = recRoot.getContent();
        // -- das 1. ist immer die action
        // Element recAction = (Element)recChildren.get(0);

        // erstmal alles was angezeigt wird entfernen
        e_list.removeAll();

        Element recSearch = (Element)recChildren.get(1);
        listSearchResult(recSearch);

        resizeElements();

    }//GEN-LAST:event_btnSuchenActionPerformed

    private void btnSucheSpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucheSpeichernActionPerformed

    }//GEN-LAST:event_btnSucheSpeichernActionPerformed

    private void mnuDateiBeendenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDateiBeendenActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuDateiBeendenActionPerformed

    private void mnuDateiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDateiActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_mnuDateiActionPerformed
    
    public Document sendRequest(Document doc) {
        Document recDoc = null;
        
        SAXBuilder xml_builder = null;
        xml_builder = new SAXBuilder();
        
        try {
            Socket sock = new Socket(konfiguration.get("/lintra/server/name"), new Integer(konfiguration.get("/lintra/server/port")));
            
            ObjectOutputStream os = new ObjectOutputStream(
                    sock.getOutputStream());
            
            XMLOutputter serializer = new XMLOutputter();
            
            /// DEBUG
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println("SENDE...");
                System.out.println("---------------------------------------------------------");
                serializer.output(doc, System.out);
                System.out.println("---------------------------------------------------------");
                System.out.flush();
            }
            /// -----------
            
            os.writeObject(doc);
            
            // Der ObjectInputStream kann erst hier initialisiert werden
            // weiter oben hing dat janze programm ...
            ObjectInputStream is = new ObjectInputStream(
                    sock.getInputStream());
            
            recDoc = (Document)is.readObject();
            
            /// DEBUG
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println("EMPFANGE...");
                System.out.println("---------------------------------------------------------");
                serializer.output(recDoc, System.out);
                System.out.println("---------------------------------------------------------");
                System.out.flush();
            }
            /// -----------
            
            os.close();
            is.close();
            sock.close();
        } catch(ClassNotFoundException CNFex) {
            System.err.println("Klasse nicht gefunden: " + CNFex);
        } catch(IOException e) {
            System.err.println(e);
        }
        
        return recDoc;
    }
    
    private void resizeElements() {
        pnlScrollAnswers.setSize(pnlAnswers.getWidth(), e_list.getHeight() + 10);

        if(pnlScrollAnswers.getHeight() > pnlAnswers.getHeight()) {
            if(scrollVert != null) {
                scrollVert.setVisible(true);
                pnlScrollAnswers.setSize(pnlAnswers.getWidth() - 15, e_list.getHeight() + 10);
            }
        } else {
            if(scrollVert != null)
                scrollVert.setVisible(false);            
        }
        
        if(scrollVert != null) {
            scrollVert.setSize(15, pnlAnswers.getHeight());
            scrollVert.setLocation(pnlAnswers.getWidth()-15, 0);
            
            // ausrechnen wieviel von pnlScrollAnswers versteckt ist
            int h_s = pnlScrollAnswers.getHeight();
            int h_a = pnlAnswers.getHeight();
            int versteckt = h_s - h_a;
            
            if(versteckt > 0) {
                scrollVert.setMaximum(versteckt);
            }
        }    
        
        if(e_list != null) {
            e_list.resizeWidthAll(pnlScrollAnswers.getWidth());
        }
    }
    
    private void listSearchResult(Element search_elem) {
        List children = search_elem.getContent();
        
        Iterator iter = children.iterator();
        
        while(iter.hasNext()) {
            Element e = (Element)iter.next();
            Ergebnis erg = new Ergebnis();
            
            List l = e.getContent();
            Iterator i = l.iterator();
            
            while(i.hasNext()) {
                Element el = (Element)i.next();
                
                if(el.getName().equalsIgnoreCase("docID") == true) {
                    erg.setDocID(new Integer(el.getText()));
                } else if(el.getName().equalsIgnoreCase("dateiname") == true) {
                    erg.setDateiName(el.getText());
                } else if(el.getName().equalsIgnoreCase("vonIP") == true) {
                    erg.setIP(el.getText());
                } else if(el.getName().equalsIgnoreCase("vonBenutzer") == true) {
                    erg.setBenutzer(el.getText());
                } else if(el.getName().equalsIgnoreCase("inhaltText") == true) {
                    erg.setInhaltText(el.getText());
                } else if(el.getName().equalsIgnoreCase("contentType") == true) {
                    erg.setContentType(el.getText());
                } else {
                    System.err.println("Unbekanntes Element! " + el);
                }
            }
            
            e_list.addEntry(erg);
            
            erg = null;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSucheSpeichern;
    private javax.swing.JButton btnSuchen;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblStatusBar;
    private javax.swing.JMenu mnuDatei;
    private javax.swing.JMenuItem mnuDateiBeenden;
    private javax.swing.JMenu mnuExtras;
    private javax.swing.JMenuItem mnuExtrasTestFeatures;
    private javax.swing.JPanel pnlAnswers;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlScrollAnswers;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JComboBox selSuchBereich;
    private javax.swing.JTextField txtSuchText;
    // End of variables declaration//GEN-END:variables
    
    class SearchType {
        protected String title;
        protected int type;
        
        SearchType(String _title, int _type) {
            title = _title;
            type = _type;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String toString() {
            return getTitle();
        }
        
        public int getType() {
            return type;
        }
    }
    
    class ErgebnisListe {
        protected List ergebnisse = new ArrayList();
                
        ErgebnisListe() {
            // do nothing
        }
        
        public void addEntry(Ergebnis erg) {
            ErgebnisPanel e_pnl = new ErgebnisPanel(erg);
            int top = 15;
            
            if(ergebnisse.size() > 0)
            {
                top = ((ErgebnisPanel)ergebnisse.get(ergebnisse.size() - 1)).getY() + ((ErgebnisPanel)ergebnisse.get(ergebnisse.size() - 1)).getHeight() + 15;
            }
            
            e_pnl.setLocation(15, top);
            e_pnl.setSize(pnlScrollAnswers.getWidth() - 30, 80);
            e_pnl.setBackground(new Color(230, 230, 230));
            
            // ergebnis in interne liste aufnehmen
            ergebnisse.add(e_pnl);
            
            
            // ergebnis auf screen malen
            pnlScrollAnswers.add(e_pnl);
            
            // und den screen updaten das er auch angezeigt wird
            pnlScrollAnswers.repaint();
            
            //scrollPanel.setVerticalScrollBar(scrollVert);
            //scrollPanel.repaint();
        }
        
        public void resizeWidthAll(int width) {
            Iterator iter = ergebnisse.iterator();
            
            while(iter.hasNext()) {
                ErgebnisPanel e_pnl = (ErgebnisPanel)iter.next();
                e_pnl.setSize(width - 30, e_pnl.getHeight());
            }
        }
        
        public void removeAll() {
            Iterator iter = ergebnisse.iterator();
            
            while(iter.hasNext()) {
                ErgebnisPanel e_pnl = (ErgebnisPanel)iter.next();
                e_pnl.removeAll();
                pnlScrollAnswers.remove(e_pnl);
            }
            
            ergebnisse = new ArrayList();
            
            pnlAnswers.repaint();
        }
        
        public int getHeight() {
            return ((ergebnisse.size() * 80) + (ergebnisse.size() * 15));
        }
    }
    
    class ErgebnisPanel extends javax.swing.JPanel {
        protected Ergebnis data;
        private javax.swing.JTextArea lblPreview;
        private javax.swing.JLabel lblResult1;
        
        ErgebnisPanel(Ergebnis erg) {
            data = erg;
            
            setLayout(null);

            // jLabel1.setIcon(new javax.swing.ImageIcon("/home/jfried/.lintrasearch/icons/xls.gif"));
            String[] s = erg.getDateiName().split("\\.");
            
            javax.swing.JLabel lblResultIcon = new javax.swing.JLabel();
            lblResultIcon.setIcon(new javax.swing.ImageIcon(konfiguration.get("/lintra/gui/icons") + "/"+ s[s.length - 1] +".gif"));
            lblResultIcon.setText("");
            lblResultIcon.setLocation(10, 4);
            lblResultIcon.setSize(32, 32);
            
            lblResult1 = new javax.swing.JLabel();
            lblResult1.setText(erg.getDateiName());
            lblResult1.setLocation(50, 8);
            lblResult1.setSize(300, 25);
            
            lblPreview = new javax.swing.JTextArea();
            int to = 100;
            
            if(erg.getInhaltText().length() < 100) {
                to = erg.getInhaltText().length();
            }
            
            lblPreview.setText(erg.getInhaltText().substring(0, to));
            lblPreview.setLocation(50, 30);
            lblPreview.setSize(200, 45);
            lblPreview.setBackground(new Color(230, 230, 230));
            lblPreview.setWrapStyleWord(true);
            lblPreview.setEditable(false);
            lblPreview.setLineWrap(true);

            lblPreview.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panelMouseClicked(evt);
                }
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    panelMouseEntered(evt);
                }               
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    panelMouseExited(evt);
                }
            });

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panelMouseClicked(evt);
                }
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    panelMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    panelMouseExited(evt);
                }
            });
            
            addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    lblPreview.setSize(getWidth() - 55, 45);
                }
            });
            
            // und hinzufuegen
            add(lblResultIcon);
            add(lblResult1);
            add(lblPreview);
        }
        
        protected void panelMouseEntered(java.awt.event.MouseEvent evt) {
            setBackground(new Color(240, 240, 240));
            lblPreview.setBackground(new Color(240, 240, 240));            
        }
        
        protected void panelMouseExited(java.awt.event.MouseEvent evt) {
            setBackground(new Color(230, 230, 230));
            lblPreview.setBackground(new Color(230, 230, 230));
        }
        
        protected void panelMouseClicked(java.awt.event.MouseEvent evt) {
            if(konfiguration.get("/lintra/develop/debug").equalsIgnoreCase("1") == true) {
                System.out.println("Klick auf id: " + data.getDocID());
            }
            
            Document doc = null;
            Element root = new Element("lintra");
            Element action = new Element("action");
            action.setText("getfile");
            root.addContent(action);
            
            Element getfile = new Element("getfile");
            getfile.setText(String.valueOf(data.getDocID()));
            root.addContent(getfile);
            
            doc = new Document(root);
            
            Document recDoc = sendRequest(doc);
            // /lintra/getfile/datei   /dateiname
            try {
                Element datei = (Element)XPath.selectSingleNode(recDoc, "/lintra/getfile/datei");
                Element dateiname = (Element)XPath.selectSingleNode(recDoc, "/lintra/getfile/dateiname");
                Base64.decodeToFile(datei.getText(), konfiguration.get("/lintra/downloads/path") + "/" + dateiname.getText());
            } catch(JDOMException e) {
                System.err.println("Fehler bei der XPath Abfrage: " + e);
            }
            
        }
    }
    
    class Ergebnis {
        protected int docID;
        protected String dateiname;
        protected String vonIP;
        protected String vonBenutzer;
        protected String inhaltText;
        protected String contentType;
        
        Ergebnis() {
            // do nothing
        }
        
        public void setDocID(int i) {
            docID = i;
        }
        
        public void setIP(String s) {
            vonIP = s;
        }

        public void setBenutzer(String s) {
            vonBenutzer = s;
        }

        public void setInhaltText(String s) {
            inhaltText = s;
        }

        public void setContentType(String s) {
            contentType = s;
        }
        
        public void setDateiName(String s) {
            dateiname = s;
        }
        
        public int getDocID() {
            return docID;
        }
        
        public String getIP() {
            return vonIP;
        }
        
        public String getDateiName() {
            return dateiname;
        }
        
        public String getBenutzer() {
            return vonBenutzer;
        }
        
        public String getInhaltText() {
            return inhaltText;
        }
        
        public String getContentType() {
            return contentType;
        }
    }
}
