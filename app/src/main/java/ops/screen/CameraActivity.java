package ops.screen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import base.endpoint.UploadImageJson;
import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.ResponseCallback;
import base.screen.BaseDialogActivity;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import base.utils.ParameterKey;
import base.widget.BaseCamera;
import butterknife.internal.Utils;
import id.collection.mobile.application.BuildConfig;
import id.collection.mobile.application.R;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import ops.screen.camera.PictureActivity;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import ops.screen.offline.DokumenOfflineList;
import ops.screen.offline.FormOfflineDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CameraActivity extends BaseDialogActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private Uri file, image;
    private static File f;
    private static final String IMAGE_DIRECTORY = "/Pegadaian";
    private Bitmap bitmap;
    private int bmpWidth, bmpHeight;
    private Integer iddoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        initiateApiData();
        getLastLocation();
        takePictureButton = (Button) findViewById(R.id.button_image);
        imageView = (ImageView) findViewById(R.id.imageview);
//        iddoc =  Integer.valueOf(getIntent().getIntExtra("IDPENGAJUAN",0));
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        takePicture();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }


    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        checkResolution(getOutputMediaFile());

        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

//        checkResolution(mediaStorageDir);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        f = new File(mediaStorageDir, timeStamp + ".jpg");

        return f;
    }

    private static void checkResolution(File fileImage, int rotation) {
//        Log.e("1",path);
//        Log.e("2",id.toString());
//        Log.e("3",latitude);
//        Log.e("4",longitude);

//        File imgFile = new File(path);
        if (fileImage.exists()) {
            OutputStream outStream = null;
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            bitmap = checkRotationFromCamera(bitmap, fileImage.getAbsolutePath()+fileImage.getName(), rotation);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth()), (int) ((float) bitmap.getHeight()), false);
            try {
                outStream = new FileOutputStream(fileImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//            int height = options.outHeight;
//            int width = options.outWidth;
//
//            if ((height*width) >= 1000000) {
//                Log.e("SIZE "," : " + (height*width));
//                resizePhotonew(fileImage);
//                //CameraHelper.resizePhoto(path);
//            }

//            savePhoto(path,id,latitude,longitude);
            bitmap.recycle();
            System.gc();
        }
    }

    public static void resizePhotonew(File fileImage) {
//        File imgFile = new File(path);

        if(fileImage.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap = BaseCamera.rotate(bitmap, 0);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(fileImage);
                fos.write(bitmapData);

                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap.recycle();
            System.gc();

        }
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private static File resaveBitmap(String path, String filename, int rotation) { //help for fix landscape photos
        String extStorageDirectory = path;
        OutputStream outStream = null;
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(path + filename);
            bitmap = checkRotationFromCamera(bitmap, path + filename, rotation);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth() * 0.3f), (int) ((float) bitmap.getHeight() * 0.3f), false);
//            bitmap = Utils.getCircleImage(bitmap);
            outStream = new FileOutputStream(path + filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static Bitmap checkRotationFromCamera(Bitmap bitmap, String pathToFile, int rotate) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("requestCode" ," "+ requestCode);
//        if (requestCode == 100) {
//            Log.e("Result" ," "+ resultCode);
            if (resultCode == RESULT_OK) {
                final int rotation = getImageOrientation(f.getAbsolutePath());
                checkResolution(f,rotation);

                Bitmap bitmap = decodeFile(f);
                imageView.setImageBitmap(bitmap);

            }else {
                if(!getIntent().getStringExtra("FORM_NAME").equalsIgnoreCase("")) {
                    if (!getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("profile")) {
                        Intent intent = new Intent(CameraActivity.this, FormActivity.class);
                        intent.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
                        intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                        intent.putExtra("TC", getIntent().getStringExtra("TC"));
                        intent.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
                        intent.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
                        intent.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
                        intent.putExtra("IMAGEID", getIntent().getStringExtra("IMAGEID"));
                        intent.putExtra("NAMA_NASABAH", getIntent().getStringExtra("NAMA_NASABAH"));
                        intent.putExtra("LISTITEMID", getIntent().getStringExtra("LISTITEMID"));
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
                    }
                }else{
                    Intent intent = new Intent(CameraActivity.this, FormOfflineDocument.class);
                    intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                    intent.putExtra("TC", getIntent().getStringExtra("TC"));
                    intent.putExtra("UPLOAD_TYPE", getIntent().getStringExtra("UPLOAD_TYPE"));
                    intent.putExtra("NEWDOCUMENT", getIntent().getBooleanExtra("NEWDOCUMENT",true));
                    intent.putExtra("IDDOCTYPE", getIntent().getStringExtra("IDDOCTYPE"));
                    intent.putExtra("ID_DOKUMEN",  getIntent().getStringExtra("ID_DOKUMEN"));
                    intent.putExtra("DOCTYPEDESC",  getIntent().getStringExtra("DOCTYPEDESC"));
                    intent.putExtra("DOCNAME",  getIntent().getStringExtra("DOCNAME"));
                    intent.putExtra("DOCID",  getIntent().getStringExtra("DOCID"));
                    startActivity(intent);
                }
            }
//        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        f = getOutputMediaFile();
        file = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }


    public void uploadImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("survey_offline")) {
            dialog.dismiss();
            saveDokumenOffline();
        } else {
            if (!networkConnection.isNetworkConnected()) {
                dialog.dismiss();
                dialog(R.string.errorNoInternetConnection);
            } else {

                try {
                    File image = new File(f.getAbsolutePath());
                    if (image.exists()) {
                        OutputStream outStream = null;
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth()), (int) ((float) bitmap.getHeight()), false);
                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

                        int height = options.outHeight;
                        int width = options.outWidth;
                        if ((height * width) >= 1000000) {
                            resizePhotonew(image);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                File image = new File(f.getAbsolutePath());
//                OutputStream outStream = null;
//                final BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
//                bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth()), (int) ((float) bitmap.getHeight()), false);
//                try {
//                    outStream = new FileOutputStream(image);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//
//                int height = options.outHeight;
//                int width = options.outWidth;
//                Log.e("SIZEAFTER ", " ababALD : " + (height * width));
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), image);

                MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), reqFile);
                RequestBody regno = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("REGNO") == null ? "M111202002858537" : getIntent().getStringExtra("REGNO"));
                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userdata.select().getUserid());
                RequestBody tc = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("TC"));
                RequestBody uploadtype = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("UPLOAD_TYPE"));
                RequestBody docid = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("DOC_ID") == null ? "12345" : getIntent().getStringExtra("DOC_ID").toUpperCase());
                RequestBody doccode = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("DOC_CODE") == null ? "" : getIntent().getStringExtra("DOC_CODE"));
                RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getLatitude()));
//                RequestBody sectionId = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra(ParameterKey.FORM_NAME));
                RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getLongitude()));
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), getAddress());

                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("regno", regno);
                map.put("userid", userid);
                map.put("tc", tc);
                map.put("uploadtype", uploadtype);
                if (!getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("profile")) {
                    map.put("docid", docid);
                    map.put("doccode", doccode);
                }
                map.put("lat", latitude);
                map.put("lon", longitude);
                map.put("addr", address);
//                if(BuildConfig.FLAVOR.equalsIgnoreCase("landing")) {
//                    map.put("sectionid", sectionId);
//                }
                Log.e("Ahh 1", "HALO LINK" + image.getName());
                Log.e("Ahh 2", "HALO LINK" + getIntent().getStringExtra("REGNO"));
                Log.e("Ahh 3", "HALO LINK" + userdata.select().getUserid());
                Log.e("Ahh 4", "HALO LINK" + getIntent().getStringExtra("TC"));
                Log.e("Ahh 5", "HALO LINK" + getIntent().getStringExtra("UPLOAD_TYPE"));
                Log.e("Ahh 6", "HALO LINK" + doccode);
                Log.e("Ahh 7", "HALO LINK" + address);

                Call<UploadImageJson.Callback> call = endPoint.uploadFile(userdata.select().getAccesstoken(), map, body);
                call.enqueue(new Callback<UploadImageJson.Callback>() {
                    @Override
                    public void onResponse(Call<UploadImageJson.Callback> call, Response<UploadImageJson.Callback> response) {
                        try {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                String link = response.body().getPHOTO_PROFILE();
                                if (getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("profile")) {
                                    Log.e("Ahh ", "HALO LINK" + link);
                                    if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK))
                                        popUpMessageProfile(response.body().getMessage(),link);
                                    else
                                        popUpMessageProfile(response.body().getMessage(),link);

                                } else {
                                    Log.e("HOO ", "FAILED YAAA " + response.body().getMessage().toString() + " " + response.body().getStatus());
                                    if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
                                        String docId= response.body().getDocid() == null? "":response.body().getDocid();
                                        popUpMessage(response.body().getMessage(), docId);
                                    }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.INVALID_LOGIN)){
                                        removeUserData(response.body().getMessage());
                                    }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.FAILED)){
                                        dialogMessage(response.body().getMessage());
                                    }else {
                                        dialogMessage(response.body().getMessage());
                                    }
                                }
                            }else{
                                dialog.dismiss();
                                Log.e("HOO ", "FAILED ");
                                dialogMessage(response.body().getMessage());
                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            Log.e("Ahh ", "HALO  LINKCOKKK ");
                            dialog(R.string.errorBackend);
                        }

                    }

                    @Override
                    public void onFailure(Call<UploadImageJson.Callback> call, Throwable t) {
                        dialogMessage("" +t);
//                uploadFailed();
                    }
                });
            }
        }
    }


    private void saveDokumenOffline(){
        File image = new File(f.getAbsolutePath());
        String idtype =  (getIntent().getExtras().getString("IDDOCTYPE"));
        String descType =  (getIntent().getExtras().getString("DOCTYPEDESC"));
        String docid =  (getIntent().getExtras().getString("DOCID"));
        String docname =  (getIntent().getExtras().getString("DOCNAME"));
        String url =  image.getAbsolutePath();
        if(!getIntent().getExtras().getBoolean("NEWDOCUMENT") == true){
            iddoc = getIntent().getExtras().getInt("IDIMAGE");
        }
        Log.e("ID IMAGE ",": " + iddoc);
        dokumenData.save(iddoc,idtype,descType,docname,docid,url);
        Log.e("JUMLAH DOKUMEN ",": " + dokumenData.count());
        Toast.makeText(this, "Dokumen berhasil disimpan", Toast.LENGTH_LONG).show();

        Intent intentTolist = new Intent(getApplicationContext(), DokumenOfflineList.class);
        intentTolist.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
        intentTolist.putExtra("TC", getIntent().getStringExtra("TC"));
        intentTolist.putExtra("UPLOAD_TYPE",getIntent().getStringExtra("UPLOAD_TYPE"));
        intentTolist.putExtra("IDIMAGE",getIntent().getStringExtra("IDIMAGE"));
        intentTolist.putExtra("IDDOCTYPE",getIntent().getStringExtra("IDDOCTYPE"));
        intentTolist.putExtra("DOCTYPEDESC",getIntent().getStringExtra("DOCTYPEDESC"));
        intentTolist.putExtra("DOCNAME",getIntent().getStringExtra("DOCNAME"));
        intentTolist.putExtra("DOCID","NEW");
        startActivity(intentTolist);
    }

    protected void popUpMessage(String rString, final String docId) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(rString)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(CameraActivity.this, FormActivity.class);
                        intent.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
                        intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                        intent.putExtra("TC", getIntent().getStringExtra("TC"));
                        intent.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
                        intent.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
                        intent.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
                        intent.putExtra("IMAGEID", docId);
                        intent.putExtra("NAMA_NASABAH", getIntent().getStringExtra("NAMA_NASABAH"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .build();

        dialog.show();
    }

    protected void popUpMessageProfile(String rString, final String link) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(rString)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        userdata.updatelinkProfile(link, userdata.select().getUserid());
                        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .build();

        dialog.show();
    }

    @Override
    protected void onResume() {
        if(isCountIdleLimit())
            removeUserData("Session Expired !");
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isCountIdleLimit())
            removeUserData("Session Expired !");
        return super.dispatchTouchEvent(ev);
    }
}
