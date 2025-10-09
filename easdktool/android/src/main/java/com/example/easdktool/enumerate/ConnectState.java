package com.example.easdktool.enumerate;
//绑定状态 0:连接失败 1:连接成功 2:断开连接 3:连接超时 4:无此设备
public enum ConnectState {
    fail(0),
    succ(1),
    disConnect(2),
    timeout(3),
    notFind(4);

    private int value;

    ConnectState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
