package com.boha.ericssen.library.util.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boha.ericssen.library.R;
import com.boha.ericssen.library.util.BohaCallLog;
import com.boha.ericssen.library.util.CallIntentService;
import com.boha.ericssen.library.util.CustomToast;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sipho on 2014/11/12.
 */
public class CallLogListAdapter extends ArrayAdapter<BohaCallLog> {
    private final int layoutRes;
    private final LayoutInflater inflater;
    private List<BohaCallLog> list;
    private Context context;
    private String log = "CallLogListAdapter", number, LOG = "CallLogListAdapter";
    ImageView image;
    TextView phone, type, time;
    View ditsebe;
    View view = null;
    Button normal, payForMe;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    EndCallListener endCallListener;
    TelephonyManager telephonyManager;
    CustomToast toast;
    TextView tv, tvState;
    int callType;

    public CallLogListAdapter(Context context, int resource, List<BohaCallLog> logs) {
        super(context, resource, logs);
        this.layoutRes = resource;
        this.list = logs;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (list == null) {
            Log.i(log, "Call Log List is empty");
            return;
        }
        Log.i(log, "Call Log List contains records");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = inflater.inflate(layoutRes, parent, false);
        } else {
            view = convertView;
        }
        phone = (TextView) view.findViewById(R.id.logNumber);
        type = (TextView) view.findViewById(R.id.logCallType);
        time = (TextView) view.findViewById(R.id.logTime);
        image = (ImageView) view.findViewById(R.id.logProPic);
        ditsebe = view.findViewById(R.id.ditsebe);
        normal = (Button) view.findViewById(R.id.logBtnNormal);
        payForMe = (Button) view.findViewById(R.id.logBtnPay4Me);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = list.get(position).getNumber();
                performDial(list.get(position).getNumber());
            }
        });
        payForMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = list.get(position).getNumber();
                performDial("127" + list.get(position).getNumber());
            }
        });
        phone.setText(list.get(position).getNumber());
        try {
            type.setText(list.get(position).getStringType());
        } catch (Exception e) {
        }
        time.setText(formatter.format(list.get(position).getDate()));
        //animate(view, 100);
        animateView(view);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getNumber() + ", " + formatter.format(list.get(position).getDate()), Toast.LENGTH_LONG).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view.findViewById(R.id.ditsebe).setVisibility(View.VISIBLE);
                Toast.makeText(context, list.get(position).getNumber() + ", " + formatter.format(list.get(position).getDate()) + ", " + list.get(position).getStringType(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void performDial(String numberString) {
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
            //openCustomToast(numberString);
        }
    }

    private class EndCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG, "$$$$$$$ CALL_STATE_RINGING  - doin nutin: " + incomingNumber);
            }
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG, "************** CALL_STATE_OFFHOOK, call type: " + type + ", Number: " + number);
                openCustomToast(number);
                //setState("Connected...");
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG, "@@@@@@@@@@@@ CALL_STATE_IDLE - setting isCallIdle true");
                //setState("Call Ended...");
                if(toast != null) {
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

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        a.setDuration(500);
        if (view == null)
            return;
        view.startAnimation(a);
    }

    void animate(View v, int i) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(v, View.SCALE_X, 0);
        an.setRepeatCount(1);
        an.setDuration(i);
        an.setRepeatMode(ValueAnimator.REVERSE);
        an.start();
    }

    class Holder {
        ImageView image;
        TextView phone, type, time;
        View ditsebe;
    }
}
