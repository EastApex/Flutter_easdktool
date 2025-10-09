package com.example.easdktool.jieli.watchface;


import com.jieli.jl_fatfs.model.FatFile;

public class JieliWatchInfo {
    public String name; //表盘名
    public int status; //表盘状态
    public String bitmapUri; //表盘缩略图链接
    public String uuid; //表盘服务器唯一标识
    public String version; //表盘版本
    public long size; //表盘文件大小
    public String fileUrl; //文件链接
    public FatFile mFatFile; //表盘文件信息
    public String updateUUID;  //更新的UUID
    public String updateVersion; //更新版本
    public String updateUrl; //更新下载链接
    public String customBgFatPath; //自定义背景路径
    public String path;//表盘路径
}
