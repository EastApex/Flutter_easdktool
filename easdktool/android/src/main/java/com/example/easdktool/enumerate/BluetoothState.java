package com.example.easdktool.enumerate;

//蓝牙状态 0:未开启蓝牙 1:蓝牙开启 2:蓝牙未授权 3:定位未开启 4:不支持BLE
public enum BluetoothState {
    unOpen(0),
    open(1),
    unAuthorized(2),
    unOpenLocation(3),
    unSupportBle(4);

    private int value;

    BluetoothState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
