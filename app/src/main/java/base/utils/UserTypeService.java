package base.utils;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.PushNotificationJson;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import id.collection.mobile.application.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTypeService extends IntentService {

    private ResultReceiver resultReceiverLender;
    private SQLiteConfigKu config;

    private String pushMessagingId;
    private String criteria1;
    private String criteria2;
    private String criteria3;
    private EndPoint endPoint;
    private Userdata userdata;

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "id.pegadaian4d.mikro.mobile.application";
    Intent bi = new Intent(COUNTDOWN_BR);
    CountDownTimer cdt = null;

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";



    public UserTypeService() {
        super(UserTypeService.class.getName());
        userdata = new Userdata(this);

    }

    @Override
    public void onHandleIntent(Intent intent) {
        resultReceiverLender = intent.getParcelableExtra("_HomeServiceLender");
        criteria1 = intent.getExtras().getString("appName");
        criteria2 = intent.getExtras().getString("username");
        criteria3 = intent.getExtras().getString("imei");
        initiateApiData();
        getToken();
        AlarmManager alarmManager=(AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intenttimer = new Intent(getApplicationContext(), ProcessTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intenttimer, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),60000,
                pendingIntent);
    }

    private void getToken() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    FirebaseMessaging.getInstance().setAutoInitEnabled(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("all");
                    FirebaseInstanceId newToken = FirebaseInstanceId.getInstance(FirebaseApp.initializeApp(UserTypeService.this));
                    newToken.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful()) {
                                pushMessagingId = task.getResult().getToken();
                                Log.e("PUSHMESSAGEID " ," : " + pushMessagingId);
                                registerTokenNotofification(pushMessagingId);
                            } else
                                getToken();

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void registerTokenNotofification(String token){

        final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();

        request.setLoginType("registerDevice");
        request.setUserid(userdata.select().getUserid());
        request.setDeviceId(token);
        Call<LoginJson.loginAutenticationCallback> call = endPoint.getAutentication(request);
        call.enqueue(new Callback<LoginJson.loginAutenticationCallback>() {
            @Override
            public void onResponse(Call<LoginJson.loginAutenticationCallback> call, Response<LoginJson.loginAutenticationCallback> response) {
                if(response.isSuccessful()){
                    Log.e("Berhasil"," Registrasi Token: " );
                }
            }

            @Override
            public void onFailure(Call<LoginJson.loginAutenticationCallback> call, Throwable t) {

            }
        });
    }
    private void initiateApiData(){
        config = new SQLiteConfigKu(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
    }

//    private void callActivateDevice(final String email) {
//        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = telephonyManager.getDeviceId();
//        if (imei == null) imei = "";
//        String manufacturer = Build.MANUFACTURER;
//        if (manufacturer == null) manufacturer = "";
//        String brand = Build.BRAND;
//        if (brand == null) brand = "";
//        String product = Build.PRODUCT;
//        if (product == null) product = "";
//        String model = Build.MODEL;
//        if (model == null) model = "";
//        String os = Build.VERSION.SDK_INT + "";
//
//        /*try {
//         *//*InstanceID instanceIDnew = InstanceID.getInstance(this);
//            pushMessagingId = instanceIDnew.getToken(config.getGoogleProjectNumber(), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);*//*
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//
//        final ActivateJson.Request request = new ActivateJson(). new Request();
//        request.setV(config.getServerBVersion());
//        request.setUn(email);
//        request.setIm(imei+"_");
//        request.setMf(manufacturer);
//        request.setB(brand);
//        request.setP(product);
//        request.setM(model);
//        request.setOs(os);
//        request.setPmi(pushMessagingId);
//
//        Call<ActivateJson.Callback> call = b.activated(request);
//        call.enqueue(new Callback<ActivateJson.Callback>() {
//            @Override
//            public void onResponse(Call<ActivateJson.Callback> call, Response<ActivateJson.Callback> response) {
//                if (response.isSuccessful()) {
//                    if (ResponseStatus.OK.name().equalsIgnoreCase(response.body().getRs())) {
//                        resultActivateDevice(response.body().getUdi());
//                        Log.e("udi", response.body().getUdi()+"");
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ActivateJson.Callback> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void resultActivateDevice(Long deviceId) {
//        activeUserMetaData.setDeviceId(deviceId);
//        userData.save(activeUserMetaData);
//
//        Bundle bundle = new Bundle();
//        if (activeUserMetaData.getUserType().equalsIgnoreCase("L"))
//            resultReceiverLender.send(0, bundle);
//        else
//            resultReceiverBorrower.send(0, bundle);
//    }
}
