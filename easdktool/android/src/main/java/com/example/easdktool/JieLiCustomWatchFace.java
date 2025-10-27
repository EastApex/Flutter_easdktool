package com.example.easdktool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.callback.WatchInfoCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.QueryWatchInfoType;
import com.apex.ax_bluetooth.model.EABleWatchInfo;

import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.OtaProgress;
import com.example.easdktool.jieli.watchface.JieliWatchFaceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class JieLiCustomWatchFace {
    final String TAG = this.getClass().getSimpleName();
    final String kArgumentsError = "ArgumentsError";
    final String kCustomWatchFaceResponse = "CustomWatchFaceResponse";
    final String kProgress = "Progress";
    Handler mHandler;
    private MethodChannel channel;
    Context mContext;

    public JieLiCustomWatchFace(MethodChannel channel, Context context) {
        this.channel = channel;
        mContext = context;
    }

    public void jieli707CustomDial(final int style, final String bgPath, final boolean isPreview, final String imageType) {

        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
            @Override
            public void watchInfo(EABleWatchInfo eaBleWatchInfo) {
                if (eaBleWatchInfo != null && eaBleWatchInfo.getLcd_full_h() > 0 && eaBleWatchInfo.getLcd_full_w() > 0) {
                    if (isPreview) {
                        getPreview(style, bgPath, eaBleWatchInfo.getLcd_full_w(), eaBleWatchInfo.getLcd_full_h(), eaBleWatchInfo.getLcd_full_type(), eaBleWatchInfo.getLcd_preview_radius(), imageType);
                    } else {
                        if (!TextUtils.isEmpty(bgPath)) {
                            JieliWatchFaceManager.getInstance().customDialBack(bgPath, new OtaCallback() {
                                @Override
                                public void success() {
                                    if (mHandler == null) {
                                        mHandler = new Handler(Looper.getMainLooper());
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (channel != null) {
                                                OtaProgress otaProgress = new OtaProgress();
                                                otaProgress.isSuccess = 1;
                                                otaProgress.progress = 100;
                                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                            }
                                            mHandler = null;
                                        }
                                    });
                                }

                                @Override
                                public void progress(final int i) {
                                    if (mHandler == null) {
                                        mHandler = new Handler(Looper.getMainLooper());
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            LogUtils.i(TAG, "当前进度:" + i);
                                            if (channel != null) {
                                                OtaProgress otaProgress = new OtaProgress();
                                                otaProgress.isSuccess = 0;
                                                otaProgress.progress = i;
                                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                            }
                                            mHandler = null;
                                        }
                                    });
                                }

                                @Override
                                public void hisUpdateId(int[] ints) {

                                }

                                @Override
                                public void mutualFail(final int i) {
                                    if (mHandler == null) {
                                        mHandler = new Handler(Looper.getMainLooper());
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (channel != null) {
                                                OtaProgress otaProgress = new OtaProgress();
                                                otaProgress.isSuccess = -1;
                                                otaProgress.progress = -1;
                                                otaProgress.errorType = i;
                                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                            }
                                            mHandler = null;
                                        }
                                    });


                                }
                            }, eaBleWatchInfo, style);
                        } else {
                            JieliWatchFaceManager.getInstance().changeCustomDialStyle(style, new GeneralCallback() {
                                @Override
                                public void result(final boolean b, final int i) {
                                    if (b) {
                                        if (mHandler == null) {
                                            mHandler = new Handler(Looper.getMainLooper());
                                        }
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (channel != null) {
                                                    OtaProgress otaProgress = new OtaProgress();
                                                    otaProgress.isSuccess = 1;
                                                    otaProgress.progress = 100;
                                                    channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                                }
                                                mHandler = null;
                                            }
                                        });

                                    } else {
                                        if (mHandler == null) {
                                            mHandler = new Handler(Looper.getMainLooper());
                                        }
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (channel != null) {
                                                    OtaProgress otaProgress = new OtaProgress();
                                                    otaProgress.isSuccess = -1;
                                                    otaProgress.progress = -1;
                                                    otaProgress.errorType = i;
                                                    channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                                }
                                                mHandler = null;
                                            }
                                        });

                                    }

                                }

                                @Override
                                public void mutualFail(final int i) {
                                    if (mHandler == null) {
                                        mHandler = new Handler(Looper.getMainLooper());
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (channel != null) {
                                                OtaProgress otaProgress = new OtaProgress();
                                                otaProgress.isSuccess = -1;
                                                otaProgress.progress = -1;
                                                otaProgress.errorType = i;
                                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                            }
                                            mHandler = null;
                                        }
                                    });

                                }
                            });
                        }
                    }

                } else {
                    if (mHandler == null) {
                        mHandler = new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (channel != null) {
                                channel.invokeMethod(kArgumentsError, "device disconnect");
                            }
                            mHandler = null;
                        }
                    });

                }
            }

            @Override
            public void mutualFail(final int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            channel.invokeMethod(kArgumentsError, i + "");
                        }
                        mHandler = null;
                    }
                });
            }
        });

    }

    private void getPreview(final int style, final String bgPath, final int screenWidth, final int screenHigh, final int screenType, final int angle, final String imageType) {
        //将图片调整成屏幕大小
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap = getFitImage(bgPath, screenWidth, screenHigh, screenType, angle);
                    if (bitmap == null) {
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (channel != null) {
                                    channel.invokeMethod(kArgumentsError, "param error");
                                }
                                mHandler = null;
                            }
                        });

                    } else {
                        Bitmap styleBitmap = null;
                        if (style == 1) {
                            styleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jieli_watchface_1);
                        } else if (style == 2) {
                            styleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jieli_watchface_2);
                        } else if (style == 3) {
                            styleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jieli_watchface_3);
                        } else if (style == 4) {
                            styleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jieli_watchface_4);
                        }
                        mContext = null;

                        byte[] data = null;
                        if (styleBitmap != null) {
                            Bitmap outBitmap = Bitmap.createBitmap(screenWidth, screenHigh, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(outBitmap);
                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            canvas.drawBitmap(bitmap, 0, 0, paint);
                            canvas.drawBitmap(styleBitmap, 0, 0, paint);
                            data = bitmap2Bytes(styleBitmap, imageType);
                            styleBitmap.recycle();

                        } else {
                            data = bitmap2Bytes(bitmap, imageType);
                        }
                        bitmap.recycle();
                        returnData(data);
                    }

                } catch (final Exception e) {
                    if (mHandler == null) {
                        mHandler = new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (channel != null) {
                                channel.invokeMethod(kArgumentsError, e.getMessage());
                            }
                            mHandler = null;

                        }
                    });
                }

            }
        }.start();

    }

    private void returnData(final byte[] data) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kCustomWatchFaceResponse, data);
                }
                mHandler = null;
            }
        });
    }

    private Bitmap getFitImage(final String bgPath, final int screenWidth, final int screenHigh, final int screenType, final int angle) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeFile(bgPath, options);
        int bWidth = bitmap.getWidth();
        int bHigh = bitmap.getHeight();
        if (bWidth < screenWidth || bHigh < screenHigh) {
            float scaleX = screenWidth * 1.0f / bWidth;
            float scaleY = screenHigh * 1.0f / bHigh;
            float maxScale = Math.max(scaleX, scaleY);
            Matrix matrix = new Matrix();
            matrix.setScale(maxScale, maxScale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            LogUtils.e(TAG, "缩放之后图片的大小,宽:" + bitmap.getWidth() + ",高:" + bitmap.getHeight());
        }
        Bitmap outBitmap = Bitmap.createBitmap(screenWidth, screenHigh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (screenType == 1) {//表示圆形
            int raduis = Math.min(screenHigh, screenWidth) / 2;
            canvas.drawCircle(screenWidth / 2, screenHigh / 2, raduis, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        } else {
            RectF rectF = new RectF(0, 0, screenWidth, screenHigh);
            canvas.drawRoundRect(rectF, angle, angle, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return outBitmap;
    }

    private byte[] bitmap2Bytes(Bitmap outBitmap, String fileType) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (fileType.equalsIgnoreCase("JPEG") || fileType.equalsIgnoreCase("JPG")) {
            outBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else {
            outBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        }
        byte[] data = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        byteArrayOutputStream = null;
        return data;

    }


}
