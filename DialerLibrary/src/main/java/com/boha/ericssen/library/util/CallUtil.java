package com.boha.ericssen.library.util;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.boha.ericssen.library.R;

import java.text.SimpleDateFormat;

/**
 * Created by Sipho on 2014/11/14.
 */
public class CallUtil {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    EndCallListener endCallListener;
    TelephonyManager telephonyManager;
    String number, displayNumber;
    Context context;
    String LOG = "CallUtil";
    CustomToast toast;
    TextView tv, tvState;
    int callType;

    public void performDial(String numberString, String displayNumber, Context ctx) {
        this.number = numberString;
        this.displayNumber = displayNumber;
        this.context = ctx;
        endCallListener = new EndCallListener();

        if (telephonyManager == null) {
            telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(endCallListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        if (!numberString.equals("")) {
            Intent i = new Intent(context, CallIntentService.class);
            i.putExtra(CallIntentService.EXTRA_NUMBER, numberString);
            context.startService(i);
        }
    }

    private class EndCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG, "$$$$$$$ CALL_STATE_RINGING  - doin nutin: " + incomingNumber);
            }
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG, "************** CALL_STATE_OFFHOOK, Number: " + number);
                openCustomToast(displayNumber);
                //setState("Connected...");
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG, "@@@@@@@@@@@@ CALL_STATE_IDLE - setting isCallIdle true");
                //setState("Call Ended...");
                if (toast != null) {
                    toast.getView().setVisibility(View.GONE);
                    toast.cancel();
                }
            }

        }
    }

    public void openCustomToast(String number) {
        Log.w("CallLogListAdapter", "################# creating new toast and showing it.....");
        if (toast != null) {
            toast.cancel();
        }
        toast = new CustomToast(context);
        toast.setGravity(Gravity.TOP, 0, 0);
        //noinspection ResourceType
        toast.setDuration(60);

        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inf.inflate(com.boha.ericssen.library.R.layout.toast_calling, null);
        tv = (TextView) v.findViewById(com.boha.ericssen.library.R.id.TC_txtNumber);

        tv.setText(number);
        toast.setView(v);
        if (number.substring(0, 3).equals("127")) {
            tv.setTextColor(context.getResources().getColor(R.color.orange));
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.green));
        }
        toast.getView().setVisibility(View.VISIBLE);
        toast.show();
    }
}
