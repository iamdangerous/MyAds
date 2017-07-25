package com.redgingers.myads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.redgingers.myads.windowManager.WindowServiceProvider;

/**
 * Created by maninder on 25/7/17.
 */

public class AdView {
    private View rootView;
    private Context context;

    public AdView(Context context, int layoutId) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(layoutId, null, false);
        init();
    }

    private void init() {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
    }

    public void show() {
        rootView.setVisibility(View.VISIBLE);
        try {
            WindowServiceProvider.get(context).removeViewImmediate(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WindowServiceProvider.get(context).addView(rootView, WindowServiceProvider.getParams(context));
    }

    public void remove() {
        rootView.setVisibility(View.GONE);
        WindowServiceProvider.get(context).removeViewImmediate(rootView);
    }
}
