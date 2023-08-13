package com.yp.server.service;

import com.yp.common.Message;
import com.yp.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

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

    public String getUserId() {
        return userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        while (true) {

            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //处理用户获取在线用户列表信息
                if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("用户||" + message.getSender() + "||向||" + message.getGetter() + "||发送消息");
                    if (ManageServerConnectClientThread.getServerConnectClientThread(message.getGetter()) != null) {
                        ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getGetter());
                        ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                        System.out.println("已发送！");
                    } else {
                        Message message2 = new Message();
                        message2.setMesType(MessageType.MESSAGE_COMM_MES_FAIL);
                        message2.setContent("对方已离线！");

                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message2);

                        System.out.println("||" + message.getGetter() + "||已离线");
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USERS)) {
                    System.out.println("用户||" + message.getSender() + "||获取在线用户列表");

                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_USERS);
                    message2.setGetter(message.getSender());
                    message2.setContent(ManageServerConnectClientThread.getOnlineUsers());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {

                    System.out.println("用户||" + message.getSender() + "||发出群发消息");
                    ManageServerConnectClientThread.sendMessageToAll(message);
                    System.out.println("已发送！");

                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println("客户端||" + message.getSender() + "||退出系统");
                    ManageServerConnectClientThread.removeServerConnectClientThread(message.getSender());
                    socket.close();
                    break;
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

}












