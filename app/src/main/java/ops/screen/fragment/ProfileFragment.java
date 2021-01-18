package ops.screen.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.picasso.OkHttp3Downloader;
//import com.sandrios.CustomCamera.internal.CustomCamera;
//import com.sandrios.CustomCamera.internal.configuration.CameraConfiguration;
//import com.sandrios.CustomCamera.internal.manager.CameraOutputModel;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ResponseStatus;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfigKu;
import base.utils.ServiceReceiver;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.CameraActivity;
import ops.screen.MainActivityDashboard;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements ServiceReceiver.Receiver {

    @BindView(R.id.etDataNama)
    EditText etDataNama;

    @BindView(R.id.etDataNoHp)
    EditText etDataNoHp;

    @BindView(R.id.etDataKTP)
    EditText etDataKTP;

    @BindView(R.id.imgDataNama)
    ImageView imgDataNama;

    @BindView(R.id.textGantiPass)
    TextView textGantiPass;
    Switch switchNotification;

    private SQLiteConfigKu sqLiteConfigKu;
    private Userdata userdata;
    private String dataNama;
    private String dataCabang;
    private String dataGroup;
    private Picasso picasso;
    private ChangePasswordFragment changePasswordFragment;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    public MainActivityDashboard activity;

    SharedPreferences sharedpreferences;
    public static final String usernotif = "usernotif";
    private String UserImei="";

    private boolean fromCamera = false;
    static final int SELECT_PICTURE = 102;
    static final int RESELECT_PICTURE = 103;
    private String photoPath;
    private String categoryGroup;
    private String category;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initiateApiData();
        changePasswordFragment = new ChangePasswordFragment();
        getProfile();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAsset();
        setProfile();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private void loadAsset() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(getActivity());
        picassoBuilder.downloader(new OkHttp3Downloader(
                NetworkClient.getUnsafeOkHttpClient()
        ));
        picasso = picassoBuilder.build();
        loadIcon();
    }

    private void loadIcon() {
        String img_url = userdata.select().getPhotoprofile();
        if (!img_url.equalsIgnoreCase(""))
            Picasso.with(getActivity()).load(img_url).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                    .error(R.drawable.ic_person_white_24dp)
                    .resize(200, 200)
//                    .rotate(90)
                    .centerCrop()
                    .into(imgDataNama);
//        picasso.load(R.drawable.nama).fit().into(imgDataNama);
    }

    private void setProfile(){

        etDataNama.setText(dataNama);
        etDataNoHp.setText(dataCabang);
        etDataKTP.setText(dataGroup);

        etDataNama.setEnabled(false);
        etDataNoHp.setEnabled(false);
        etDataKTP.setEnabled(false);
        textGantiPass.setVisibility(View.GONE);

    }
    private void getProfile(){
        userdata = new Userdata(getActivity());
        dataNama =  userdata.select().getFullname();
        dataCabang = userdata.select().getBranchname();
        dataGroup = userdata.select().getGroupname();
    }


    @OnClick(R.id.textGantiPass)
    public void gantiPassword(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        fragmentTransaction.replace(R.id.frame_container, changePasswordFragment).addToBackStack(null).commit();
    }


    protected void initiateApiData(){
        networkConnection = new NetworkConnection(getActivity());
        sqLiteConfigKu = new SQLiteConfigKu(getActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
    }
    protected void dialog(int rString) {
        new MaterialDialog.Builder(getActivity())
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        String valuePref =  sharedpreferences.getString(usernotif, "");
                        if(valuePref.equalsIgnoreCase(ResponseStatus.REMINDER_FALSE.name()))
                            switchNotification.setChecked(false);
                        else
                            switchNotification.setChecked(true);
                    }
                })
                .cancelable(true)
                .show();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }

    @OnClick(R.id.imgDataNama)
    public void uploadimage(){
        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }else {
            Intent intentprofile = new Intent(getActivity().getApplicationContext(), CameraActivity.class);
            intentprofile.putExtra("REGNO","11");
            intentprofile.putExtra("TC","5.0");
            intentprofile.putExtra("UPLOAD_TYPE","profile");
            startActivity(intentprofile);
        }
//        intentChooser(null,"HALLO");
    }


    public void intentChooser(final Integer photoId, final String category) {
        final String[] items			= new String[] {"Dari Kamera", "Dari Galeri"};
        ArrayAdapter<String> adapter	= new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder		= new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Gambar");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    if (photoId == null) {
                        takePicture(category);
                    } else {
                        takePicture(category);
                    }
                }
                if (item == 1) {
                    if (photoId == null) {
                        openGallery(null, category);
                    } else {
                        openGallery(photoId, category);
                    }
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void takePicture(final String category) {
        fromCamera = true;
        this.category = category;

//        CustomCamera
//                .with(getActivity())
//                .setShowPicker(false)
//                .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO)
//                .enableImageCropping(false)
//                .launchCamera(new CustomCamera.CameraCallback() {
//                    @Override
//                    public void onComplete(CameraOutputModel model) {
//
//                        try {
//                            File photoFile = CameraHelper.createImageFile();
//                            BaseCamera.copyFile(model.getPath(), photoFile);
//                            photoPath = photoFile.getAbsolutePath();
//                            Log.e("PATH PHOTO "," ; " + photoPath.toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Intent intent = new Intent(getActivity(), SurveyCameraActivity.class);
////                        intent.putExtra(SurveyCameraActivity.EXTRA_PATH, photoPath);
////                        intent.putExtra(SurveyCameraActivity.EXTRA_PATH, photoPath);
////                        intent.putExtra(SurveyCameraActivity.CATEGORY, category);
//                        startActivity(intent);
//                    }
//                });
    }




    private void openGallery(Integer photoId, String category) {
//        this.category = category;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        if (photoId == null) {
            startActivityForResult(Intent.createChooser(intent, "Pilih Aksi"), SELECT_PICTURE);
        } else {
//            this.photoId = photoId;
            startActivityForResult(Intent.createChooser(intent, "Pilih Aksi"), RESELECT_PICTURE);
        }
    }




   /* private void uploadImageFile() {
        if (!networkConnection.isNetworkConnected()) {
            return;
        }
        final SurveyImage model = uploadImageList.get(uploadImagePosition);
        categoryGroupValue = model.getCategoryGroup();
        categoryGroupValue = (categoryGroupValue != null ? categoryGroupValue.replace("/", "_") : "na");
        categoryValue = model.getCategory();
        categoryValue = (categoryValue != null ? categoryValue.replace("/", "_") : "na");

        File file2 = new File(model.getPath());
        if (file2.exists()) {


//        if(base64!=null){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file2);
            MultipartBody.Part body = MultipartBody.Part.createFormData("multipartFile", getFilename(model.getPath()), reqFile);

            RequestBody username = RequestBody.create(MediaType.parse("text/plain"), uploadUser.getUsername());
            RequestBody categoryGroup = RequestBody.create(MediaType.parse("text/plain"), categoryGroupValue);
            RequestBody category = RequestBody.create(MediaType.parse("text/plain"), categoryValue);
            RequestBody variant = RequestBody.create(MediaType.parse("text/plain"), "MUFINS");
            RequestBody imageFileName = RequestBody.create(MediaType.parse("text/plain"), getFilename(model.getPath()));
            RequestBody imageFileSize = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(model.getSize()));
            RequestBody imageHeight = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(model.getHeight()));
            RequestBody imageWidth = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(model.getWidth()));
            RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), model.getLatitude() != null ? model.getLatitude() : "");
            RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), model.getLongitude() != null ? model.getLongitude() : "");
            RequestBody cfApplicationId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(uploadProfile.getCfApplicationId()));
            RequestBody cfProsesId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(uploadProfile.getCfProsesId()));

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("username", username);
            map.put("categoryGroup", categoryGroup);
            map.put("category", category);
            map.put("imageFileName", imageFileName);
            map.put("imageFileSize", imageFileSize);
            map.put("imageHeight", imageHeight);
            map.put("imageWidth", imageWidth);
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            map.put("cfApplicationId", cfApplicationId);
            map.put("cfProcessId", cfProsesId);
            map.put("variant", variant);
        }
    }*/

}
