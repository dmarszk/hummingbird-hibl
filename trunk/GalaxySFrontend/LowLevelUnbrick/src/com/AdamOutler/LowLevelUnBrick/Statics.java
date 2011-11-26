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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;



/**
 *
 * @author adam
 * 
 * Statics is used for any type of static variable
 * It is the Static Class for information to be used
 * everywhere in the program.
 */
public class Statics {
    public Statics(){
    }    

    /*increase or decrease the logging level*/
    public static int ConsoleLevel=2; //userdata is output to console
    /*increase or decrease the logging level*/
    public static int LogLevel=2; //all logs are output to file
       
    /*
     * miscellanious variables
     */ 
    static Shell shellCommand = new Shell();
    static Log Log = new Log();    
    public static ArrayList LiveSendCommand = new ArrayList();
    public static PrintWriter OutFile;
    public static boolean LogCreated=false; //used by log class
    public static LowLevelUnbrickOneClickView HeimdallOneClickView;
    /*
     *Form data for Heimdall One-Click View 
     */
    public static String DeviceModel;//Set by FileOperations, Read by one-click view
    public static String DeviceFriendlyName;
    public static String DeveloperTeam;
    public static String DeveloperMembers;
    public static String ROMName;
    public static String ROMRevision;
    public static String Platform;
    public static String PlatformVersion;
    public static String Manufacturer;    
    public static String DeveloperWebsiteLink;
    public static String DeveloperDonateLink;
    public static JTextArea ProgressArea; //used by log to update Progress
    public static JTextArea StatusArea; //used by Log to set carret and apped data
    public static String PreStatus ="";//Buffer for data before form has been created
    public static String PreProgress="";
    public static int ConnectionSatus=5;
    
    public static String Slash=System.getProperty("file.separator");
    final public static String HeimdallCheckString=" Glass Echidna";
    
    
    

            
    
    private static String TempF=null;
    //TempFolder is the folder used for file operations
    public static String TempFolder=getTempFolder();
    private static String getTempFolder(){
        
        if (TempF == null){
            TempF = System.getProperty("java.io.tmpdir");
            if (!TempF.endsWith(Slash))TempF=TempF + Slash;
            String UserName=System.getenv("USERNAME");
            if ( UserName == null){
                TempF=TempF+"TempHeimdallOneClick";
            } else {
                TempF=TempF+UserName+"HeimdallOneClick";
            }
            String Randomness = "";
            String Characters="123456789ABCDEF";
            Random RandomNumberGenerator=new Random();
            for (int i = 0; i < 8; i++){
                Randomness=Randomness+ Characters.charAt(RandomNumberGenerator.nextInt(Characters.length()));
            }
            TempF=TempF+Randomness;
            if (!TempF.endsWith(Slash))TempF=TempF + Slash;
        }
        return TempF;
    }
   
 

    /*
     * Cross-Platform data storage
     */    
    public static String OSName=System.getProperty("os.name");
    public static String  OSType=""; //used for logging
    public static String  Arch="";
    public static String HeimdallInstallFileName="unsupported";
    public static String HeimdallBinaryFileName="heimdall";
    public static String InstalledHeimdallVersion="";
    public static String ResourceHeimdallVersion="";
    public static String HeimdallVersionContained="v1.3.0";
    /*
     * Windows resources
     */
    //Windows drivers
    final public static String WinLibUSBInTempFolder = Statics.TempFolder+"heimdall-win32\\Heimdall Suite 1.3.0\\libusb-1.0.dll";
    //libusb driver
    final public static String WinDriverDeploymentInTempFolder = Statics.TempFolder+"heimdall-win32\\Heimdall Suite 1.3.0\\Drivers\\zadig.exe";

    //Windows Visual C++
    final public static String WinVCRedistFolderInTempFolder= Statics.TempFolder+"vcredist_x86.exe";
    //Windows Heimdall file
    final public static String WinHeimdallInTempFolder=TempFolder+"heimdall-win32\\Heimdall Suite 1.3.0\\heimdall.exe";
    //Windows permission elevation in temporary folder
    final public static String WinElevatorInTempFolder=Statics.TempFolder+"\\Elevate.exe";
    //Windows Loader in case Elevate does not work
    final  public static String WinPermissionsLoaderResource="/com/AdamOutler/HeimdallOneClick/resources/HeimdallPackage/oneclickloader.exe";
    //Windows VCRedist location in package
    final public static String WinVCRedistributableResource="/com/AdamOutler/HeimdallOneClick/resources/vcredist_x86.exe";
    //Windows permissions elevator
    final public static String WinElevatorResource="/com/AdamOutler/HeimdallOneClick/resources/Elevate.exe";
    
    
    /*
     * Cross platform resources
     */
    //Heimdall Version text file
    final public static String HeimdallVersionText="/com/AdamOutler/HeimdallOneClick/resources/HeimdallPackage/HeimdallVersion";
    //Resources folder in package
    final public static String ROMPackageFolderResource="/com/AdamOutler/HeimdallOneClick/resources/ROMPackage";
    //XML location in package
    final public static String ROMPackageTXTFileForFolderResource="/com/AdamOutler/HeimdallOneClick/ROMPackage/PROPERTIES.txt";
    //ROM location in package
    final public static String HeimdallBinariesResource="resources/HeimdallPackage/";
    //Connected Icon
    final public static String ConnectedIcon="resources/images/Connected.png";
    //disconnected Icon
    final public static String DisonnectedIcon="resources/images/Disconnected.png";
    //unknown icon
    final public static String UnknownIcon="resources/images/Unknown.png";
    //ROM Package in tar.gz format
    final public static String ROMPackageResourceFolder="/com/AdamOutler/HeimdallOneClick/resources/ROMPackage/";
    //if heimdall was detected as installed
    public static boolean HeimdallInstalled=false; 
    //used for communication between checkbox on heimdallOneClickView and FlashPrep
    public static boolean FlashBootloaders = false;  

    
    /*
     * Variables for TGZFormat
     */
    public static boolean UseTGZFormat=false;
    //Firmware XML in resource 
    public static String ROMPackageTGZXMLResource="/com/AdamOutler/HeimdallOneClick/resources/ROMPackage/firmware.xml";
    //Firmware package in resource
    final public static String ROMPackageTGZ="/com/AdamOutler/HeimdallOneClick/resources/ROMPackage/HeimdallPackage.tar.gz";
    //ROM Package In Temp Folder
    final public static String ROMPackageDeployed=Statics.TempFolder+"ROMPACKAGE/";
    //Firmware XML Temp FOlder
    public static String ROMPackageFirmwareXMLTempFolder= Statics.TempFolder+"firmware.xml";
            
 
    /*
     * Determines if Linux, Mac or Windows
     */
    //Check for windows
    public static boolean isWindows(){
 	String os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "win" ) >= 0); }
    //Check for Mac
    public static boolean isMac(){
 	String os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "mac" ) >= 0);}
    //Check for Linux
    public static boolean isLinux(){
        String os = System.getProperty("os.name").toLowerCase();
	return (os.indexOf( "nux") >=0);}
    
    
    
  
    /*
     * sets system information, including heimdall presence, operating system and archetecture
     */
    public void setSystemInfo(){

        if(isWindows()){
            
            Statics.HeimdallInstallFileName="heimdall-win32.zip";
            Statics.OSType="Windows";
            Statics.Slash="\\";


        } else if (isMac()) {
            OSType="Mac";
            HeimdallInstallFileName="heimdall-mac.dmg";
            Statics.Slash="/";
            HeimdallBinaryFileName="heimdall";

        }else if(isLinux()){
            OSType="Linux";
            Statics.Slash="/";
            HeimdallBinaryFileName="heimdall";
           String[] Command={ "dpkg", "--version"};
            String dpkgResults=shellCommand.sendShellCommand(Command);
            if (dpkgResults.contains("Debian")){
                OSType="Debian Based";
                String[] CommandArch={"arch"};
                Arch=shellCommand.sendShellCommand(CommandArch);
                if (Arch.contains("i686")){
                    HeimdallInstallFileName="heimdall_i386.deb";
                    Arch="i686";
                } else if (Arch.contains("x86_64")) {
                    HeimdallInstallFileName="heimdall_amd64.deb";
                    Arch="x86_64";
                } else {
                    HeimdallInstallFileName="";
                    Arch="Unexpected Processor Archetecture";
                }
            }else {
                OSType="Linux";
                HeimdallInstallFileName="";                
            }
        }

        FileOperations FileOperations=new FileOperations();
        boolean MadeFolder;
        
        if (! FileOperations.verifyFileExists(Statics.TempFolder)){
            MadeFolder = FileOperations.makeFolder(Statics.TempFolder);
        } else {
            MadeFolder = true;
        }
        if (MadeFolder){
            Log.level3("Set Temporary Folder:" + Statics.TempFolder );
            //Log.level1("Log:" + Statics.TempFolder +"log.txt");
        }
        HeimdallInstalled=false;
        Log.level3("Statics.setSystemInfo(): " + OSType + " "+ Arch );
        Log.level1("Operating System: " + OSType + " "+ Arch );
        String HeimdallCmd=Statics.HeimdallBinaryFileName;
        String[] CommandHeimdall={HeimdallCmd};
        String Version=shellCommand.sendShellCommand(CommandHeimdall);
        String SplitComma=Version.split(",")[0];
        if ( ! (Version.equals("")) ) {
            String SplitReturn="";
            try {
                if (! SplitReturn.equals("")){
                    SplitReturn=SplitComma.split("\n")[2];
                    InstalledHeimdallVersion=SplitReturn;
                    HeimdallInstalled=true;
                } else {              
                   String[] CommandHeimdall2={Statics.HeimdallBinaryFileName,"version"};
                   Version = shellCommand.sendShellCommand(CommandHeimdall2); 
                   
                   if (Version.equals("v1.3")){
                       HeimdallInstalled=true;
                       InstalledHeimdallVersion=Version.replace("\n", "");

                       return;
                   } else if (Version.equals("\nv1.3 (beta)")) {
                       InstalledHeimdallVersion=Version.replace("\n", "");
                       Log.level1("Found Heimdall Version: "+InstalledHeimdallVersion);
                       InstalledHeimdallVersion=Version.replace("\n", "");
                       HeimdallInstalled=true;
                      
                       return;
                   } else if (Version.equals("\nv1.3.0")) {
                       InstalledHeimdallVersion=Version.replace("\n", "");
                       Log.level1("Found Heimdall Version: "+InstalledHeimdallVersion);
                       InstalledHeimdallVersion=Version.replace("\n", "");
                       HeimdallInstalled=true;

                       return;
                   }
                   
                }
                if (SplitReturn.contains("Arguments")){
                    String[] CommandHeimdall2={Statics.HeimdallBinaryFileName, "version" };
                    InstalledHeimdallVersion=shellCommand.sendShellCommand(CommandHeimdall2).replace("\n", "");
                }
                Log.level1("Found Heimdall Version: "+InstalledHeimdallVersion);
                HeimdallInstalled=true;
                if (InstalledHeimdallVersion.equals("") ){
                    InstalledHeimdallVersion="Unknown version";

                }
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                HeimdallInstalled=false;
            }
        } else {
            HeimdallInstalled=false;
            Log.progress("\nHeimdall is not installed!!");
        }
    }
    
      
    

   

}
