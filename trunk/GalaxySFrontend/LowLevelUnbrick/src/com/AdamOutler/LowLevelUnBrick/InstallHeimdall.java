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
import java.util.zip.ZipException;
import javax.swing.JOptionPane;

/**
 *
 * @author adam
 */
public class InstallHeimdall {
    FileOperations FileOperations = new FileOperations();
    Log Log = new Log();
    Shell Shell=new Shell();    
    
    public void upgradeHeimdall(){
        int Value = 0;
        if (!Statics.isWindows()){
            Log.progress("Heimdall version is out of date.\n");
            /* TODO: see if this is needed
             * System.out.printf("JOptionPane.YES_OPTION    = %d%n" +
                        "JOptionPane.NO_OPTION     = %d%n" +
                        "JOptionPane.CLOSED_OPTION = %d%n",
                         JOptionPane.YES_OPTION,
                         JOptionPane.NO_OPTION,
                         JOptionPane.CLOSED_OPTION);
             * 
             */
                     TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
                     Value = timeOutOptionPane.showTimeoutDialog( 10, //timeout
                        Statics.HeimdallOneClickView.getFrame(), //parentComponent
                        "Heimdall One-Click has not detected the expected " //Display Message
                        +"version of Heimdall installed on this computer.\n"
                        +"Would you like to upgrade Heimdall?",
                        "Upgrade Heimdall?",//DisplayTitle
                        TimeOutOptionPane.OK_OPTION, // Options buttons
                        TimeOutOptionPane.QUESTION_MESSAGE, //Icon
                        null, // option buttons
                        "yes"); //seconds before auto "yo"
                 if ((Value == JOptionPane.YES_OPTION)||(Value == JOptionPane.CLOSED_OPTION)) {
                 //statics.setSystemInfo();    
                     installHeimdall();
                 }
         } else {
             installHeimdall();
         }
    }
    
 public boolean verifyHeimdall(){

     String SendHeimdallVerification[]={Statics.HeimdallBinaryFileName, "version"};
     String HeimdallTest=Shell.sendShellCommand(SendHeimdallVerification).replace("\n","");
     //verify vc
     Statics.InstalledHeimdallVersion=HeimdallTest;

     if ( Statics.ResourceHeimdallVersion.contains("v1.3.0")) {
         Statics.HeimdallInstalled=true;
         return true;
     }
     return false;
         
 }
 public void installHeimdall(){
    String InternalFile=Statics.HeimdallBinariesResource + Statics.HeimdallInstallFileName;
    String FilesystemFile=Statics.TempFolder + Statics.HeimdallInstallFileName;
    FileOperations.copyFromResourceToFile(InternalFile, FilesystemFile);
    if (Statics.isWindows()){
        FileOperations.copyFromResourceToFile(InternalFile,FilesystemFile);
        try {
             Unzip Unzip = new Unzip();    
             Unzip.unzip(FilesystemFile);
         } catch (ZipException ex) {
             Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,"Could not unzip file.  Internal file has been tampered with.\n"
                     , InternalFile,  JOptionPane.ERROR_MESSAGE);
             System.exit(2);
         } catch (IOException ex) {
             Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,"Could not unzip file.  Internal file has been tampered with.\n"
                      , InternalFile,  JOptionPane.ERROR_MESSAGE);
             System.exit(2);                          
         }
         Statics.HeimdallBinaryFileName=FileOperations.findRecursive(Statics.TempFolder,"heimdall.exe");
         if (Statics.HeimdallBinaryFileName == null){
             JOptionPane.showMessageDialog(null,"Could not deploy and locate heimdall.exe in temp folder.\n"
                     , InternalFile,  JOptionPane.ERROR_MESSAGE);
             System.exit(2);
         }
         Log.level3("Heimdall Binary:" +Statics.HeimdallBinaryFileName);
         if (!verifyHeimdall()){
             this.installVCRedist();
         } else {
             Statics.HeimdallInstalled=true;
         }
    }
    if (Statics.isMac()){
        String[] Mount={ "hdiutil", "mount" , FilesystemFile };
        String[] LineSplit=Shell.sendShellCommand(Mount).split("\n");
        String Folder="";
        for (String Lines : LineSplit ){
            String[] Line = Lines.split("	");
            for ( String Item : Line){
                if (Item.contains("Heimdall")){
                    Folder=Item;
                    Log.progress("Mounted "+Folder);
                }
            }
        }
        String[] GetFolderContents={ "ls","-1", Folder};
        String[] FolderContents=Shell.sendShellCommand(GetFolderContents).split("\n");
        String File="";
        for (String Item : FolderContents) {
             if (Item.contains("mpkg")){
                 File=Item;
                 JOptionPane.showMessageDialog(null,"Heimdall One-Click will now launch Heimdall Installer\n"
                     + "You must install Heimdall in order to continue", "Exiting Heimdall One-Click",  JOptionPane.ERROR_MESSAGE);  }
        }
        String[] OpenMpkg={ "open", Folder + "/" + File};
        Shell.sendShellCommand(OpenMpkg);
        System.exit(0);
     }
     if (Statics.isLinux()){
         FileOperations.setExecutableBit(FilesystemFile);
         FileOperations.verifyFileExists(FilesystemFile);
         String[] Command={ "gksudo",  "--description", "Heimdall Installer", "dpkg -i "+FilesystemFile };
         Shell.sendShellCommand(Command);
         if (!Shell.sendShellCommand(new String[] {"heimdall"} ).contains("CritERROR!!")){
             Statics.HeimdallInstalled=true;
         }
     }
  }
    
  public void installVCRedist(){
         FileOperations.copyFromResourceToFile(Statics.WinVCRedistributableResource,Statics.WinVCRedistFolderInTempFolder);
         String InstallVC[]={Statics.WinVCRedistFolderInTempFolder};
         deployElevate();
         String InstallVCResults= Shell.elevateSimpleCommand(InstallVC);
         if (InstallVCResults.contains("CritERROR!!!")){
             displayWindowsPermissionsMessageAndExit();
         }
  }  
    
  public void installWindowsDrivers(){
      //install drivers
      Log.level0("Installing drivers");
      TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
      timeOutOptionPane.showTimeoutDialog(
            60, //timeout
            null, //parentComponent
            "1. Put your device in --download mode-- and connect to computer.\n"
            + "2. In the next window select Options>List all devices.\n"
            +"3. select SAMSUNG USB Composite device in the main window\n"
            +"4.  click 'install driver'.\n", //Display Message
            "Driver Installation",   //DisplayTitle
            TimeOutOptionPane.OK_OPTION, // Options buttons
            TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
            new String[]{"OK"}, // option buttons
            "OK"); //Default
      
      String cmd[]={Statics.WinDriverDeploymentInTempFolder};
      deployElevate();
      String ZadigResults=Shell.elevateSimpleCommand(cmd);
      if (ZadigResults.contains("CritERROR!!")){
          displayWindowsPermissionsMessageAndExit();
    
     }

  }
  
  public void deployWindowsLoader(String LoaderDestination){
      TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
      timeOutOptionPane.showTimeoutDialog(
             300, //timeout
             null, //parentComponent
             "Log in as an administrator and run this program again.\n"
              + "This is required for installation of Heimdall and dependencies.", //Display Message
             "Administrative Permissions Required",   //DisplayTitle
             TimeOutOptionPane.OK_OPTION, // Options buttons
             TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
             new String[]{"OK"}, // option buttons
             "OK"); //Default
   }
  

  
  public void displayWindowsPermissionsMessageAndExit(){
      if (Statics.isWindows()){
          JOptionPane.showMessageDialog(null,""
              + "Administrative permissions are required to continue.\n"
              + "Please log in as a System Administrator  and rerun the command or use the console: \n"
              + "runas /user:Administrator java -jar "+ getClass().getProtectionDomain().getCodeSource().getLocation().getPath().toString(), //Display Message
             "Permissions Error",   JOptionPane.ERROR_MESSAGE);
      }
      System.exit(0);
   }
  
   public void unzipHeimdall(String InternalFile, String FilesystemFile ){
        try {
             Unzip Unzip = new Unzip();    
             Unzip.unzip(FilesystemFile);
         } catch (ZipException ex) {
             Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,"Could not unzip file.  Internal file has been tampered with.\n"
                     + "You must install heimdall manually", InternalFile,  JOptionPane.ERROR_MESSAGE);
             System.exit(2);
         } catch (IOException ex) {
             Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,"Could not unzip file.  Internal file has been tampered with.\n"
                     + "You must install heimdall manually", InternalFile,  JOptionPane.ERROR_MESSAGE);
             System.exit(2);                          
         }
    }

    public void deployElevate(){
      FileOperations.copyFromResourceToFile(Statics.WinElevatorResource, Statics.WinElevatorInTempFolder);
      FileOperations.verifyFileExists(Statics.WinElevatorInTempFolder);
    }

    void runWinHeimdallInstallationProcedure() {
        installVCRedist();
        installHeimdall();
        installWindowsDrivers();
    }
}
