package com.shambhavpay.util;

import android.view.View;

public class Click {

    public static void preventTwoClick(final View view){
        try {
            view.setEnabled(false);
            view.postDelayed(new Runnable() {
                public void run() {
                    view.setEnabled(true);
                }
            }, 1000);
        }catch (Exception e){
        }
    }

}
