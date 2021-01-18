package base.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseEndPoint {

    @POST("users/signinglobal")
    Call<UserSignInJson.Callback> signin(@Body UserSignInJson.Request request);

    @POST("devices/activatedglobal")
    Call<ActivatedJson.Callback> activated(@Body ActivatedJson.Request request);

    @POST("devices/inactivatedglobal")
    Call<InActivatedJson.Callback> inactivated(@Body InActivatedJson.Request request);

    @POST("location/batchinsert")
    Call<TrackerJson.Callback> tracker(@Body TrackerJson.Request request);

    @GET("configs/{key}")
    Call<ConfigTrackerJson.Callback> config(@Path("key") String key);

    @POST("user/passwordcheck")
    Call<PasswordCheckJson.Callback> passwordcheck(@Body PasswordCheckJson.PasswordCheckRequest request);


}
