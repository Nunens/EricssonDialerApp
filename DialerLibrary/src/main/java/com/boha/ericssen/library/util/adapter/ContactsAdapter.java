package com.boha.ericssen.library.util.adapter;

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

import com.boha.ericssen.library.R;
import com.boha.ericssen.library.util.CallIntentService;
import com.boha.ericssen.library.util.CustomToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sowertech on 2014/11/12.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {
    private final LayoutInflater mInflater;

    private List<Contact> mList;
    private ArrayList<Contact> mLost;
    private Context context;
    //private ModelFilter filter;
    private Button normal, pay;
    private String log = "ContactsAdapter", number, LOG = "ContactsAdapter";
    TextView name;
    EndCallListener endCallListener;
    TelephonyManager telephonyManager;
    CustomToast toast;
    TextView tv, tvState;
    int callType;

    public ContactsAdapter(Context ctx, List<Contact> list) {
        super(ctx, R.layout.list_item, list);

        mList = list;
        this.context = ctx;
        mList = list;
        this.mLost = new ArrayList<>();
        mLost.addAll(mList);
        this.mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mList == null) {
            Log.i(log, "Contact List is empty");
            return;
        }
        //getFilter();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }
        final Contact item = mList.get(position);
        ImageView img = (ImageView) view.findViewById(R.id.imgView);
        normal = (Button) view.findViewById(R.id.callBtnNormal);
        pay = (Button) view.findViewById(R.id.callBtnPay4Me);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = mList.get(position).getPhone();
                performDial(mList.get(position).getPhone());
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = mList.get(position).getPhone();
                performDial("127" + mList.get(position).getPhone());
            }
        });
        name = (TextView) view.findViewById(R.id.txtName);
        Log.i("CONTACT", "CONTACT NAME: " + name.getText().toString());
        name.setText(mList.get(position).getName());
        animateView(view);
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
                openCustomToast(number);
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG, "@@@@@@@@@@@@ CALL_STATE_IDLE - setting isCallIdle true");
                if (toast != null) {
                    toast.getView().setVisibility(View.GONE);
                    toast.cancel();
                    toast = null;
                }
            }

        }
    }

    public void openCustomToast(String number) {
        Log.w(LOG, "################# creating new toast and showing it.....");
        if (toast != null) {
            toast.cancel();
            Log.w(LOG, "################# cancelling toast.....137");
        }
        Log.w(LOG, "################# new toast.....140");
        toast = new CustomToast(context);
        toast.setGravity(Gravity.TOP, 0, 0);
        //noinspection ResourceType
        toast.setDuration(20);

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

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mList.clear();
        if (charText.length() == 0) {
            mList.addAll(mLost);
        } else {
            for (Contact wp : mLost) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
