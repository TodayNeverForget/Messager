package com.yp.server.service;

import java.util.HashMap;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);


    }

    public static String getOnlineUsers() {
        StringBuilder onlineUserList = new StringBuilder();
        for (String onlineUser : hm.keySet()) {
            onlineUserList.append(onlineUser + " ");
        }

        return onlineUserList.toString();
    }



}
