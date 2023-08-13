package com.yp.client.service;
import com.yp.common.Message;
import com.yp.common.MessageType;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_USERS)) {

                    String[] onlineUserArray = message.getContent().split(" ");

                    System.out.println("0000000000在线用户0000000000");
                    for (String onlineUser : onlineUserArray) {
                        System.out.println("\t用户：" + onlineUser);
                    }

                }else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES )) {

                    System.out.println("\n" + message.getSender() + " 对 " +
                            message.getGetter() + " 说：" + message.getContent() + "  " + message.getSendTime());

                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES )) {
                    System.out.println("\n(群发消息)");
                    System.out.println(message.getSender() + " 对大家说 "
                            + message.getContent() + "  " + message.getSendTime());

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES_FAIL)){
                    System.out.println(message.getContent());
                }else {
                    System.out.println("其它情况");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
