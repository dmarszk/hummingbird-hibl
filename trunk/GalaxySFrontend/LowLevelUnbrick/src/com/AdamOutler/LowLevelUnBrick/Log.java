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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.text.BadLocationException;

/**
 *
 * @author adam
 * Logging levels:
 * Logging levels are set in Statics
 * 
 * Level0: silent, keep this for future critical tasks
 * Level1: Information for user to see
 * Level2: Information for developers to see
 * Level3: verbose information, use for conversions and random tasks
 * 
 */

public class Log{
              
       
       
       public Log(){
       }
       
       private void consoleOut(String data) {
            System.out.println(data);
            if (!"".equals(data)){ 
                if (! "\n".equals(data)){
                    try{
                        Statics.ProgressArea.append("\n"+data);
                        Statics.ProgressArea.setCaretPosition(
                                Statics.ProgressArea.getDocument()
                                .getLength());
                    }catch (NullPointerException e){
                        Statics.PreStatus=Statics.PreStatus+data;
                    }

                }
            }
        }


        // level 0 is used for errors..  basically silent. Use level 1 for 
        // for most tasks
        public void level0(String data) {
                if (Statics.ConsoleLevel >= 0) {
                       consoleOut(data);

                }
                if (Statics.LogLevel >= 0) {
                        debugOut(data);

                }
        }

        // level 1 is for user data
        public void level1(String data) {
                if (Statics.ConsoleLevel >= 1) {
                        consoleOut(data);
                }
                if (Statics.LogLevel >= 1) {
                        debugOut(data);

                }

        }

        // level 2 is for debugging data
        public void level2(String data) {
                if (Statics.ConsoleLevel >= 2) {
                        consoleOut(data);
                }
                if (Statics.LogLevel >= 2) {
                        debugOut(data);

                }
        }

        // level 3 is conversions and other random test data
        public void level3(String data) {
                if (Statics.ConsoleLevel >= 3) {
                        consoleOut(data);
                }
                if (Statics.LogLevel >= 3) {
                        debugOut(data);

                }
        }


        public void copyError(String Filename) {
            //standard unexpected token failure
            this.level0("File Copy Error: " + Filename);
        }

        public void genericError(String Message) {
            this.level0("Error: " + Message);
        }
        public void writeToLogFile(String data){
            debugOut(data);
        }
        private void debugOut(String data) {
            
                FileWriter WriteFile = null;
                     try {
                     WriteFile = new FileWriter(Statics.TempFolder+"log.txt", true);
                     } catch (IOException ex) {
                     }
                 PrintWriter out = new PrintWriter(WriteFile);
                 Statics.OutFile=out;
                 if (Statics.OutFile != null){Statics.LogCreated=true;}
                 out.print(data);
                 out.close();
            
        }
       
        public void progress(String data) {
            try {
                Statics.ProgressArea.append(data);
                Statics.ProgressArea.setCaretPosition(Statics.ProgressArea.getDocument().getLength());
            } catch (NullPointerException e){
                System.out.print(data);
            }

        }
        public void LiveUpdate(String data){
            System.out.println(data);
            Statics.ProgressArea.append(data);
            Statics.ProgressArea.setCaretPosition(Statics.ProgressArea.getDocument().getLength());

        }
        public void beginLine(){
            try {
                int start=Statics.ProgressArea.getLineStartOffset
                        (Statics.ProgressArea.getLineCount()-1);
                int end = Statics.ProgressArea.getText().length();
                
                Statics.ProgressArea.replaceRange("", start, end);
                
                int x=1;
                System.out.print(x);
            }
                catch (BadLocationException ex) {
            }
        }
        
}
