/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcml.space;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class TCPUtils {
    private static String RecMessage;

    public static void sendMessage(Socket socket, String message) {
        System.out.println("send: " + message + " to " + socket);
        TCPServer.SendOut(TCPServer.GamePortoutMap.get(socket), message);
    }
}
