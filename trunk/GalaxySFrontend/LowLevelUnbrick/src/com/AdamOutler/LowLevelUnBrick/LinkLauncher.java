/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamOutler.LowLevelUnBrick;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author adam
 */
public class LinkLauncher implements Runnable  {
    static String Link;
    public void launchLink(String link){
        Link = link;
        Runnable runnable = new LinkLauncher();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void run() {
           
        Shell Shell = new Shell();
        String Cmd[]={"firefox", Link};
        String LaunchRes=Shell.sendShellCommand(Cmd);
        if (LaunchRes.contains("CritERROR!!!")){
            String MCmd[]={"open" , Link};
            String MLaunchRes=Shell.sendShellCommand(MCmd);
            if (MLaunchRes.contains("CritERROR!!!")){
                String WCmd[]={"explorer", Link};
                String WLaunchRes=Shell.sendShellCommand(WCmd);
                if (WLaunchRes.contains("CritERROR!!!")){
                     if (Desktop.isDesktopSupported()) {
                        Desktop desktop;
                        desktop = Desktop.getDesktop();
                        URI uri = null;
                        try {
                            uri = new URI(Link);
                             desktop.browse(uri);
                        } catch (IOException ioe) {
                        } catch (URISyntaxException use) {
                        }
                     }
                }
            }
        }
    }
}
    

