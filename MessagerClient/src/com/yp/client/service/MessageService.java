package com.yp.client.service;

import com.yp.common.Message;
import com.yp.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class MessageService {
    Message message;

    public void sendMessageToOne(String sender, String getter, String content) {

         message = new Message();

         message.setMesType(MessageType.MESSAGE_COMM_MES);
         message.setSender(sender);
         message.setGetter(getter);
         message.setContent(content);
         message.setSendTime(new Date().toString());

        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread
                            (sender).getSocket().getOutputStream());

            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendMessageToAll(String sender, String content) {
        message = new Message();

        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(sender);
        message.setContent(content);
        message.setSendTime(new Date().toString());

        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread
                            (sender).getSocket().getOutputStream());

            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
















