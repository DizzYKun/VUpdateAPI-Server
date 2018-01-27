package com.mcml.space;

import com.mcml.space.Utils.DataUtils;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {

    public static int PORT = 3090;// �����Ķ˿ں�
    public static HashMap<Socket, DataInputStream> GamePortinMap = new HashMap();
    public static HashMap<Socket, DataOutputStream> GamePortoutMap = new HashMap();

    public static void main(String[] args) {
        System.out.println("�����������˿�(����)...");
        Scanner sc = new Scanner(System.in);
        String openedport = sc.next();
        PORT = Integer.parseInt(openedport);
        System.out.println("������������...");
        DataUtils.init();
        Thread QueueLineServerThread = new Thread() {

            public void run() {
                TCPServer Queueserver = new TCPServer();
                Queueserver.init();
            }
        };
        QueueLineServerThread.start();
        System.out.println("������ɣ���ʼ�����˿�:" + PORT);
        sc.close();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // һ���ж���, ���ʾ��������ͻ��˻��������
                Socket client = serverSocket.accept();
                // �����������
                new TCPServer.HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("�������쳣: " + e.getMessage());
        }
    }

    public void GameServerinit() {
        try {
            ServerSocket serverSocket = new ServerSocket(21991);
            Socket client = serverSocket.accept();
            DataInputStream input = new DataInputStream(
                    client.getInputStream());
            DataOutputStream out = new DataOutputStream(
                    client.getOutputStream());
            GamePortinMap.put(client, input);
            GamePortoutMap.put(client, out);
        } catch (Exception e) {
            System.out.println("�������쳣: " + e.getMessage());
        }
    }

    private class HandlerThread implements Runnable {

        private Socket socket;

        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            DataOutputStream out = null;
            try {
                while (true) {
                    DataInputStream input = new DataInputStream(
                            socket.getInputStream());
                    out = new DataOutputStream(
                            socket.getOutputStream());
                    GamePortinMap.put(socket, input);
                    GamePortoutMap.put(socket, out);
                    String message = input.readUTF();
                    System.out.println("Receive: " + message + " from " + out);
                    ServerRec.RecMessage(message,socket);
                }
            } catch (SocketException ex) {
                GamePortinMap.remove(socket);
                GamePortoutMap.remove(socket);
                System.out.println("Disconnect: " + socket.toString());
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("Catch Finally: " + e.getMessage());
                    }
                }
                try {
                    out.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public static void SendOut(DataOutputStream out, String message) {
        System.out.println("Send: " + message + " to " + out);
        try {
            out.writeUTF(message);
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void SendOut(Socket target, String message) {
        System.out.println("Send: " + message + " to " + target);
        try {
            GamePortoutMap.get(target).writeUTF(message);
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}