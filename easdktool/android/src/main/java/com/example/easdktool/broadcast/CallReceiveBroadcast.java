package com.example.easdktool.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.apex.ax_bluetooth.model.EABleSocialContact;
import com.apex.ax_bluetooth.utils.LogUtils;

import com.example.easdktool.R;
import com.example.easdktool.service.MessageUtils;


public class CallReceiveBroadcast extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private static int lastetState = TelephonyManager.CALL_STATE_IDLE;


    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            telephony.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    if (TextUtils.isEmpty(incomingNumber)) {
                        return;
                    }
                    switch (state) {
                        case TelephonyManager.CALL_STATE_IDLE:
                            boolean isMiss = false;
                            if (lastetState == TelephonyManager.CALL_STATE_RINGING) {
                                isMiss = true;
                            }
                            lastetState = TelephonyManager.CALL_STATE_IDLE;
                            if (EABleManager.getInstance().getDeviceConnectState() != EABleConnectState.STATE_CONNECTED) {
                                return;
                            }
                            EABleSocialContact eaBleSocialContact = new EABleSocialContact();
                            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.incomingcall);
                            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.del);
                            EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact, new GeneralCallback() {
                                @Override
                                public void result(boolean b, int i) {
                                    LogUtils.i(TAG, context.getString(R.string.information_success));
                                }

                                @Override
                                public void mutualFail(int i) {
                                    LogUtils.i(TAG, context.getString(R.string.information_push_fail));
                                }


                            });
                            //未接来电提醒
                            if (isMiss) {
                                EABleSocialContact eaBleSocialContact1 = new MessageUtils().getCurrentMissIncomingCall(incomingNumber, System.currentTimeMillis(), context);
                                if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTED && eaBleSocialContact1 != null) {
                                    EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact1, new GeneralCallback() {
                                        @Override
                                        public void result(boolean b, int i) {

                                        }

                                        @Override
                                        public void mutualFail(int i) {

                                        }

                                    });

                                }

                            }
                            break;
                        case TelephonyManager.CALL_STATE_RINGING:
                            lastetState = TelephonyManager.CALL_STATE_RINGING;
                            if (EABleManager.getInstance().getDeviceConnectState() != EABleConnectState.STATE_CONNECTED) {
                                return;
                            }
                            EABleSocialContact eaBleSocialContact2 = new MessageUtils().getIncomingCallNotice(incomingNumber, System.currentTimeMillis(), context);
                            if (eaBleSocialContact2 == null) {
                                return;
                            }
                            EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact2, new GeneralCallback() {
                                @Override
                                public void result(boolean b, int i) {

                                }


                                @Override
                                public void mutualFail(int i) {

                                }
                            });
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            lastetState = TelephonyManager.CALL_STATE_OFFHOOK;
                            if (EABleManager.getInstance().getDeviceConnectState() != EABleConnectState.STATE_CONNECTED) {
                                return;
                            }
                            EABleSocialContact eaBleSocialContact3 = new EABleSocialContact();
                            eaBleSocialContact3.seteType(EABleSocialContact.SocialContactType.incomingcall);
                            eaBleSocialContact3.setE_ops(EABleSocialContact.SocialContactOps.del);
                            if (EABleManager.getInstance().getCurrentOtaState()) {
                                return;
                            }
                            EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact3, new GeneralCallback() {
                                @Override
                                public void result(boolean b, int i) {

                                }

                                @Override
                                public void mutualFail(int i) {

                                }

                            });


                            break;
                    }

                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
            LogUtils.i(TAG, e.getMessage());
        }
    }


}
