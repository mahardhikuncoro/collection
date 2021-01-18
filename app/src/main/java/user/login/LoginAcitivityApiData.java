package user.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import com.google.gson.Gson;

import java.util.ArrayList;

import base.network.FormJson;
import base.network.LoginJson;
import base.network.Slider;
import base.screen.BaseDialogActivity;
import base.sqlite.SliderSQL;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import base.sqlite.TaskListDetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginAcitivityApiData extends BaseDialogActivity  {

    protected String noHp;
    private ArrayList<Slider> sliderDataList;
    SliderSQL sliderdql;
    private ArrayList<String> sliderList;

    protected void  callLoginAuthentication(String userId, final String password){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()){
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();

            request.setLoginType("loginauthentication");
            request.setUserid(userId);
            request.setPassword(password);
            request.setLon(String.valueOf(getLongitude()));
            request.setLat(String.valueOf(getLatitude()));
            request.setAddr(String.valueOf(getAddress()));

            Call<LoginJson.loginAutenticationCallback> call = endPoint.getAutentication(request);
            call.enqueue(new Callback<LoginJson.loginAutenticationCallback>() {
                @Override
                public void onResponse(Call<LoginJson.loginAutenticationCallback> call, Response<LoginJson.loginAutenticationCallback> response) {
                    try {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equalsIgnoreCase("1")) {
                                userdata.save(response.body().getUserid(),
                                        response.body().getFullname(),
                                        response.body().getPhotoprofile(),
                                        response.body().getGroupid(),
                                        response.body().getGroupname(),
                                        response.body().getBranchid(),
                                        response.body().getBranchname(),
                                        response.body().getAccesstoken(),
                                        response.body().getTokentype(),
                                        response.body().getExpiredin());
                                setFormMaster(response.body().getAccesstoken());

                                sliderDataList = new ArrayList<Slider>();
                                sliderList = new ArrayList<String>();
                                sliderdql.deleteAll();
                                for(LoginJson.SliderObject slider : response.body().getSlider()){
                                    saveApiSlider(slider);
                                }
                            }else if(response.body().getStatus().equalsIgnoreCase("0")){
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }else{
                            dialog.dismiss();
                            dialog(R.string.errorBackend);
                        }
                    }catch (Exception e){
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                }

                @Override
                public void onFailure(Call<LoginJson.loginAutenticationCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
    }


    private void saveApiSlider(LoginJson.SliderObject sliderObject) {


//        for (int i=0;i<=5 ;i++) {

//            Log.e("SLIDER SIZE INPUT " , " + "+ i);
            Slider slider = new Slider();
            slider.setId(Long.parseLong(sliderObject.getSliderPriority()));
            slider.setName(sliderObject.getSliderDesc());
            slider.setImage( sliderObject.getSliderUrl());
            slider.setLink("");
            slider.setPublish("Y");
            slider.setPackage_name("");

            sliderDataList.add(slider);
            sliderdql.save(slider);
//        }
    }


    protected void verifyUser(final String username, final ImageView imageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }
        else {

            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();

            request.setLoginType("verifyuser");
            request.setUserid(username.toString());
            request.setLon(String.valueOf(getLongitude()));
            request.setLat(String.valueOf(getLatitude()));
            request.setAddr(String.valueOf(getAddress()));
            Call<LoginJson.loginAutenticationCallback> callverify = endPoint.getAutentication(request);
            callverify.enqueue(new Callback<LoginJson.loginAutenticationCallback>() {
                @Override
                public void onResponse(Call<LoginJson.loginAutenticationCallback> call, Response<LoginJson.loginAutenticationCallback> response) {
                    try {
                        if(response.isSuccessful()){
                            dialog.dismiss();
                            if(response.body().getStatus().equalsIgnoreCase("1")){
                                String photoprofile = response.body().getPhotoprofile();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("photoprofile", photoprofile);
                                startActivity(intent);
                                finish();
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }
                        else{
                            dialog(R.string.errorBackend);
                            dialog.dismiss();
                        }
                    }catch (Exception e){
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                }
                @Override
                public void onFailure(Call<LoginJson.loginAutenticationCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }

    }
    protected void setFormMaster(String token) {
        if (!networkConnection.isNetworkConnected()) {
//            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
            requestForm.setType("form");
            requestForm.setUserid(userdata.select().getUserid());
//            String token = userdata.select().getAccesstoken();

            Call<FormJson.CallbackForm> callForm = endPoint.getDataMaster(token,requestForm);
            callForm.enqueue(new Callback<FormJson.CallbackForm>() {
                @Override
                public void onResponse(Call<FormJson.CallbackForm> call, final Response<FormJson.CallbackForm> response) {
                    try {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equalsIgnoreCase("1")) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        String successResponsedata = "", successResponse = "";
                                        Gson gsondatabody = new Gson();
                                        successResponse = gsondatabody.toJson(response.body());
                                        formData.save("1", "FORM_SURVEY_DATA", "SECTION", successResponse);
                                        for (FormJson.CallbackForm.Data model : response.body().getData()) {
                                            Gson gsondata = new Gson();
                                            TaskListDetailModel datamodel = new TaskListDetailModel();
                                            datamodel.setNamaNasabah(model.getSectionName());
                                            successResponsedata = gsondata.toJson(model);
//                                                for (FormJson.CallbackForm.Data.Field fieldmodel : model.getField()) {
//                                                formData.save("1", "FORM_SURVEY", model.getSectionName(), successResponsedata);
                                            formData.save("1", "FORM_SURVEY", model.getSectionId(), successResponsedata);
//                                                }
                                            Log.e("Data SECTION ", " : " + model.getSectionName());
                                            Log.e("FORMDATA ", " : " + successResponsedata);
                                        }
                                    }
                                }).start();
                            }else if(response.body().getStatus().equalsIgnoreCase("0")){
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }else if(response.body().getStatus().equalsIgnoreCase("100")){
                                dialog.dismiss();
                                removeUserData(response.body().getMessage());
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        } else {
                            dialogMessage(response.body().getMessage());
                        }
                    }catch (Exception e){
                    }
                    Intent intentdashboard = new Intent(getApplicationContext(), MainActivityDashboard.class);
                    startActivity(intentdashboard);
                    finish();
                }

                @Override
                public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                    dialog(R.string.errorBackend);
                    dialog.dismiss();
                }

            });

        }
    }

    protected void  callNewLogin(String userId, final String password){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()){
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();

            request.setLoginType("loginldap");
            request.setUserid(userId);
            request.setPassword(password);
            request.setDeviceId(getdeviceId());
            request.setLon(String.valueOf(getLongitude()));
            request.setLat(String.valueOf(getLatitude()));
            request.setAddr(String.valueOf(getAddress()));

            Call<LoginJson.loginAutenticationCallback> call = endPoint.getAutentication(request);
            call.enqueue(new Callback<LoginJson.loginAutenticationCallback>() {
                @Override
                public void onResponse(Call<LoginJson.loginAutenticationCallback> call, Response<LoginJson.loginAutenticationCallback> response) {
                    try {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equalsIgnoreCase("1")) {
                                userdata.save(response.body().getUserid(),
                                        response.body().getFullname(),
                                        response.body().getPhotoprofile(),
                                        response.body().getGroupid(),
                                        response.body().getGroupname(),
                                        response.body().getBranchid(),
                                        response.body().getBranchname(),
                                        response.body().getAccesstoken(),
                                        response.body().getTokentype(),
                                        response.body().getExpiredin());
                                setFormMaster(response.body().getAccesstoken());

                                sliderDataList = new ArrayList<Slider>();
                                sliderList = new ArrayList<String>();
                                sliderdql.deleteAll();
                                for(LoginJson.SliderObject slider : response.body().getSlider()){
                                    saveApiSlider(slider);
                                }
                            }else if(response.body().getStatus().equalsIgnoreCase("0")){
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }else{
                            dialog.dismiss();
                            dialog(R.string.errorBackend);
                        }
                    }catch (Exception e){
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                }

                @Override
                public void onFailure(Call<LoginJson.loginAutenticationCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
    }

    protected String getdeviceId(){

        String deviceId="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceId;
    }

}
