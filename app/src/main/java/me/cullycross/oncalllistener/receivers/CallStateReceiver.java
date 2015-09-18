package me.cullycross.oncalllistener.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class CallStateReceiver extends BroadcastReceiver {

    private TelephonyManager mTelephonyManager;
    private Context mContext;
    private Intent mIntent;

    public CallStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private final PhoneStateListener mPhoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            String callState = "UNKNOWN";
            String dialingNumber = mIntent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callState = "IDLE";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    callState = "Ringing (" + incomingNumber + ")";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    callState = "Dialing (" + dialingNumber + ")";
                    break;
            }
            Log.i(">>>Broadcast", "onCallStateChanged " + callState);
        }
    };
}
