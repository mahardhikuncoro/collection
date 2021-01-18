package base.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import base.screen.BaseDialogActivity;
import base.sqlite.SQLiteConfig;
import base.utils.enm.ResponseCode;
import id.collection.mobile.application.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit extends BaseDialogActivity {

    protected SQLiteConfig config;
    protected NetworkConnection networkConnection;
    protected Retrofit retrofit;
    protected Retrofit retrofitLoc;

    protected void initiateApiData(){
        config = new SQLiteConfig(this);
        networkConnection = new NetworkConnection(this);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(config.getServerName())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        retrofitLoc = new Retrofit.Builder()
                .baseUrl(config.getServerLocName())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    /*private void callApi(){
        BaseEndPoint endPoint = retrofit.create(BaseEndPoint.class);
        UserSignInJson.Request request = new UserSignInJson(). new Request();
        request.setV(1);
        Call<UserSignInJson.Callback> call = endPoint.signin(request);
        callRetrofit(call, new GetCode<UserSignInJson.Callback>() {
            @Override
            public void responseOk(UserSignInJson.Callback response) {
            }
        });
    }*/

    /*protected <T extends BaseResponseCode> void callApi(Call<T> call, final Class<T> tClass, GetResponse){
        callRetrofit(call, tClass, new GetCode<T>() {
            @Override
            public void onSuccess(T response) {
                if (response.getRc() != null){
                    if(response.getRc().equals(0)){
                    } else if (response.getRc().equals(1)){

                    } else if (response.getRc().equals(3)){

                    } else if (response.getRc().equals(6)){

                    } else if (response.getRc().equals(9)){

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }*/

    protected <T extends BaseResponseCode> void callRetrofit(Call<T> call, final GetCode<T> getCode){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()){
                    if(response.body().getRc()!=null) {
                        if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.OK) {
                            T callback = (T) (response.body());
                            getCode.responseOk(callback);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.UNAUTHORIZED) {
                            dialog(R.string.registerUnauthorized);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.INACTIVE) {
                            dialog(R.string.errorInactive);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.EMPTY) {
                            dialog(R.string.errorEmpty);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.INCOMPATIBLE_VERSION) {
                            dialogPlaystore(R.string.errorIncompatibleVersion);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.RUNTIME_EXCEPTION) {
                            getCode.serviceUnavailable();
                            // Log.e("errorRunTime : ", response.body().getRm());
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.INVALID_APP) {
                            getCode.serviceUnavailable();
                            dialog(R.string.errorInvalidApp);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.APP_NULL) {
                            getCode.serviceUnavailable();
                            //  Log.e("errorAppNull : ", response.body().getRm());
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.INVALID_SUB_CODE) {
                            getCode.serviceUnavailable();
                            //  Log.e("errorInvalidSubCode : ", response.body().getRm());
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.INVALID_DEVICE) {
                            dialog(R.string.errorInvalidDevice);
                        } else if (ResponseCode.byOrdinal(response.body().getRc()) == ResponseCode.TASK_ASSIGN) {
                            taskAssign();
                        } else {
                            Log.e("ini error apa","gak jelas");
                            getCode.serviceUnavailable();
                            dialog(R.string.errorConnection);
                        }

                    }else {
                        getCode.serviceUnavailable();
                    }
                }  else if (response.code() >= 500 && response.code() <= 550){

                        getCode.serviceUnavailable();
                    }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e("ini error apa  failure",t.getLocalizedMessage());
                getCode.serviceUnavailable();
            }
        });
    }

    protected void taskAssign(){

    }

    public interface GetCode<T>{
        void responseOk(T response);

        void serviceUnavailable();
    }

}
