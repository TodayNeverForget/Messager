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

    public boolean checkUser(String userId, String passwd) {
        boolean isSuccess = false;
        user = new User(userId, passwd);
        /*user.setUserId(userId);
        user.setPasswd(passwd);*/


        try {
            Socket socket = new Socket("127.0.0.1", 8888);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
//            socket.shutdownOutput();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();


            if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();//启动线程
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

    public void getOnlineUsersList() {
        message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USERS);
        message.setSender(user.getUserId());

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}












