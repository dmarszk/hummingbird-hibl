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
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class LowLevelUnbrickOneClickApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        if ((getClass().getResource(Statics.ROMPackageTGZ))==null){
          show(new LowLevelUnbrickOneClickView(this,false));
        } else{
          show(new LowLevelUnbrickOneClickView(this,true));
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of LowLevelUnBrick
     */
    public static LowLevelUnbrickOneClickApp getApplication() {
        return Application.getInstance(LowLevelUnbrickOneClickApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        
          //delete the temp folder before starting.
        FileOperations FileOperations=new FileOperations();
        Log Log = new Log();
        FileOperations.makeFolder(Statics.TempFolder);
        Log.level1("\nUncompressing HIBL Payload to " + Statics.TempFolder +"/n");
        FileOperations.copyFromResourceToFile("/com/AdamOutler/LowLevelUnBrick/resources/UnBrickPack.zip", Statics.TempFolder+"UnBrickPack.zip");
        Unzip Unzip=new Unzip();
        try {
            Unzip.unzip(Statics.TempFolder+"UnBrickPack.zip");
        } catch (ZipException ex) {
            Logger.getLogger(LowLevelUnbrickOneClickView.class.getName()).log(Level.SEVERE, null, ex);
            Log.level0("ERROR, INVALID ZIP FILE IN PACKAGE");
        } catch (IOException ex) {
            Logger.getLogger(LowLevelUnbrickOneClickView.class.getName()).log(Level.SEVERE, null, ex);
            Log.level0("ERROR, OUT OF SPACE OR NO ACCESS TO TEMP FOLDER");
        }
        launch(LowLevelUnbrickOneClickApp.class, args);
    }
}
