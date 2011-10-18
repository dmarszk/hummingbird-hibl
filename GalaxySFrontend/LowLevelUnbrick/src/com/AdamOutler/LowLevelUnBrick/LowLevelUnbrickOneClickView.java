/*
 * Copyright (c) 2011 Adam Outler
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.AdamOutler.LowLevelUnBrick;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * The application's main frame.
 */
public class LowLevelUnbrickOneClickView extends FrameView {
    FileOperations fileOperations = new FileOperations();
    /**
     * 250ms
     */
    public final static int QUARTER_SECOND = 250;
    /**
     * 1000ms
     */
    public final static int ONE_SECOND = 1000;   
    private static boolean ButtonStatusChangedRequested=true; //used by timer on form oneclickview
    private static boolean LabelStatusChangedRequested=true; // used by timer on form oneclickview
   
    
    Log Log = new Log();
    Shell ShellCommand = new Shell();
    

    
  

    
    public LowLevelUnbrickOneClickView(SingleFrameApplication app, boolean UseTGZFormat) {
        super(app);
        Statics.UseTGZFormat=UseTGZFormat;
        initComponents();
        this.getFrame().setTitle("Ultimate UnBrickable Resurrector- Galaxy Edition"); 
    }
    
    /**
     * 
     */
    @Action
    public void showAboutBox() {
            }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        Statics.HeimdallOneClickView=this;
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        ConnectedLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setMinimumSize(new java.awt.Dimension(650, 650));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new java.awt.Dimension(651, 520));
        mainPanel.setVerifyInputWhenFocusTarget(false);
        Statics.HeimdallOneClickView=this;

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.AdamOutler.LowLevelUnBrick.LowLevelUnbrickOneClickApp.class).getContext().getResourceMap(LowLevelUnbrickOneClickView.class);
        jButton2.setText(resourceMap.getString("Flash.text")); // NOI18N
        jButton2.setToolTipText(resourceMap.getString("Flash.toolTipText")); // NOI18N
        jButton2.setFocusPainted(false);
        jButton2.setName("Flash"); // NOI18N
        jButton2.setRequestFocusEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jScrollPane2.viewportBorder.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jScrollPane2.viewportBorder.titleFont"))); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setBackground(resourceMap.getColor("jTextArea2.background")); // NOI18N
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText(resourceMap.getString("jTextArea2.text")); // NOI18N
        jTextArea2.setBorder(null);
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea2.setName("jTextArea2"); // NOI18N
        Statics.ProgressArea=this.jTextArea2;
        jScrollPane2.setViewportView(jTextArea2);

        ConnectedLabel.setLabelFor(ConnectedLabel);
        ConnectedLabel.setName("ConnectedLabel"); // NOI18N

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConnectedLabel)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConnectedLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addContainerGap())
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

       
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileOperations FileOperations=new FileOperations();
        
        Log.level0("\n\n Please wait.... Uploading..");
        Log.level0("-------------------------------------------------------------\n   Hummingbird Interceptor Boot Loader (HIBL) v1.0\n   Copyright (C) Rebellos 2011\n-------------------------------------------------------------\n");
        
        if (Statics.isLinux()){
            Shell Shell = new Shell();
            String[] arch={"arch"};
            String ArchReturn=Shell.sendShellCommand(arch);
            String SMDK="";
            if (ArchReturn.contains("64")){
                SMDK = Statics.TempFolder+"UnBrickPack"+Statics.Slash+"smdk-usbdl64";
            } else {
                SMDK = Statics.TempFolder+"UnBrickPack"+Statics.Slash+"smdk-usbdl";
            }
            FileOperations.setExecutableBit(SMDK);
            Log.level1("Building command list");
            String Command = SMDK +" -f " + Statics.TempFolder+"UnBrickPack"+
                    Statics.Slash+"HIBL.bin -a D0020000;\n" + "sleep 3;\n"
                    + SMDK +" -f " + Statics.TempFolder+"UnBrickPack"+
                    Statics.Slash+"Sbl.bin -a 40244000";
            if (FileOperations.verifyFileExists(Statics.TempFolder+"Script.sh")){
                Log.level1("Clearing Previous Instance");
                FileOperations.deleteFile(Statics.TempFolder+"Script.sh");
            }
            try {
                Log.level1("Building command list");
                FileOperations.writeToFile(Command, Statics.TempFolder+"Script.sh");
            } catch (IOException ex) {
                Logger.getLogger(LowLevelUnbrickOneClickView.class.getName()).log(Level.SEVERE, null, ex);
                Log.level0("ERROR WRITING TO TEMP FOLDER");
            }
            Log.level1("Requesting Permission to access device");
           
            Statics.LiveSendCommand=new ArrayList();
            Statics.LiveSendCommand.add("gksudo");
            Statics.LiveSendCommand.add(Statics.TempFolder+"Script.sh");
            FileOperations.setExecutableBit(Statics.TempFolder+"Script.sh");
            Shell.liveShellCommand();
            
            
            
            // 402244000
            
            //Log.level1(Command.toString());
            
        } else {
            TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
            int DResult= timeOutOptionPane.showTimeoutDialog(
                 5, //timeout
                 null, //parentComponent
                 "This app will only work under Linux. This app has not been designed\n"
                    + "to work with any other OS.",
                 "Linux Not Detected",  //DisplayTitle
                 TimeOutOptionPane.OK_OPTION, // Options buttons
                 TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
                 new String[]{"OK"}, // option buttons
                 "OK"); //Default{
        }
           

    }//GEN-LAST:event_jButton2ActionPerformed
  
    public void enableButtons(boolean State){

    }
    private static void launchLink(String Link){
        LinkLauncher LinkLauncher=new LinkLauncher();
        LinkLauncher.launchLink(Link);
    }
    public static void showDonateLink(){
        TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
            int DResult= timeOutOptionPane.showTimeoutDialog(
                 5, //timeout
                 null, //parentComponent
                 "Would you like to make a donation?\n"
                 + "Donations give developers a tangeble reason to continue quality software development\n",
                 "Donate to the developers",  //DisplayTitle
                 TimeOutOptionPane.OK_OPTION, // Options buttons
                 TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
                 new String[]{"Donate To AdamOutler", "No"}, // option buttons
                 "No"); //Default{
            if ( DResult == 1 ){
                launchLink("http://forum.xda-developers.com/donatetome.php?u=2710388");
            } else if (DResult == 2){
                launchLink("http://forum.xda-developers.com/donatetome.php?u=3682533");
            } else if (DResult == 3){
            }
    }
    
  
    /**
     * 
     * @param path
     * @param description
     * @return
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConnectedLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

}

