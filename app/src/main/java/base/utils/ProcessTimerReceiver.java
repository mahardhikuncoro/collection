package base.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.SetDataJson;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProcessTimerReceiver extends BroadcastReceiver {

    protected FusedLocationProviderClient fusedLocationClient;
    int PERMISSION_ID = 44;
    protected String longitude = "0.00";
    protected String latitude = "0.00";
    protected String address ="Unknown street";
    private Context context;
    private SQLiteConfigKu config;
    private EndPoint endPoint;
    private Userdata userdata;

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra(UserTypeService.PARAM_OUT_MSG);
        this.context = context;
        initiateApiData();
        Log.e("TIMER", " TIMERRRR ONFINISH " + text);
        getLastLocation();
    }

    private void initiateApiData(){
        config = new SQLiteConfigKu(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
        userdata = new Userdata(context);
    }

    /*protected boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    protected void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    protected boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return false;
        }
        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }
*/


    @SuppressLint("MissingPermission")
    protected void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    protected LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude =String.valueOf( mLastLocation.getLatitude());
            setLatitude(latitude);
            setLongitude(longitude);
            setAddress(getCompleteAddressString(latitude,longitude));
        }
    };

    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
                fusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    try {
                                        setLatitude(String.valueOf(location.getLatitude()));
                                        setLongitude(String.valueOf(location.getLongitude()));
                                        Log.e("TIMER ","LONGITUDE " + getLongitude() );
                                        Log.e("TIMER ","LATITUDE " + getLatitude());
//                                    if(networkConnection.isNetworkConnected())
                                        setAddress(getCompleteAddressString(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude())));
//                                    else
//                                        dialog(R.string.errorNoInternetConnection);
                                    }catch(Exception e) {}
                                    Log.e("TIMER "," ADDRESS " + getAddress());
                                    updateLocation();
                                }
                            }
                        }
                );
//            } else {
//                dialogGps(R.string.errorGps);
//            }
//        } else {
//            requestPermissions();
//        }
    }

    private void updateLocation() {

        final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
        request.setUserid(userdata.select().getUserid());
        request.setTc("updatelocation");
        request.setLat(getLatitude());
        request.setLon(getLongitude());
        request.setAddr(getAddress());
        Call<SetDataJson.SetDataCallback> callBack = endPoint.setData(userdata.select().getAccesstoken(), request);
        callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
            @Override
            public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                if(response.isSuccessful())
                if(response.body().getStatus().equalsIgnoreCase("1")) {
                    Log.e("TIMER  " , " SUKSES UPDATE LOKASI " );
//                    Toast.makeText(context, "Update Lokasi!", Toast.LENGTH_LONG)
//                            .show();
                } else {
                    Log.e("TIMER  " , " GAGAL UPDATE LOKASI " );
//                    Toast.makeText(context, "Update lokasi gagal !", Toast.LENGTH_LONG)
//                            .show();
                }
            }
            @Override
            public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

            }
        });

    }

    protected String getCompleteAddressString(String LATITUDE, String LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.valueOf(LATITUDE), Double.valueOf(LONGITUDE), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("TIMER Address :", strReturnedAddress.toString());
            } else {
                strAdd = "Unknown address";
                Log.e("Address : ", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            strAdd = "Unknown address";
            Log.e("HAA ", "Canont get Address!" + e);
        }
        return strAdd;
    }


    public String getLongitude() {
        return longitude;
    }

    protected void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    protected void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

}
