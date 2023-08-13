package com.yp.client.service;
import com.yp.common.Message;
import com.yp.common.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ClientConnectServerThread extends Thread{
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;

        while (true) {

            try {
                System.out.println("客户端线程，等待从服务端发来的消息...");
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_USERS)) {

                    String[] onlineUserArray = message.getContent().split(" ");

                    System.out.println("0000000000在线用户0000000000");
                    for (String onlineUser : onlineUserArray) {
                        System.out.println("\t用户：" + onlineUser);
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
