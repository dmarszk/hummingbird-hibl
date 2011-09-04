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

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author adam
 */
public class FileOperations {
    Log Log = new Log();
    Shell shellCommand = new Shell();

   public FileOperations(){
   }

 

  public boolean copyFromResourceToFile(String Resource, String toFile){
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(Resource);
            try {
                if ( resourceAsStream.available() >= 1 ){
                    File Destination = new File(toFile);
                    writeInputStreamToFile(resourceAsStream, Destination);
                    if (Destination.length() >= 1){
                        return true;
                    } else {

                        Log.level0("Failed to write file");
                        return false;
                    }

                } else {
                    Log.level0("Critical Error copying " + Resource);
                }
            } catch ( NullPointerException e){
            return false;
            }
                

           
            
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            Log.level0("Critical Error copying "+ Resource);
            return false;
        }
   return false;
  }
  
  public void recursiveDelete(String path){
   recursiveDelete(new File(path));   
  }
  
  public void recursiveDelete(File path){
        File[] c = path.listFiles();
        if (path.exists()){
            Log.level2("Cleaning up folder:" + path.toString());
            
            for (File file : c){
                if (file.isDirectory()){
                    Log.level3("Deleting " + file.toString());
                    recursiveDelete(file);
                    file.delete();
                } else {
                    file.delete();
                }
            }
            path.delete();
        }
  }
  
  
  public boolean verifyPermissionsRecursive(String path){
       File Check = new File(path);
       File[] c = Check.listFiles();
       if (Check.exists()){
           Log.level2("Verifying permissions in folder:" + path.toString());
           for (File file : c){
               if (!file.canWrite()){
                   return false;
               }
           }
       } 
       return true;
  }
    public String findRecursive(String PathToSearch,String FileName){
       File Check = new File(PathToSearch);
       File[] c = Check.listFiles();
       if (Check.exists()){
           Log.level2("Searching for file in folder:" + PathToSearch.toString());
           for (File file : c){
               String x = file.getName();
               if (file.isDirectory()){
                    Log.level3("Searching " + file.toString());
                    File[] subdir = file.listFiles();
                    for (File sub : subdir){
                        if (sub.isDirectory()) {
                            String FoundFile = findRecursive(sub.toString(),FileName);
                            if (FoundFile.toString().endsWith("heimdall.exe")){
                                return FoundFile;
                            }
                        } else {
                            if (sub.toString().equals(FileName)) return sub.toString();
                        }
                    }
               } else  if (file.getName().equals("heimdall.exe")){
                   return file.getAbsoluteFile().toString();
               }
           }
       } 
       return null;
  }

         
     

  
  public boolean makeFolder(String Folder){
    Boolean CreatedFolder = false;
    File folder= new File(Folder);

    if (folder.exists()){
        return false;
    } else{
        CreatedFolder=folder.mkdir();
    }
    if ( CreatedFolder ){
        Log.level2("System temp folder created sucessfully in: "+Folder);   
    }else{

        CreatedFolder=false;    
        Log.level0("Could not create temp folder in " + Folder);
    }
    
    return CreatedFolder;
 }
  
  
  private boolean writeInputStreamToFile(InputStream is, File file) {
      Log.level3("Attempting to write "+file.getPath());
      try {
          DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
	  int c;
          if (is.available() > 0 ){
              while((c = is.read()) != -1) {
                  out.writeByte(c);
	      }
          } else {
              Log.level0("ERROR: FILE READ WAS 0 LENGTH");
              return false;
          }
	  is.close();
	  out.close();
          
      }	catch(IOException e) {
          System.err.print(e);
          System.err.println("Error Writing/Reading Streams.");
                  return false;
      }
      if ((file.exists()) && (file.length() >= 4)){
          return true;
      } else { 
          return false;
      }
     
  }
  



   
   
  public Boolean deleteFile(String FileName){
      Boolean Deleted=true;
      File file = new File(FileName);
      if (file.exists()){
          Deleted=false;
          if (file.delete()){
            Deleted=true;
            Log.level3("Deleted "+ FileName);                 
          } else {
            Deleted=false;
            Log.level0("ERROR DELETING FILE:" + FileName);
            JOptionPane.showMessageDialog(null, "Could not delete "+FileName+
                ".  Delete this folder manually", 
                "file error", JOptionPane.ERROR_MESSAGE);
            
          }
      } else {
          Deleted=true;
      }
      return Deleted;
  }
   
  public void copyFile(File sourceFile, File destFile) throws IOException {
      
      Log.level3("Copying " + sourceFile.getPath() + " to " + destFile.getPath());
      if(!destFile.exists()) {
          destFile.createNewFile();
      }  
      FileChannel source = null;
      FileChannel destination = null;
      try {   
          source = new FileInputStream(sourceFile).getChannel();
          destination = new FileOutputStream(destFile).getChannel();   
          destination.transferFrom(source, 0, source.size());
      }  finally {
          if(source != null) {
              source.close();
          }
      }
      if(destination != null) {
          destination.close(); 
          
      }
      
      
  } 
   
  public String currentDir() {
      String CurrentDir=new File(".").getAbsolutePath();
      Log.level3("Detected current folder: "+ CurrentDir);
              if (CurrentDir.endsWith(".")){
                  CurrentDir=CurrentDir.substring(0,CurrentDir.length()-1);
              }
      return CurrentDir;
  }
  public boolean copyFile(String FromFile, String ToFile){
      File OriginalFile = new File(FromFile);
      File DestinationFile = new File(ToFile);
        try {
            copyFile(OriginalFile, DestinationFile);
            return true;
        } catch (IOException ex) {
            return false;
        }
              
  }
  
  public boolean verifyFileExists(String Folder){
      File FileFolder = new File(Folder);
      boolean Result=(FileFolder.length()>=1);
      Log.level3("Verifying "+Folder+" .  Result="+Result);
      Log.level3("Result=" +Result);
      return (Result);
  }
  
  public boolean setExecutableBit(String Executable){
      File Exe = new File(Executable);
      boolean Result = Exe.setExecutable(true);
      Log.level3("Setting executable "+Exe+". Result="+Result);
      return Result;
  }



      public boolean verifyResource(String Res){
            boolean Result;
            //this.statusAnimationLabel.setText(Res);
            Log.progress("Uncompressing "+Res+".... ");
            deleteFile(Res);
            Result=copyFromResourceToFile(setRes(Res), setDest(Res));
            //Log.level3("Unpacking " + setDest(Res) + " Performed correctly: " + Result ); 
            if (Result){
                Log.progress("Uncompressed\n");
            } else {
                Log.progress("FAILED!!!!\n");
            }
            return Result;
    }
    private String setDest(String FileName){
        return Statics.TempFolder + FileName;
    }


        
    
        
    private String setRes(String FileName ){
        return Statics.ROMPackageResourceFolder + FileName;
    }
    
    public String readTextFromResource(String Resource){
        FileInputStream fis = null; 
        InputStreamReader in = null;
        String TextFile="";
        InputStream resourceAsStream = getClass().getResourceAsStream(Resource);
        StringBuilder text = new StringBuilder();
        try {
            in = new InputStreamReader(resourceAsStream, "UTF-8");
            int read;
            while ((read = in.read())!= -1 ){
                char C = Character.valueOf((char)read);
                text.append(C);       
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        Log.level2(text.toString());
        return text.toString();
    }
    
    
    
    
    public void writeToFile(String Text, String File) throws IOException{
    
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(File,true));
            bw.write(Text);
            bw.close(); 
        
    }
}    

  
  


