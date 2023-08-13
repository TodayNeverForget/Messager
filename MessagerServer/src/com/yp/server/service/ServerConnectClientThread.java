package com.yp.server.service;

import com.yp.common.Message;
import com.yp.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ServerConnectClientThread extends Thread {
    private String userId;
    private Socket socket;

    public ServerConnectClientThread(String userId, Socket socket) {
        this.userId = userId;
        this.socket = socket;
    }

    @Override
    public void run() {

        while (true) {

            try {
                System.out.println("服务端与客户端 " + userId + " 连接中...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //处理用户获取在线用户列表信息
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USERS)) {
                    System.out.println("用户||" + message.getSender() + "||获取在线用户列表");

                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_USERS);
                    message2.setGetter(message.getSender());
                    message2.setContent(ManageServerConnectClientThread.getOnlineUsers());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                }



            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

}












