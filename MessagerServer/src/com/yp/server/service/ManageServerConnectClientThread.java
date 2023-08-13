package com.yp.server.service;

import com.yp.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static void addServerConnectClientThread
            (String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);


    }

    public static void sendMessageToAll(Message message) {

        ObjectOutputStream oos;
        for (String userId : hm.keySet()) {
            if (message.getSender().equals(userId)) continue;
            try {
                oos = new ObjectOutputStream(hm.get(userId).getSocket().getOutputStream());

                oos.writeObject(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getOnlineUsers() {
        StringBuilder onlineUserList = new StringBuilder();
        for (String onlineUser : hm.keySet()) {
            onlineUserList.append(onlineUser + " ");
        }

        return onlineUserList.toString();
    }



}
