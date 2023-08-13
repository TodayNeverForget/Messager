package com.yp.client.view;

import com.yp.client.service.userClientService;
import com.yp.client.utils.Utility;
import org.junit.jupiter.api.Test;

/**
 * @author 杨鹏
 * @version 1.0
 * 客户端菜单
 */
public class View {
    private String input;
    userClientService userClientService = new userClientService();

    public static void main(String[] args) {
        new View().mainMenu();

    }

    //主菜单界面
    public void mainMenu() {
        boolean loop = true;

        while (loop) {

            System.out.println("0000000000欢迎进入网络通讯系统0000000000");
            System.out.println("\t1. 登录");
            System.out.println("\t9. 关闭");
            System.out.print("\n\n\n请选择: ");
            input = Utility.readString(1);

            switch (input) {
                case "1":
                    System.out.print("账  号：");
                    String userId;
                    userId = Utility.readString(20);
                    System.out.print("密  码：");
                    String passwd;
                    passwd = Utility.readString(50);
                    if (userClientService.checkUser(userId, passwd)) {
                        secMenu(userId);
                    } else {
                        System.out.println("登录失败！");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }

        System.out.println("退出系统");

    }

    //二级菜单界面
    public void secMenu(String user) {
        boolean loop = true;

        while (loop) {
            System.out.println("000000000||欢迎" + user + "||000000000");
            System.out.println("\t1. 获取在线用户");
            System.out.println("\t2. 群发消息");
            System.out.println("\t3. 私发消息");
            System.out.println("\t4. 更多功能...");
            System.out.println("\t9. 退出");
            System.out.print("\n\n\n请选择: ");
            input = Utility.readString(1);

            switch (input) {
                case "1":
                    userClientService.getOnlineUsersList();
                    //因列表显示延迟问题 预估需要在此休眠10ms等待列表回送显示完成
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }

    }

    //在线用户界面
    public void onlineUsers() {
        boolean loop = true;

        while (loop) {
            System.out.println("0000000000在线用户0000000000");
//            userClientService.getOnlineUsersList();
            System.out.println("\t9. 返回");
            System.out.print("\n\n\n请选择: ");
            input = Utility.readString(1);

            switch (input) {
                case "9":
                    loop = false;
                    break;
            }

        }

    }


}
