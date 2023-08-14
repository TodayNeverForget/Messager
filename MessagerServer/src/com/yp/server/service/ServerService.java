package com.yp.server.service;

import com.yp.common.Message;
import com.yp.common.MessageType;
import com.yp.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class ServerService {
    private ServerSocket serverSocket;
    private static HashMap<String, User> validUsers = new HashMap<>();

    static {
        validUsers.put("191215042", new User("191215042", "superstar123"));
        validUsers.put("191215020", new User("191215020", "123456"));
        validUsers.put("191215001", new User("191215001", "123456"));
        validUsers.put("191215055", new User("191215055", "123456"));
    }

    public boolean userCheck(String userId, String passwd) {
        User user = validUsers.get(userId);

        if (user == null) {
            return false;
        }
        if (!user.getPasswd().equals(passwd)) {
            return false;
        }

        return true;

    }

    public ServerService() {
        Socket socket;
        User user;
        Message message;
        new MessagePush().start();
//        ManageServerConnectClientThread manageServerConnectClientThread = new ManageServerConnectClientThread();

        try {
            System.out.println("服务端监听端口...");
            serverSocket = new ServerSocket(8888);


            while (true) {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                user = (User) ois.readObject();

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                message = new Message();
                if (userCheck(user.getUserId(), user.getPasswd())) {
                    //登陆成功
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(user.getUserId(), socket);
                    serverConnectClientThread.start();
                    System.out.println("服务端与客户端 " + user.getUserId() + " 连接中...");
                    ManageServerConnectClientThread.addServerConnectClientThread(user.getUserId(),
                            serverConnectClientThread);

                    //判断该用户是否有离线消息
                    OfflineService.checkofflineInformation(user.getUserId());
                } else {
                    //登陆失败
                    System.out.println("登陆失败！！！");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}











