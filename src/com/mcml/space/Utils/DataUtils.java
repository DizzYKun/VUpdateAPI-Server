package com.mcml.space.Utils;

import com.mcml.space.TCPServer;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataUtils {

    private static File DataFile;
    private static YamlConfiguration data;

    public static void init() {
        DataFile = new File("data.yml");
        if (DataFile.exists() == false) {
            try {
                DataFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data = YamlConfiguration.loadConfiguration(DataFile);
    }
    
    public static void uploadData(String projectname,int version,String download,String PW,int QQ,Socket socket){
        if(data.isString("Plugin." + projectname + ".Password")){
            if(PW.equals(data.getString("Plugin." + projectname + ".Password")) == false){
                TCPServer.SendOut(socket, "sendUserWindowMessage-密码错误，无法执行数据更改，权限拒绝！");
                return;
            }
        }
        data.set("Plugin." + projectname + ".projectname", projectname);
        data.set("Plugin." + projectname + ".version", version);
        data.set("Plugin." + projectname + ".download", download);
        data.set("Plugin." + projectname + ".Password", PW);
        data.set("Plugin." + projectname + ".QQ", QQ);
        try {
            data.save(DataFile);
        } catch (IOException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        TCPServer.SendOut(socket, "sendUserWindowMessage-成功修改了服务器云端数据！");
    }
    
    public static int getVersion(String projectname){
        return data.getInt("Plugin."+projectname + ".version");
    }
    
    public static String getDownloadRoad(String projectname){
        return data.getString("Plugin."+projectname + ".download");
    }
}
