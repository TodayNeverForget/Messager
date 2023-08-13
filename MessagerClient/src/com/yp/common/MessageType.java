package com.yp.common;

/**
 * @author 杨鹏
 * @version 1.0
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; //登录成功
    String MESSAGE_LOGIN_FAIL = "2";
    String MESSAGE_COMM_MES = "3";
    String MESSAGE_TO_ALL_MES = "4";
    String MESSAGE_COMM_MES_FAIL = "5";
    String MESSAGE_GET_ONLINE_USERS = "6";
    String MESSAGE_RET_ONLINE_USERS = "7";
    String MESSAGE_CLIENT_EXIT = "9";

}
