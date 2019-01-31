package com.assignmenthappyemi.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignmenthappyemi.LocationAddress;
import com.assignmenthappyemi.R;
import com.assignmenthappyemi.adapter.TemperatureAdapter;
import com.assignmenthappyemi.model.Temperature;
import com.assignmenthappyemi.retrofit.ApiConstants;
import com.assignmenthappyemi.retrofit.RestClient;
import com.assignmenthappyemi.utils.AppUtils;
import com.assignmenthappyemi.utils.NetworkUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class TemperatureActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private static final String TAG = TemperatureActivity.class.getSimpleName();
    private static final int NO_OF_DAYS_DATA = 5; // as 0th index data of day will be same day, we need to show from index 1 to 4
    private RecyclerView recyclerView;
    private TextView textViewDegree;
    private TextView textViewCity;
    private LinearLayout linearLayoutAnim;
    private RelativeLayout relativeActualView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    LocationManager locationManager;
    LocationRequest locationRequest;
    LocationSettingsRequest.Builder locationSettingsRequest;
    PendingResult<LocationSettingsResult> pendingResult;
    public static final int REQUEST_LOCATION = 1001;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 1;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        initViews();

        // get current location
        getLocation();

    }

    private void initViews() {

        textViewDegree = findViewById(R.id.textView_degree);
        textViewCity = findViewById(R.id.textView_city);
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutAnim = findViewById(R.id.linearLayoutAnim);
        relativeActualView = findViewById(R.id.relative_actual_view);
        progressBar = findViewById(R.id.progress_bar);
        // recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    // API call
    private void getTemperatureData(String query) {

        Call<Temperature> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL_DEV).getTemperatureData(ApiConstants.key, query, NO_OF_DAYS_DATA); // as 0th index data of day will be same day, we need to show from index 1 - 4
        requestCallback.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(Call<Temperature> call, Response<Temperature> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    Temperature temperature = response.body();
                    if (temperature != null) {

                        setUIData(temperature);
                    } else {
                        Toast.makeText(TemperatureActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TemperatureActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                if (t != null) {
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });
    }


    // UI binding with API response
    private void setUIData(Temperature temperature) {

        // Need to show Current temperature on top section UI and average forecast temperature for next four days in list

        progressBar.setVisibility(View.GONE);
        relativeActualView.setVisibility(View.VISIBLE);
        Temperature.TemperatureLocation location = temperature.getLocation();
        Temperature.Current currentTemp = temperature.getCurrent();
        List<Temperature.ForecastDay> forecastDayList = temperature.getForecast().getListOfDays();
        forecastDayList.remove(0);
        if (location != null) {
            textViewCity.setText(location.getName() + "," + location.getCountry());
        }

        if (currentTemp != null) {
            textViewDegree.setText(currentTemp.getTempC() + getString(R.string.degree));
        }

        if (forecastDayList != null && forecastDayList.size() > 0) {
            recyclerView.setAdapter(new TemperatureAdapter(TemperatureActivity.this, forecastDayList));

            Animation hide = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_up_layout);
            linearLayoutAnim.startAnimation(hide);

        }
    }

    // Get Location -- START
    private void getLocation() {
        if (NetworkUtils.hasConnectivity(TemperatureActivity.this)) {
            if (AppUtils.isGooglePlayServicesAvailable(TemperatureActivity.this)) {
                setupProgressbar();
                showLocation();
            }
        } else {
            Toast.makeText(TemperatureActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupProgressbar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(TemperatureActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
        }
    }

    public void dialogDismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void showLocation() {
        if (!checkLocationPermission()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(TemperatureActivity.this, ACCESS_FINE_LOCATION)) {
                     /*   && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
                        && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {*/
                new TedPermission(TemperatureActivity.this)
                        .setPermissionListener(permissionLocationlistener)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("This app Requires Permission")
                        .setPermissions(ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                        //, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            } else {
                new TedPermission(TemperatureActivity.this)
                        .setPermissionListener(permissionLocationlistener)
                        .setDeniedCloseButtonText("Cancel")
                        .setDeniedMessage("If you reject permission,you can not use this service \n Please turn on permissions from Settings")
                        .setGotoSettingButtonText("Settings")
                        .setPermissions(ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                        //, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            }
        } else {
            setupGps();

        }
    }

    PermissionListener permissionLocationlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            showLocation();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };

    private boolean checkLocationPermission() {
        int result = ContextCompat.checkSelfPermission(TemperatureActivity.this, ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public void setupGps() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(TemperatureActivity.this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        googleApiClient.connect();

        mLocationSetting();

    }

    public void mLocationSetting() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1 * 1000);
        locationRequest.setFastestInterval(1 * 1000);
        locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        showEnableGpsDialog();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(TemperatureActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TemperatureActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e(TAG, "location :" + location.getLatitude() + " , " + location.getLongitude());
        dialogDismiss();
        googleApiClient.disconnect();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
                TemperatureActivity.this, new GeocoderHandler());

       /* if (NetworkUtils.hasConnectivity(TemperatureActivity.this)) {
            progressBar.setVisibility(View.VISIBLE);
            relativeActualView.setVisibility(View.GONE);

          *//*  String query = location.getLatitude() + "," + location.getLongitude();
            getTemperatureData(query);*//*

            String query = location.getLatitude() + "," + location.getLongitude();
            getTemperatureData(query);
        }*/

    }


    public void showEnableGpsDialog() {

        pendingResult = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequest.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {


            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                Log.e(TAG, "showEnableGpsDialog status code: " + status.getStatusCode());


                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        Log.e(TAG, "showEnableGpsDialog SUCCESS");
                        googleApiClient.connect();

                        dialogShow(getString(R.string.detecting_current_location));
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e(TAG, "showEnableGpsDialog RESOLUTION_REQUIRED");
                        try {
                            status.startResolutionForResult(TemperatureActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e(TAG, "showEnableGpsDialog SETTINGS_CHANGE_UNAVAILABLE");
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    public void dialogShow(String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        if (googleApiClient != null)
            googleApiClient.disconnect();
        super.onDestroy();
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    if (NetworkUtils.hasConnectivity(TemperatureActivity.this)) {
                        progressBar.setVisibility(View.VISIBLE);
                        relativeActualView.setVisibility(View.GONE);

                        String query = locationAddress;
                        getTemperatureData(query);
                    }
                    break;
                default:
                    locationAddress = null;
                    break;
            }

            //binding.textViewAddress.setText(locationAddress);
            Log.e("Address", locationAddress);
        }
    }

    // Get Location -- END

}
