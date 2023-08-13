package com.yp.client.service;

import com.yp.client.utils.StreamUtils;
import com.yp.common.Message;
import com.yp.common.MessageType;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class FileService {
    Message message;

    public void sendFileToOne(String sender, String getter, String content) {
        String srcPath = content.split(" ")[0];
        content = content.split(" ")[1];

        //判断源文件是否存在
        if (!new File(srcPath).exists()) {
            System.out.println("源文件不存在，取消被发送！");
            return;
        }

        message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(content);
        message.setSendTime(new Date().toString());

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcPath));

            byte[] file = StreamUtils.streamToByteArray(bis);
            message.setFile(file);

            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread
                            (sender).getSocket().getOutputStream());

            oos.writeObject(message);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
