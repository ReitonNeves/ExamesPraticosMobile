package br.gov.ma.detran.examespraticosmobile.util;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;

import br.gov.ma.detran.examespraticosmobile.R;

public class ColorStatusBarUtil {

    public static void setColorStatusBar(Activity activity){
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }
}
