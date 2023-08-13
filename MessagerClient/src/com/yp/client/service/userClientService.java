package com.yp.client.service;

import com.yp.common.Message;
import com.yp.common.MessageType;
import com.yp.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class userClientService {
//    private User user = new User();
    private User user;
    private Message message;
    Socket socket;

    public boolean checkUser(String userId, String passwd) {
        boolean isSuccess = false;
        user = new User(userId, passwd);
        /*user.setUserId(userId);
        user.setPasswd(passwd);*/


        try {
            socket = new Socket("127.0.0.1", 8888);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
//            socket.shutdownOutput();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();


            if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();//启动线程
                System.out.println("客户端线程，等待接收服务端消息...");
                ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);
                isSuccess = true;
            } else {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    //该实现应该做成一个消息类 以后期处理更多的消息发送
    public void sendMessage(String getter, String content) {
        message = new Message();
        message.setMesType(MessageType.MESSAGE_CONVERSATION);
        message.setSender(user.getUserId());
        message.setGetter(getter);
        message.setContent(content);

        try {
            //为什么不用该类本身的socket呢？
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                            getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getOnlineUsersList() {
        message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USERS);
        message.setSender(user.getUserId());

        try {
            //为什么不用该类本身的socket呢？
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void logout() {
        message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId());

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}












