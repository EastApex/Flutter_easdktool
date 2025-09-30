package com.example.easdktool;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.model.EABleWatchInfo;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.custom_dial.CustomDialCallback;
import com.example.custom_dial.CustomDiffTxtColorDialParam;
import com.example.custom_dial.CustomPointDialParam;
import com.example.custom_dial.NewRGBAPlatformDiffTxtUtils;
import com.example.custom_dial.NewRGBApointUtils;
import com.example.easdktool.been.CustomWatchFace;
import com.example.easdktool.callback.PrewMapCallBack;
import com.example.easdktool.callback.WatchFileCallback;

import java.io.File;

public class CustomWatchFaceUtils {
    final String TAG = this.getClass().getSimpleName();
    private EABleWatchInfo eaBleWatchInfo;
    private CustomWatchFace customWatchFace;
    private Context mContext;
    Typeface currentTypeFace;
    WatchFileCallback watchFileCallback;
    Bitmap prewBitmap;
    private int txtColor = Color.BLACK;

    public CustomWatchFaceUtils(EABleWatchInfo eaBleWatchInfo, CustomWatchFace customWatchFace, Context mContext, Typeface currentTypeFace, WatchFileCallback watchFileCallback, Bitmap prewBitmap) {
        this.eaBleWatchInfo = eaBleWatchInfo;
        this.customWatchFace = customWatchFace;
        this.mContext = mContext;
        this.currentTypeFace = currentTypeFace;
        this.watchFileCallback = watchFileCallback;
        this.prewBitmap = prewBitmap;
    }

    public void createWatchFaceFile() {
        int screenWidth = eaBleWatchInfo.getLcd_full_w();
        int screenType = eaBleWatchInfo.getLcd_full_type();
        int screenHigh = eaBleWatchInfo.getLcd_full_h();
        int coreType = eaBleWatchInfo.getLcd_pixel_type();
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap backBitmap = BitmapFactory.decodeFile(customWatchFace.bgImagePath, options2);
        if (backBitmap != null) {
            int bWidth = backBitmap.getWidth();
            int bHigh = backBitmap.getHeight();
            if (bWidth < screenWidth || bHigh < screenHigh) {
                float wScale = screenWidth * 1.0f / bWidth;
                float hScale = screenHigh * 1.0f / bHigh;
                float mScale = Math.max(wScale, hScale);
                Matrix matrix = new Matrix();
                matrix.setScale(mScale, mScale);
                backBitmap = Bitmap.createBitmap(backBitmap, 0, 0, backBitmap.getWidth(), backBitmap.getHeight(), matrix, false);
                LogUtils.e(TAG, "缩放之后图片的大小,宽:" + backBitmap.getWidth() + ",高:" + backBitmap.getHeight());
            }
        }
        if (customWatchFace.isNumberWf) {
            if (!TextUtils.isEmpty(customWatchFace.numbeColorHex)) {
                txtColor = Color.parseColor(customWatchFace.numbeColorHex);
            }
            int startX = 0;
            int startY = 0;
            int weekX = 0;
            int weekY = 0;
            int dataX = 0;
            if (screenWidth == 240) {
                if (screenType == 1) {
                    startX = 58;
                    startY = 30;
                    weekX = 80;
                    weekY = 84;
                    dataX = 133;
                } else {
                    if (screenHigh == 296) {
                        startX = 47;
                        startY = 20;
                        weekX = 75;
                        weekY = 70;
                        dataX = 129;
                    } else if (screenHigh == 280) {
                        startX = 47;
                        startY = 20;
                        weekX = 72;
                        weekY = 70;
                        dataX = 140;
                    } else {
                        startX = 47;
                        startY = 20;
                        weekX = 60;
                        weekY = 70;
                        dataX = 146;
                    }
                }
            } else if (screenWidth == 320) {
                startX = 82;
                startY = 35;
                weekX = 95;
                weekY = 110;
                dataX = 150;

            } else if (screenWidth == 340) {
                startX = 95;
                startY = 35;
                weekX = 98;
                weekY = 110;
                dataX = 167;
            } else if (screenWidth == 356) {
                startX = 88;
                startY = 20;
                weekX = 75;
                weekY = 95;
                dataX = 155;
            } else if (screenWidth == 360) {
                if (screenType == 1) {
                    if (!TextUtils.isEmpty(eaBleWatchInfo.getWatchType())) {
                        if (eaBleWatchInfo.getWatchType().equalsIgnoreCase("APEX_G16")) {
                            startX = 90;
                            startY = 20;
                            weekX = 125;
                            weekY = 95;
                            dataX = 125;
                        } else if (eaBleWatchInfo.getWatchType().contains("Solar Neo")) {
                            startX = 90;
                            startY = 20;
                            weekX = 120;
                            weekY = 95;
                            dataX = 123;
                        } else {
                            startX = 90;
                            startY = 20;
                            weekX = 100;
                            weekY = 95;
                            dataX = 140;
                        }
                    }
                } else {
                    startX = 90;
                    startY = 20;
                    weekX = 135;
                    weekY = 95;
                    dataX = 105;
                }
            } else if (screenWidth == 368) {
                startX = 98;
                startY = 20;
                weekX = 98;
                weekY = 95;
                dataX = 163;
            } else if (screenWidth == 390) {
                Log.e(TAG, "走了这里");
                startX = 96;
                startY = 40;
                weekX = 95;
                weekY = 140;
                if (coreType == 2) {
                    if (screenHigh == 450) {
                        dataX = 190;
                    } else {
                        dataX = 200;
                    }
                } else {
                    dataX = 220;
                }
            } else if (screenWidth == 410) {
                startX = 105;
                startY = 20;
                weekX = 117;
                weekY = 140;
                dataX = 193;
            } else if (screenWidth == 412) {
                startX = 107;
                startY = 20;
                weekX = 118;
                weekY = 140;
                dataX = 193;
            } else if (screenWidth == 466) {
                startX = 127;
                startY = 30;
                weekX = 165;
                weekY = 140;
                dataX = 235;
            }
            if (startX != 0) {
                CustomDiffTxtColorDialParam customDiffTxtColorDialParam = new CustomDiffTxtColorDialParam();
                customDiffTxtColorDialParam.setDateX(dataX);
                customDiffTxtColorDialParam.setWy(weekY);
                customDiffTxtColorDialParam.setWx(weekX);
                customDiffTxtColorDialParam.setStartY(startY);
                customDiffTxtColorDialParam.setStartX(startX);
                customDiffTxtColorDialParam.setTxtColor(txtColor);
                customDiffTxtColorDialParam.setScreenType(screenType);
                customDiffTxtColorDialParam.setCornerRadius(eaBleWatchInfo.getLcd_preview_radius());
                customDiffTxtColorDialParam.setScreenWidth(screenWidth);
                customDiffTxtColorDialParam.setScreenHigh(screenHigh);
                customDiffTxtColorDialParam.setpWidth(eaBleWatchInfo.getLcd_preview_w());
                customDiffTxtColorDialParam.setpHigh(eaBleWatchInfo.getLcd_preview_h());
                customDiffTxtColorDialParam.setBackBitmap(backBitmap);
                customDiffTxtColorDialParam.setPreviewBitmap(prewBitmap);
                final NewRGBAPlatformDiffTxtUtils rgbaPlatformDiffTxtUtils = new NewRGBAPlatformDiffTxtUtils(mContext.getApplicationContext(), coreType);
                rgbaPlatformDiffTxtUtils.showData(true);
                rgbaPlatformDiffTxtUtils.produceDialBin(customDiffTxtColorDialParam, new CustomDialCallback() {
                    @Override
                    public void dialPath(final String s) {
                        mContext = null;
                        rgbaPlatformDiffTxtUtils.destroy();
                        if (watchFileCallback != null) {
                            watchFileCallback.watchFaceFile(s);
                        }


                    }
                });
            } else {
                mContext = null;
                if (watchFileCallback != null) {
                    watchFileCallback.watchFaceFile(null);
                }
            }

        } else {
            CustomPointDialParam customPointDialParam = new CustomPointDialParam();
            customPointDialParam.setBackBitmap(backBitmap);
            customPointDialParam.setCornerRadius(eaBleWatchInfo.getLcd_preview_radius());
            customPointDialParam.setPreviewHigh(eaBleWatchInfo.getLcd_preview_h());
            customPointDialParam.setPreviewWidth(eaBleWatchInfo.getLcd_preview_h());
            customPointDialParam.setScreenHigh(screenHigh);
            customPointDialParam.setScreenType(screenType);
            customPointDialParam.setScreenWidth(screenWidth);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap tBit = null, hourBit = null, minuteBit = null, secondBit = null, frameBit = null;
            if (customWatchFace.pointerColorType == 0) {
                if (screenWidth == 240) {
                    if (screenHigh == 240) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_240_circle, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_240_circle, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_240_circle, options);
                        customPointDialParam.sethTop(65);
                        customPointDialParam.setmTop(23);
                        customPointDialParam.setsTop(9);
                    } else if (screenHigh == 296) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_240, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_240, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_240, options);
                        customPointDialParam.sethTop(90);
                        customPointDialParam.setmTop(47);
                        customPointDialParam.setsTop(35);
                    } else {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_240, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_240, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_240, options);
                        customPointDialParam.sethTop(83);
                        customPointDialParam.setmTop(40);
                        customPointDialParam.setsTop(27);
                    }

                } else if (screenWidth == 320) {
                    if (screenHigh == 386) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_320, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_320, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_320, options);
                        customPointDialParam.sethTop(118);
                        customPointDialParam.setmTop(64);
                        customPointDialParam.setsTop(45);
                    } else {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_320, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_320, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_320, options);
                        customPointDialParam.sethTop(118);
                        customPointDialParam.setmTop(64);
                        customPointDialParam.setsTop(45);
                    }
                } else if (screenWidth == 340) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_320, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_320, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_320, options);
                    customPointDialParam.sethTop(92);
                    customPointDialParam.setmTop(42);
                    customPointDialParam.setsTop(23);
                } else if (screenWidth == 356) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    customPointDialParam.sethTop(104);
                    customPointDialParam.setmTop(64);
                    customPointDialParam.setsTop(36);
                } else if (screenWidth == 360) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    customPointDialParam.sethTop(84);
                    customPointDialParam.setmTop(44);
                    customPointDialParam.setsTop(16);
                } else if (screenWidth == 368) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    customPointDialParam.sethTop(128);
                    customPointDialParam.setmTop(88);
                    customPointDialParam.setsTop(60);
                } else if (screenWidth == 390) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    if (coreType != 2) {
                        customPointDialParam.sethTop(100);
                        customPointDialParam.setmTop(60);
                        customPointDialParam.setsTop(31);
                    } else {
                        customPointDialParam.sethTop(130);
                        customPointDialParam.setmTop(89);
                        customPointDialParam.setsTop(62);
                    }
                } else if (screenWidth == 410) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    customPointDialParam.sethTop(155);
                    customPointDialParam.setmTop(115);
                    customPointDialParam.setsTop(87);
                } else if (screenWidth == 412) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_356, options);
                    customPointDialParam.sethTop(156);
                    customPointDialParam.setmTop(116);
                    customPointDialParam.setsTop(88);
                } else if (screenWidth == 466) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_black_466, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_black_466, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_black_466, options);
                    customPointDialParam.sethTop(126);
                    customPointDialParam.setmTop(41);
                    customPointDialParam.setsTop(17);
                }

            } else if (customWatchFace.pointerColorType == 1) {
                if (screenWidth == 240) {
                    if (screenHigh == 240) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_240_circle, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_240_circle, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_240_circle, options);
                        customPointDialParam.sethTop(65);
                        customPointDialParam.setmTop(23);
                        customPointDialParam.setsTop(9);
                    } else if (screenHigh == 296) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_240, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_240, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_240, options);
                        customPointDialParam.sethTop(90);
                        customPointDialParam.setmTop(47);
                        customPointDialParam.setsTop(35);
                    } else {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_240, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_240, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_240, options);
                        customPointDialParam.sethTop(83);
                        customPointDialParam.setmTop(40);
                        customPointDialParam.setsTop(27);
                    }
                } else if (screenWidth == 320) {
                    if (screenHigh == 386) {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_320, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_320, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_320, options);
                        customPointDialParam.sethTop(118);
                        customPointDialParam.setmTop(64);
                        customPointDialParam.setsTop(45);
                    } else {
                        hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_320, options);
                        minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_320, options);
                        secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_320, options);
                        customPointDialParam.sethTop(118);
                        customPointDialParam.setmTop(64);
                        customPointDialParam.setsTop(45);
                    }
                } else if (screenWidth == 340) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_320, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_320, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_320, options);
                    customPointDialParam.sethTop(92);
                    customPointDialParam.setmTop(42);
                    customPointDialParam.setsTop(23);
                } else if (screenWidth == 356) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    customPointDialParam.sethTop(104);
                    customPointDialParam.setmTop(64);
                    customPointDialParam.setsTop(36);
                } else if (screenWidth == 360) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    customPointDialParam.sethTop(84);
                    customPointDialParam.setmTop(44);
                    customPointDialParam.setsTop(16);
                } else if (screenWidth == 368) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    customPointDialParam.sethTop(128);
                    customPointDialParam.setmTop(88);
                    customPointDialParam.setsTop(60);
                } else if (screenWidth == 390) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    if (coreType != 2) {
                        customPointDialParam.sethTop(100);
                        customPointDialParam.setmTop(60);
                        customPointDialParam.setsTop(31);
                    } else {
                        customPointDialParam.sethTop(130);
                        customPointDialParam.setmTop(89);
                        customPointDialParam.setsTop(62);
                    }
                } else if (screenWidth == 410) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    customPointDialParam.sethTop(155);
                    customPointDialParam.setmTop(115);
                    customPointDialParam.setsTop(87);
                } else if (screenWidth == 412) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_356, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_356, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_356, options);
                    customPointDialParam.sethTop(156);
                    customPointDialParam.setmTop(116);
                    customPointDialParam.setsTop(88);
                } else if (screenWidth == 466) {
                    hourBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_hour_white_466, options);
                    minuteBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_minute_white_466, options);
                    secondBit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_second_white_466, options);
                    customPointDialParam.sethTop(126);
                    customPointDialParam.setmTop(41);
                    customPointDialParam.setsTop(17);
                }
            }
            if (hourBit != null) {
                customPointDialParam.setHourBitmap(hourBit);
                customPointDialParam.setSecondBitmap(secondBit);
                customPointDialParam.setMinuteBitmap(minuteBit);
                customPointDialParam.setPreviewBitmap(prewBitmap);
                customPointDialParam.setFrameBitmap(frameBit);
                final NewRGBApointUtils rgbaPointUtils = new NewRGBApointUtils(mContext.getApplicationContext(), coreType);
                rgbaPointUtils.produceDialBin(customPointDialParam, new CustomDialCallback() {
                    @Override
                    public void dialPath(final String s) {
                        mContext = null;
                        rgbaPointUtils.destroy();
                        if (watchFileCallback != null) {
                            watchFileCallback.watchFaceFile(s);
                        }

                    }
                });
            } else {
                mContext = null;
                if (watchFileCallback != null) {
                    watchFileCallback.watchFaceFile(null);
                }
            }

        }


    }
}
