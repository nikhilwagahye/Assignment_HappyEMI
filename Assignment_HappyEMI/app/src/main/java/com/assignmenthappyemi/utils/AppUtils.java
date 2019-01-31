package com.assignmenthappyemi.utils;

import android.app.Activity;
import android.app.Dialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AppUtils {

    public static String epoch2DateString(long epochSeconds, String formatString) {
        //int offset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
        long longVal = epochSeconds * 1000L;// + offset;
        Date updatedate = new Date(longVal);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(updatedate);
    }


    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Integer resultCode = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            Dialog dialog = googleApiAvailability.getErrorDialog(activity, resultCode, 0);
            if (dialog != null) {
                dialog.show();
            }
            return false;
        }
        return true;
    }

}
