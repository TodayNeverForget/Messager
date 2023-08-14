package com.yp.server.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import com.yp.common.*;
/**
 * @author 杨鹏
 * @version 1.0
 */
public class OfflineService {
    private static HashMap<String, ArrayList<Message>> offlineInformation = new HashMap<>();


    public static boolean checkOfflineUsers(String userId, Message message) {

        if (userId == null ) return false;

        if (ManageServerConnectClientThread.getHm().get(userId) == null) {
            offlineInformation.putIfAbsent(userId, new ArrayList<Message>());
            offlineInformation.get(userId).add(message);
            return true;
        }

        return false;
    }

    public static void checkofflineInformation(String userId) {

        if (offlineInformation.get(userId) != null) {
            ObjectOutputStream oos;
            for (Message message : offlineInformation.get(userId)) {
                try {
                    oos = new ObjectOutputStream(ManageServerConnectClientThread
                            .getServerConnectClientThread(userId).getSocket().getOutputStream());
                    oos.writeObject(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("用户 " + userId + " 接收离线消息");
            offlineInformation.remove(userId);

        }

    }


}










