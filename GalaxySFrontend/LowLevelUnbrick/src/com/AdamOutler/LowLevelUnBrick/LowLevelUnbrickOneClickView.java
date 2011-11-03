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




import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import java.util.ArrayList;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.ImageIcon;
/**
 * The application's main frame.
 */
public class LowLevelUnbrickOneClickView extends FrameView {
    private static Shell ShellCommand=new Shell();
    FileOperations fileOperations = new FileOperations();
    /**
     * 250ms
     */
    private final int QUARTER_SECOND = 250; //ms
    /**
     * 1000ms
     */
    public final static int ONE_SECOND = 1000;   
    private static boolean ButtonStatusChangedRequested=true; //used by timer on form oneclickview
    private static boolean LabelStatusChangedRequested=true; // used by timer on form oneclickview
   
    
    Log Log = new Log();

    

    
    final public static String DebugMode="resources/images/DebugMode.jpg";
    final public static String S5PC110="resources/images/S5PC110.jpg";
    final public static String DeviceNotFound="resources/images/UnbrickableApp.png";
    final public static String DownloadMode="resources/images/DownloadMode.jpg";
    final public static String MassStorage="resources/images/MassStorage.jpg";
    final public static String MediaPlayer="resources/images/MediaPlayer.jpeg";
    final public static String SamsungKies="resources/images/SamsungKies.jpg";
 
    Timer monitoringTimer = new Timer(QUARTER_SECOND, new ActionListener() {
        
        public void actionPerformed(ActionEvent evt) {
            
            String Result= ShellCommand.silentShellCommand(new String[] {"lsusb"});
            if (Result.contains("04e8:1234")){
               jLabel2.setText("S5PC110 detected");
               jLabel3.setText("Button Enabled- Upload Download Mode now");
               jLabel1.setIcon(createImageIcon(S5PC110, "S5PC110 Mode"));
            } else {
                if (Result.contains("04e8:6601")){
                   jLabel1.setIcon(createImageIcon(DownloadMode, "Download Mode."));
                   jLabel2.setText("Download Mode");
                   jLabel3.setText("Start Firmware Download with Heimdall or Odin");
                } else if (Result.contains("04e8:6877")){
                   jLabel1.setIcon(createImageIcon(SamsungKies, "Samsung Kies."));
                   jLabel2.setText("Samsung Kies");
                   jLabel3.setText("Kies Mode detected");
                } else if (Result.contains("04e8:68a9")){
                   jLabel1.setIcon(createImageIcon(MediaPlayer, "Media Player."));
                   jLabel2.setText("Media Player");
                   jLabel3.setText("Samsung Media Player");
                } else if (Result.contains("04e8:681d")){
                   jLabel1.setIcon(createImageIcon(MassStorage, "Mass Storage."));
                   jLabel3.setText("Mass storage device");
                   jLabel2.setText("Mass Storage");
                } else if (Result.contains("04e8:681c")){
                   jLabel1.setIcon(createImageIcon(DebugMode, "Debug Mode."));
                   jLabel3.setText("Android Debug Bridge");
                   jLabel2.setText("Debug Mode Detected");
                } else if (Result.contains("04e8:684e")){
                   jLabel1.setIcon(createImageIcon(DeviceNotFound, "Samsung GMO Modem"));
                   jLabel3.setText("Samsung GMO Modem");
                   jLabel2.setText("Debug Mode");
                } else if (Result.contains("18d1:4e20")){
                   jLabel1.setIcon(createImageIcon(DeviceNotFound, "Fastboot"));
                   jLabel3.setText("Fastboot");
                   jLabel2.setText("Fastboot mode");
                } else {
                   jLabel1.setIcon(createImageIcon(DeviceNotFound, "The device is connected."));
                   jLabel3.setText("No device detected");
                   jLabel2.setText("Device not found");
                }
            }

            monitoringTimer.start();
        }
    });
    
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    
    public LowLevelUnbrickOneClickView(SingleFrameApplication app, boolean UseTGZFormat) {
        super(app);
        Statics.UseTGZFormat=UseTGZFormat;
        initComponents();
        Dimension MinSize = new Dimension(670, 550);
        Dimension RecSize = new Dimension(670, 600);
        this.getFrame().setMinimumSize(MinSize);
        this.getFrame().setSize(RecSize);
        this.getFrame().setTitle("Ultimate UnBrickable Resurrector- Galaxy Infusion Edition"); 
        Statics Statics = new Statics();
        if (Statics.isLinux()){
            monitoringTimer.start();
        } else {
            TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
            int DResult= timeOutOptionPane.showTimeoutDialog(
                10, //timeout
                null, //parentComponent
                "This app will only work under Linux. This app has not been designed\n"
                + "to work with any other OS.",
                "Linux Not Detected",  //DisplayTitle
                TimeOutOptionPane.OK_OPTION, // Options buttons
                TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
                new String[]{"OK"}, // option buttons
                "OK"); //Default{
        }
       
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setMinimumSize(new java.awt.Dimension(651, 450));
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
        jTextArea2.setFont(resourceMap.getFont("jTextArea2.font")); // NOI18N
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

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ConnectedLabel)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(ConnectedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(50, 50, 50))
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

       
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileOperations FileOperations=new FileOperations();
        
        Log.level1("\n\n Begin Resurrection\n");
     
        if (Statics.isLinux()){
            Shell Shell = new Shell();
            String[] arch={"arch"};
            String ArchReturn=Shell.sendShellCommand(arch);
            String SMDK="";
            if (ArchReturn.contains("64")){
                Log.level1("64Bit detected");
                SMDK = Statics.TempFolder+"UnBrickPack"+Statics.Slash+"smdk-usbdl64";
            } else {
                Log.level1("32Bit detected");
                SMDK = Statics.TempFolder+"UnBrickPack"+Statics.Slash+"smdk-usbdl";
            }
 
            if (FileOperations.verifyFileExists(SMDK)){
                Log.level1("Verified Binary:" + SMDK);
            } else {
                if (FileOperations.verifyFileExists(SMDK)){
                    Log.level1("Error: Could not find " + SMDK);
                }
            }
            if (FileOperations.setExecutableBit(SMDK)){
                Log.level1("Set Executable Bit " + SMDK);
            } else {
                Log.level1("Error: Could not Set Executable Bit " + SMDK);
            }
            String Command = SMDK +" -f " + Statics.TempFolder+"UnBrickPack"+
                    Statics.Slash+"HIBL.bin -a D0020000;\n test $? && echo  \\\\n Interceptor Injection Complete.  Injecting modified SBL\\\\n\\\\n||echo Interceptor Injection Failure!!!\\\\n\\\\n;\n  sleep 3;\n"
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
            Log.level0("\n Please wait.... Uploading..");
            Log.level0("-------------------------------------------------------------\n Hummingbird Interceptor Boot Loader (HIBL) v2.0\n Copyright (C) Rebellos 2011\n-------------------------------------------------------------\n");

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       LinkLauncher LinkLauncher = new LinkLauncher();
       LinkLauncher.launchLink("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=6LPHJBF33CA4W");
    }//GEN-LAST:event_jButton1ActionPerformed
  
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
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConnectedLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

}

