package com.yp.client.service;

import java.util.HashMap;
/**
 * @author 杨鹏
 * @version 1.0
 */
public class ManageClientConnectServerThread extends Thread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }

    @Override
    public void run() {

    }
}
