package com.assignmenthappyemi;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {

                          /*  Log.e(TAG, " getAdminArea: " + addressList.get(0).getAdminArea()
                    + " getLocality: " + addressList.get(0).getLocality()
                    + " getSubAdminArea: " + addressList.get(0).getSubAdminArea()
                    + " getSubLocality: " + addressList.get(0).getSubLocality()
                    + " getFeatureName: " + addressList.get(0).getFeatureName()
                    + " getAddressLine 0: " + addressList.get(0).getAddressLine(0)
                                    + " getThroughfare: " + addressList.get(0).getThoroughfare()
                                    + " subLocality: " + addressList.get(0).getSubLocality()
                                            + " postal code: " + addressList.get(0).getPostalCode()
                            );*/
                        String throughfare = addressList.get(0).getThoroughfare();
                        String sublocality = addressList.get(0).getSubLocality();
                        String addressLine1 = addressList.get(0).getAddressLine(0);
                        if (addressList.get(0).getLocality() != null) {
                            result = addressList.get(0).getLocality();
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                    /*    result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;
*/
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                       /* result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";*/
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}