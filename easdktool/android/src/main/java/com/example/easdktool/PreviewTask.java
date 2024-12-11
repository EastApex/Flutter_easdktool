package com.example.easdktool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.apex.bluetooth.model.EABleWatchInfo;
import com.apex.bluetooth.utils.LogUtils;
import com.example.custom_dial.NewRGBAPlatformDiffTxtUtils;
import com.example.custom_dial.NewRGBApointUtils;
import com.example.easdktool.been.CustomWatchFace;
import com.example.easdktool.callback.PrewMapCallBack;

import java.io.File;


class PreviewTask extends AsyncTask<Void, Void, Bitmap> {
    final String TAG = this.getClass().getSimpleName();
    private EABleWatchInfo eaBleWatchInfo;
    private CustomWatchFace customWatchFace;
    private Context mContext;
    Typeface currentTypeFace;
    private int txtColor = Color.BLACK;
    private PrewMapCallBack prewMapCallBack;

    public PreviewTask(EABleWatchInfo eaBleWatchInfo, CustomWatchFace customWatchFace, Context mContext, Typeface currentTypeFace) {

        this.eaBleWatchInfo = eaBleWatchInfo;
        this.customWatchFace = customWatchFace;
        this.mContext = mContext;
        this.currentTypeFace = currentTypeFace;
    }

    public void setPrewMapCallBack(PrewMapCallBack prewMapCallBack) {
        this.prewMapCallBack = prewMapCallBack;
    }

    @Override
    protected Bitmap doInBackground(Void... integers) {
        try {
            LogUtils.e(TAG, "开始生成缩略图");
            if (customWatchFace == null || TextUtils.isEmpty(customWatchFace.bgImagePath) || !new File(customWatchFace.bgImagePath).exists() || eaBleWatchInfo == null) {
                LogUtils.e(TAG, "param error");
                return null;
            }
            int screenWidth = eaBleWatchInfo.getLcd_full_w();
            int screenType = eaBleWatchInfo.getLcd_full_type();
            int screenHigh = eaBleWatchInfo.getLcd_full_h();
            int raduis = eaBleWatchInfo.getLcd_preview_radius();
            int prewHigh = eaBleWatchInfo.getLcd_preview_h();
            int prewWidth = eaBleWatchInfo.getLcd_preview_w();
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
                            weekX = 95;
                            weekY = 70;
                            dataX = 121;
                        } else if (screenHigh == 280) {
                            startX = 47;
                            startY = 20;
                            weekX = 95;
                            weekY = 70;
                            dataX = 121;
                        } else if (screenHigh == 240) {
                            startX = 47;
                            startY = 20;
                            weekX = 95;
                            weekY = 70;
                            dataX = 121;
                        } else {
                            startX = 47;
                            startY = 20;
                            weekX = 60;
                            weekY = 70;
                            dataX = 146;
                        }
                    }
                } else if (screenWidth == 320) {
                    startX = 85;
                    startY = 35;
                    weekX = 115;
                    weekY = 110;
                    dataX = 165;
                } else if (screenWidth == 340) {
                    startX = 95;
                    startY = 35;
                    weekX = 122;
                    weekY = 110;
                    dataX = 173;
                } else if (screenWidth == 356) {
                    startX = 88;
                    startY = 20;
                    weekX = 75;
                    weekY = 95;
                    dataX = 155;
                } else if (screenWidth == 360) {
                    startX = 90;
                    startY = 20;
                    weekX = 105;
                    weekY = 95;
                    dataX = 190;
                } else if (screenWidth == 368) {
                    startX = 98;
                    startY = 20;
                    weekX = 112;
                    weekY = 95;
                    dataX = 193;
                } else if (screenWidth == 390) {
                    startX = 95;
                    startY = 20;
                    weekX = 95;
                    weekY = 140;
                    dataX = 220;
                } else if (screenWidth == 410 || screenWidth == 412) {
                    startX = 106;
                    startY = 20;
                    weekX = 120;
                    weekY = 140;
                    dataX = 220;
                } else if (screenWidth == 466) {
                    startX = 127;
                    startY = 30;
                    weekX = 155;
                    weekY = 140;
                    dataX = 245;
                }
                if (startX != 0) {
                    NewRGBAPlatformDiffTxtUtils rgbaPlatformDiffTxtUtils = new NewRGBAPlatformDiffTxtUtils(mContext.getApplicationContext(), eaBleWatchInfo.getLcd_pixel_type());
                    rgbaPlatformDiffTxtUtils.showData(true);

                    Bitmap bitmap = rgbaPlatformDiffTxtUtils.produceDialThumbnail(backBitmap, screenWidth, screenHigh, raduis,
                            screenType, startX, startY, weekX, weekY, txtColor, dataX, prewWidth, prewHigh);
                    LogUtils.e(TAG, "开始坐标" + startX + "," + startY + ",日期时间坐标:" + weekX + "," + weekY + "," + dataX + " bitmap " + (bitmap == null));
                    rgbaPlatformDiffTxtUtils.destroy();

                    return bitmap;
                } else {

                    return null;
                }
            } else {
                Bitmap bBitMap = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                if (customWatchFace.pointerColorType == 0) {
                    if (screenWidth == 240) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_240, options);
                    } else if (screenWidth == 320) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 340) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 356 || screenWidth == 360) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 368) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 466) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_466, options);
                    } else if (screenWidth == 390) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 410) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    } else if (screenWidth == 412) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_black_point_356, options);
                    }


                } else if (customWatchFace.pointerColorType == 1) {
                    if (screenWidth == 240) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_240, options);

                    } else if (screenWidth == 320) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 340) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 356 || screenWidth == 360) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 368) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 466) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_466, options);
                    } else if (screenWidth == 390) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 410) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    } else if (screenWidth == 412) {
                        bBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thum_white_point_356, options);
                    }

                }
                if (bBitMap != null) {
                    NewRGBApointUtils rgbaPointUtils = new NewRGBApointUtils(mContext.getApplicationContext(), eaBleWatchInfo.getLcd_pixel_type());
                    Bitmap previewBitmap = rgbaPointUtils.produceDialThumbnail(backBitmap, prewWidth, prewHigh, raduis, screenType, bBitMap);
                    rgbaPointUtils.destroy();
                    return previewBitmap;
                }
                return null;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
        return null;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mContext = null;
        //将图片返回
        if (prewMapCallBack != null) {
            prewMapCallBack.thumbnail(bitmap);
        }

    }
}