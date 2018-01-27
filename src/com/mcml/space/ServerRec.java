/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcml.space;

import com.mcml.space.Utils.DataUtils;
import com.mcml.space.Utils.Utils;
import java.net.Socket;

public class ServerRec {

    public static void RecMessage(String message,Socket socket) {
        if(message.startsWith("Login-")){
            TCPServer.SendOut(socket, "LoginSuc-");
        }
        if(message.startsWith("uploadData-")){
            Utils.StringToUpload(message.substring(11,message.length()),socket);
        }
        if(message.startsWith("getVersion-")){
            int version = DataUtils.getVersion(message.substring(11,message.length()));
            TCPServer.SendOut(socket, "PluginVersion-" + version);
        }
        if(message.startsWith("getDownloadRoad-")){
            String downloadRoad = DataUtils.getDownloadRoad(message.substring(16, message.length()));
            TCPServer.SendOut(socket, "PluginDownloadRoad-" + downloadRoad);
        }
    }
}
