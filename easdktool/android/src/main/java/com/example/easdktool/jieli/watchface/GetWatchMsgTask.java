package com.example.easdktool.jieli.watchface;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.jieli.jl_fatfs.FatFsErrCode;
import com.jieli.jl_fatfs.model.FatFile;
import com.jieli.jl_rcsp.impl.WatchOpImpl;
import com.jieli.jl_rcsp.interfaces.listener.ThreadStateListener;
import com.jieli.jl_rcsp.interfaces.watch.OnWatchOpCallback;
import com.jieli.jl_rcsp.model.base.BaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqjasonZhong
 * @email zhongzhuocheng@zh-jieli.com
 * @desc
 * @since 2021/3/31
 */
public class GetWatchMsgTask extends Thread {
    final String TAG = this.getClass().getSimpleName();
    private final WatchOpImpl mWatchOp;
    private final List<FatFile> taskList;
    private final JieliDialCallback mCallback;
    private final ThreadStateListener mStateListener;
    private final ArrayList<JieliWatchInfo> watchList = new ArrayList<>();

     Handler mHandler = new Handler(Looper.getMainLooper());
    private final Object mLock = new Object();
    private volatile boolean isLock;

    public GetWatchMsgTask(WatchOpImpl impl, List<FatFile> list, JieliDialCallback callback, ThreadStateListener listener) {
        mWatchOp = impl;
        taskList = list;
        mCallback = callback;
        mStateListener = listener;
    }

    @Override
    public void run() {
        if (null != mStateListener) mStateListener.onStart(getId());
        if (taskList == null || taskList.isEmpty()) {
            if (null != mStateListener) {
                mStateListener.onFinish(getId());
            }
            if (mCallback != null) {
                mCallback.jieliDial(null);
            }
            return;
        }
        synchronized (mLock) {
            for (final FatFile fatFile : taskList) {
                isLock = false;
                mWatchOp.getWatchMessage(fatFile.getPath(), new OnWatchOpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        final String watchMsg = result;
                        Log.i(TAG, "getWatchMessage >>> -onSuccess- result = " + result + ", path = " + fatFile.getPath());
                        mWatchOp.getCustomWatchBgInfo(fatFile.getPath(), new OnWatchOpCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.w(TAG, "getCustomWatchBgInfo >>> -onSuccess- result = " + result + ", path = " + fatFile.getPath());
                                String customBgPath = result;
                                if (!"null".equals(customBgPath)) {
                                    customBgPath = "/" + getFileName(result).toUpperCase();//自定义的背景名
                                }
                                JieliWatchInfo watchInfo = new JieliWatchInfo();
                                watchInfo.name = fatFile.getName();
                                watchInfo.mFatFile = fatFile;
                                watchInfo.version = watchMsg;
                                watchInfo.size = fatFile.getSize() * 4 * 1024;
                                watchInfo.status = 1;
                                watchInfo.customBgFatPath = customBgPath;
                                watchInfo.path = fatFile.getPath();
                                watchList.add(watchInfo);

                                synchronized (mLock) {
                                    if (isLock) {
                                        mLock.notify();
                                    }
                                }
                            }

                            @Override
                            public void onFailed(BaseError error) {
                                synchronized (mLock) {
                                    if (isLock) {
                                        mLock.notify();
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailed(BaseError error) {
                        synchronized (mLock) {
                            if (isLock) {
                                mLock.notify();
                            }
                        }
                    }
                });
                isLock = true;
                try {
                    mLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isLock = false;
                }
            }
            if (mCallback != null) {
                mHandler.post(() -> {
                    if (watchList.isEmpty()) {
                        mCallback.error(new BaseError(FatFsErrCode.RES_RCSP_SEND, "request watch message failed."));
                    } else {
                        mCallback.jieliDial(watchList);
                    }
                    mHandler=null;
                });
            }
        }
        if (null != mStateListener) mStateListener.onFinish(getId());
    }

    private String getFileName(String filePath) {
        if (filePath == null) return null;
        int index = filePath.lastIndexOf("/");
        if (index > -1) {
            return filePath.substring(index + 1);
        } else {
            return filePath;
        }
    }
}
