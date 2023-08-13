package com.yp.client.service;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }

    public static HashSet getClientConnetServerUsersId() {
        HashSet<String> UsersId = new HashSet<>();

        for (String userId : hm.keySet()) {
            UsersId.add(userId);
        }

        return UsersId;

    }

}
