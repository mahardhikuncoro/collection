package base.network;

import java.util.Map;

import base.endpoint.UploadImageJson;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface EndPoint {

    //UPDATE WITH LOGIN

    @POST("customer/reset_password.php")
    Call<ParameterJson.changePassResponse> changePassEndpoint(@Body ParameterJson.changePassRequest body);

    @POST("push/register.php")
    Call<PushNotificationJson.registerTokenIdCallback> registerTokenId(@Body PushNotificationJson.registerTokenIdRequest body);

    @POST("push/unregister.php")
    Call<PushNotificationJson.registerTokenIdCallback> unRegisterTokenId(@Body PushNotificationJson.registerTokenIdRequest body);

    @POST("userLogin")
    Call<LoginJson.loginAutenticationCallback> getAutentication(@Body LoginJson.LoginRequest body);

    @POST("getMenuAccess")
    Call<LoginJson.getmenuCallback> getMenuAccess(@Header("Authorization") String authKey, @Body LoginJson.LoginRequest body);

    @POST("getInbox")
    Call<TaskListJson.TasklistCallback> getInbox(@Header("Authorization") String authKey, @Body TaskListJson.TasklistRequest body);

    @POST("getDataMaster")
    Call<FormJson.CallbackForm> getDataMaster(@Header("Authorization") String authKey, @Body FormJson.RequestForm body);

    @POST("getDataMaster")
    Call<RetreiveCallbackJson> getCascadingNew(@Header("Authorization") String authKey, @Body RetreiveRequestJson body);

    @POST("getData")
    Call<RetreiveJson.RetreiveCallback> getDataRetreive(@Header("Authorization") String authKey, @Body RetreiveJson.RetreiveRequest body);

    @POST("getData")
    Call<RetreiveNewListJson.RetreiveCallback> getDataRetreiveNewList(@Header("Authorization") String authKey, @Body RetreiveNewListJson.RetreiveRequest body);

    @POST("getData")
    Call<RetreiveHistoryListJson.RetreiveCallback> getDataHistory(@Header("Authorization") String authKey, @Body RetreiveHistoryListJson.RetreiveRequest body);

    @POST("changePassword")
    Call<ChangePassJson.ChangePassCallback> changePassword(@Header("Authorization") String authKey, @Body ChangePassJson.ChangePassRequest body);

    @POST("setData")
    Call<SetDataJson.SetDataCallback> setData(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

    @POST("updateStatus")
    Call<SetDataJson.SetDataCallback> updateStatus(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

    @Multipart
    @POST("uploadFiles")
    Call<UploadImageJson.Callback> uploadFile(@Header("Authorization") String authKey,
                                              @PartMap() Map<String, RequestBody> partMap,
                                              @Part() MultipartBody.Part image);

    @POST("deleteData")
    Call<SetDataJson.SetDataCallback> deleteData(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

}
