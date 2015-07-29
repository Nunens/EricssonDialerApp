package com.boha.dialer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.boha.dialer.R;
import com.boha.ericssen.library.util.BohaCallLog;
import com.boha.ericssen.library.util.BohaUtil;
import com.boha.ericssen.library.util.adapter.CallLogListAdapter;

import java.util.List;


/**
 * Created by Sipho on 2014/11/12.
 */
public class CallLogFragment extends Fragment implements PageFragment {
    View view;
    ListView logs;
    TextView dilo;
    Context ctx;
    CallLogListAdapter opva;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call_log, container, false);
        setFields(view);
        return view;
    }

    void setFields(View v) {
        ctx = getActivity();
        logs = (ListView) v.findViewById(R.id.callLog);
        List<BohaCallLog> callLog = BohaUtil.getCallDetails(ctx);
        setChatList(callLog);
    }

    private void setChatList(List<BohaCallLog> log) {
        opva = new CallLogListAdapter(ctx, R.layout.call_log_list,
                log);

        logs.setAdapter(opva);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
