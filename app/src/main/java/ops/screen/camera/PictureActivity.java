package ops.screen.camera;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import base.endpoint.UploadImageJson;
import base.network.ResponseCallback;
import base.network.ResponseStatus;
import base.screen.BaseDialogActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureActivity extends BaseDialogActivity {

    private ImageView imageView;
    private static final String IMAGE_DIRECTORY = "/LosKalbar";
    private static File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initiateApiData();
        ButterKnife.bind(this);

        imageView = findViewById(R.id.img);

        imageView.setImageBitmap(MainActivityCamera.bitmap);
        saveImage(MainActivityCamera.bitmap);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }


    @OnClick(R.id.btnUpload)
    public void uploadImage() {
        Log.e("MAsuk ", "Upload Image : ----- >" + Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();

        if (!networkConnection.isNetworkConnected()) {
            dialog(R.string.errorNoInternetConnection);
            dialog.dismiss();
        } else {
            File file = new File(f.getAbsolutePath());
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);

            Log.e("Masuk ", "Upload Image dfdfd" + file.getAbsolutePath());

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            RequestBody regno = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("REGNO"));
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userdata.select().getUserid());
            RequestBody tc = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("TC"));
            RequestBody uploadtype = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("UPLOAD_TYPE"));
            RequestBody docid = RequestBody.create(MediaType.parse("text/plain"), "14");
            RequestBody doccode = RequestBody.create(MediaType.parse("text/plain"), "12");
            RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "0");
            RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "0");
            RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "mana hayo");

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("regno", regno);
            map.put("userid", userid);
            map.put("tc", tc);
            map.put("uploadtype", uploadtype);
            map.put("docid", docid);
            map.put("doccode", doccode);
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            map.put("address", address);

            Call<UploadImageJson.Callback> call = endPoint.uploadFile(userdata.select().getAccesstoken(), map, body);
            call.enqueue(new Callback<UploadImageJson.Callback>() {
                @Override
                public void onResponse(Call<UploadImageJson.Callback> call, Response<UploadImageJson.Callback> response) {
                    try {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                if (getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("profile")) {
                                    String link = response.body().getPHOTO_PROFILE();
                                    Log.e("Ahh ", "HALO LINK" + link);
                                    startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
                                    userdata.updatelinkProfile(link, userdata.select().getUserid());
                                } else {

                                    if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
                                        Intent intent = new Intent(PictureActivity.this, FormActivity.class);
                                        intent.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
                                        intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                                        intent.putExtra("TC", getIntent().getStringExtra("TC"));
                                        intent.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
                                        intent.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
                                        intent.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
                                        startActivity(intent);
                                    } else if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.FAILED)) {
                                        dialogMessage(response.body().getMessage());
                                    }
                                }
                            } else if (response.body().getStatus().equalsIgnoreCase("100")) {
                                removeUserData(response.body().getMessage());
                            } else {
                                dialogMessage(response.body().getMessage());
                            }
                        } else {
                            Log.e("Ahh ", "HALO LINKCOKKK ");
                            dialog(R.string.errorBackend);
                        }
                    } catch (Exception e) {
                        Log.e("Ahh ", "HALOas  LINKCOKKK ");
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }

                }

                @Override
                public void onFailure(Call<UploadImageJson.Callback> call, Throwable t) {
                    Log.e("Ahh ", "SAtt HALO LINKCOKKK ");
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
//                uploadFailed();

                }
            });
        }
    }
}
