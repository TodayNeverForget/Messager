package com.yp.server.service;

import com.yp.common.Message;
import com.yp.common.MessageType;
import com.yp.utils.Utility;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class MessagePush extends Thread {
    Message message;


    @Override
    public void run() {

        while (true) {

            System.out.print("请输入您要推送的内容：");

            message = new Message();
            message.setContent(Utility.readString(200));
            message.setMesType(MessageType.MESSAGE_SERV_MES);
            message.setSendTime(new Date().toString());

            HashMap<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();

            for (ServerConnectClientThread thread : hm.values()) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream
                            (thread.getSocket().getOutputStream());
                    oos.writeObject(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("消息推送成功！");

        }


    }

}















