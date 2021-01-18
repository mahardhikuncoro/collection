package ops.screen;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import base.network.PushNotificationJson;
import base.network.Slider;
import base.screen.BaseDialogActivity;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import base.utils.ParameterKey;
import base.utils.ServiceReceiver;
import base.utils.UserTypeService;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.BuildConfig;
import ops.screen.fragment.ProfileFragment;
import ops.screen.fragment.TaskListFragment;
import ops.screen.fragment.HomeFragment;
import id.collection.mobile.application.R;
import ops.screen.fragment.TaskListHistoryFragment;
import ops.screen.offline.DokumenOfflineList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;

public class MainActivityDashboard extends BaseDialogActivity implements ServiceReceiver.Receiver{

    private Boolean doubleBackToExitPressedOnce = false;
    public static int selectedItemId;
    private BottomNavigationView bottomNavigationView;
    private TelephonyManager telephonyManager;

    private SQLiteConfigKu config;
    private String sk;

    //reminder variable
    private String criteria1;
    private String criteria2;
    private String criteria3;
    private MaterialDialog dialog;
    public static int FLAG = 0;
    private Integer screenumber = 0;

    public Integer getScreenumber() {
        return screenumber;
    }

    public void setScreenumber(Integer screenumber) {
        this.screenumber = screenumber;
    }

    private ArrayList<Slider> sliderDataList;
    SliderSQL sliderdql;
    private ArrayList<String> sliderList;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.imgprofile)
    ImageView imgprofile;
    @BindView(R.id.txtViewName) TextView txtviewname;
    @BindView(R.id.txtSlamat) TextView txtSlamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view);
        ButterKnife.bind(this);
        toolbar.inflateMenu(R.menu.menu_actionbar);
        sliderdql = new SliderSQL(this);
        userdata = new Userdata(this);
        initToolbar();
        setToolbar();
        initiateApiData();
//        setToolbarMenu(toolbar);
//        callApiSlider();
        setNavigationbottom();
        getLastLocation();
        // load the store fragment by default
        selectedItemId = R.id.navigation_home;

        if(BuildConfig.FLAVOR.equalsIgnoreCase("bpdbali")){
            startService(invokeLoginService(getResources().getString(R.string.criteria_app_name), "081231810928019",UserImei));
        }

        Intent intent = getIntent();
        if (intent.hasExtra("FLAG_SUBMIT")) {
            bundle = new Bundle();
            bundle.putString("ASSIGNED_TYPE", intent.getStringExtra("ASSIGNED_TYPE") == null ? "" : intent.getStringExtra("ASSIGNED_TYPE"));
            bundle.putString("ASSIGNED_TC", intent.getStringExtra("ASSIGNED_TC") == null ? "" : intent.getStringExtra("ASSIGNED_TC"));
            selectedItemId = R.id.navigation_task;
            loadFragment(new HomeFragment());
         }else  if (intent.hasExtra(ParameterKey.SCREEN_HISTORY)) {
             selectedItemId = R.id.navigation_history;
             loadFragment(new TaskListHistoryFragment());
         }else {
            loadFragment(new TaskListFragment());
            // Do something else
        }
    }

    private void setNavigationbottom() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().add(Menu.NONE, R.id.navigation_home, Menu.NONE, "Beranda").setIcon(R.drawable.ic_home);
        bottomNavigationView.getMenu().add(Menu.NONE, R.id.navigation_history, Menu.NONE, "Riwayat").setIcon(R.drawable.ic_history);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            bottomNavigationView.getMenu().add(Menu.NONE, R.id.navigation_task, Menu.NONE, "Tugasku").setIcon(R.drawable.ic_task_navigation);
        else
            bottomNavigationView.getMenu().add(Menu.NONE, R.id.navigation_task, Menu.NONE, "Tugasku").setIcon(R.drawable.ic_task_navigation);
        bottomNavigationView.getMenu().add(Menu.NONE, R.id.navigation_profile, Menu.NONE, "Profil").setIcon(R.drawable.ic_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
        Intent intent = getIntent();
        if (intent.hasExtra("FLAG_SUBMIT")) {
            selectedItemId = R.id.navigation_task;
            bottomNavigationView.setSelectedItemId(R.id.navigation_task);
        }else if(intent.hasExtra(ParameterKey.SCREEN_TASK)){
            selectedItemId = R.id.navigation_task;
            bottomNavigationView.setSelectedItemId(R.id.navigation_task);
        }else if(intent.hasExtra(ParameterKey.SCREEN_HISTORY)){
            selectedItemId = R.id.navigation_history;
            bottomNavigationView.setSelectedItemId(R.id.navigation_history);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
            selectedItemId = bottomNavigationView.getSelectedItemId();
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    bundle = new Bundle();
                    bundle.putString("ASSIGNED_TYPE", "2");
                    bundle.putString("ASSIGNED_TC", "5.0");
                    fragment = new TaskListFragment();
                    if(selectedItemId != R.id.navigation_home) {
//                        startActivity(new Intent(getApplicationContext(),FormActivity.class));
                        loadFragment(fragment);
                    }
                    selectedItemId = R.id.navigation_home;
                    return true;
                case R.id.navigation_history:
                    bundle = new Bundle();
                    bundle.putString("ASSIGNED_TYPE", "2");
                    bundle.putString("ASSIGNED_TC", "5.0");
                    fragment = new TaskListHistoryFragment();
                    if(selectedItemId != R.id.navigation_history) {
//                        startActivity(new Intent(getApplicationContext(),FormActivity.class));
                        loadFragment(fragment);
                    }
                    selectedItemId = R.id.navigation_history;
                    return true;
                case R.id.navigation_task:
                    fragment = new HomeFragment();
                    if(selectedItemId != R.id.navigation_task)
                        loadFragment(fragment);
                    selectedItemId = R.id.navigation_task;
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    if(selectedItemId != R.id.navigation_profile)
                        loadFragment(fragment);
                    selectedItemId = R.id.navigation_profile;
                    return true;
            }
            return false;
        }
    };

    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {
//        int count = getFragmentManager().getBackStackEntryCount();
//        Log.e("FRAGMENT ","COUNT : " + getScreenumber());
//        Fragment fragment;
//        if(getScreenumber() == 1){
//            fragment = new HomeFragment();
//            loadFragment(fragment);
//            setScreenumber(0);
//        }
//        else if(getScreenumber() == 3){
//            fragment = new TaskListFragment();
//            loadFragment(fragment);
//            setScreenumber(0);
//        }else if(getScreenumber() == 0){
//            if(sk.equalsIgnoreCase("reminder")){
                Intent intent = new Intent(this, MainActivityDashboard.class);
//                intent.putExtra("sk", "ds");
                startActivity(intent);
                finish();
//            }else {
//                if (doubleBackToExitPressedOnce) {
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                doubleBackToExitPressedOnce = true;
//                Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        doubleBackToExitPressedOnce = false;
//                    }
//                }, 2000);
//            }
//        }
    }


    private void unRegisterTokenNotifification(){
        dialog = new MaterialDialog.Builder(this)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .show();

        criteria1 = getString(R.string.app_name);
        criteria2 = userdata.select().getUserid();
        criteria3 = UserImei;

        if (! networkConnection.isNetworkConnected()){
            dialog(R.string.errorNoInternetConnection);
        } else {
            final PushNotificationJson.registerTokenIdRequest request = new PushNotificationJson().new registerTokenIdRequest();
            final PushNotificationJson.registerTokenIdRequest.PushDatamodel requestModel = new PushNotificationJson().new registerTokenIdRequest().new PushDatamodel();

            requestModel.setCriteria1(criteria1);
            requestModel.setCriteria2(criteria2);
            requestModel.setCriteria3(criteria3);
            requestModel.setCriteria4("");
            requestModel.setCriteria5("");
            requestModel.setInstanceIdToken("");
            requestModel.setCreatedBy(criteria2);
            request.setModel(requestModel);

            Call<PushNotificationJson.registerTokenIdCallback> call = endPoint.unRegisterTokenId(request);
            call.enqueue(new Callback<PushNotificationJson.registerTokenIdCallback>() {
                @Override
                public void onResponse(Call<PushNotificationJson.registerTokenIdCallback> call, Response<PushNotificationJson.registerTokenIdCallback> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        FLAG = 0;
                        userdata.deleteAll();
                        Intent intent = new Intent(MainActivityDashboard.this, MainActivityDashboard.class);
                        intent.putExtra("sk", "ds");
                        startActivity(intent);
                        finish();
                        Log.e("Sukses Unregister", "Token ");
                    } else
                        dialog(R.string.errorBackend);
                }

                @Override
                public void onFailure(Call<PushNotificationJson.registerTokenIdCallback> call, Throwable t) {
                    dialog(R.string.errorBackend);
                    dialog.dismiss();
                    Log.e("Failed Register", "Token ");
                }
            });
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }

//    private void callApiSlider() {
//
//        sliderDataList = new ArrayList<Slider>();
//        sliderList = new ArrayList<String>();
//        sliderdql.deleteAll();
//        for (int i=0;i<=5 ;i++) {
//
//            Log.e("SLIDER SIZE INPUT " , " + "+ i);
//            Slider slider = new Slider();
//            slider.setId(Long.parseLong(String.valueOf(i)));
//            slider.setName("slider" +i);
//            slider.setImage( "https://www.trainingperbankan.com/wp-content/uploads/2018/05/logo-bank-kalbar.jpg");
//            slider.setLink("");
//            slider.setPublish("Y");
//            slider.setPackage_name("");
//
//            sliderDataList.add(slider);
//            sliderdql.save(Long.parseLong(String.valueOf(i)), getResources().getString(R.string.companyName),"https://www.trainingperbankan.com/wp-content/uploads/2018/05/logo-bank-kalbar.jpg",
//                    "", "Y", "");
//        }
//    }

    private void initToolbar(){

        imgprofile.setVisibility(View.VISIBLE);
        String img_url="";
        try {
            img_url = userdata.select().getPhotoprofile();
        }catch(Exception e){

        }
        String fullname = userdata.select().getFullname();
        if (!img_url.equalsIgnoreCase("")) {


            final String finalImg_url = img_url;
            Picasso.with(getApplicationContext())
                    .load(img_url)
                    .placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                    .error(R.drawable.ic_person_white_24dp).
                    resize(200, 200).
                    centerCrop()
                    .into(imgprofile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.with(getApplicationContext()).load(finalImg_url).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                                    .error(R.drawable.ic_person_white_24dp).
                                    resize(200, 200).
                                    centerCrop()
                                    .into(imgprofile);
                            Log.e("SUKSES LOAD","PROPIC ");
                        }

                        @Override
                        public void onError() {
                            Picasso.with(getApplicationContext()).load(R.drawable.ic_person_white_24dp).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                                    .error(R.drawable.ic_person_white_24dp).
                                    resize(200, 200).
                                    centerCrop()
                                    .into(imgprofile);

                            Log.e("ERROR LOAD","PROPIC ");
                        }
                    });
        }
        txtviewname.setText(fullname.toUpperCase());
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        //Set greeting
        String greeting = null;
        if(hour>=6 && hour<12){
            greeting = "Selamat Pagi";
        } else if(hour>= 12 && hour < 15){
            greeting = "Selamat Siang";
        } else if(hour >= 15 && hour < 18){
            greeting = "Selamat Sore";
        } else if(hour >= 18 && hour < 24){
            greeting = "Selamat Malam";
        } else{
            greeting = "Selamat Malam";
        }

        txtSlamat.setText(greeting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    private void setToolbar(){

        String id = userdata.select().getBranchid();
        String fullname = userdata.select().getFullname();
//        txtIdUser.setText(id);
//        txtFullname.setText(fullname);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(MainActivityDashboard.this).setTitle("").setView(addView)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                }).setNegativeButton("", null).show();

                        break;
                    case R.id.action_change_pass:
                        Intent intentchangepass = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intentchangepass);
                        finish();
                        break;
                    case R.id.action_logout:
                        dialogLogout(R.string.asklogout);
                        break;

                    case R.id.action_offline:
                        Intent intentFormOffline = new Intent(getApplicationContext(), DokumenOfflineList.class);
                        startActivity(intentFormOffline);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    private Intent invokeLoginService(String appName, String username, String imei) {
        Intent intent = new Intent(this, UserTypeService.class);
        ServiceReceiver receiver = new ServiceReceiver(new Handler());
        receiver.setReceiver(this);
        intent.putExtra("_HomeServiceLender", receiver);
        intent.putExtra("appName", appName);
        intent.putExtra("username", username);
        intent.putExtra("imei", imei);
        return intent;
    }

    @Override
    protected void onResume() {
        if(isCountIdleLimit())
            removeUserData("Session Expired !");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isCountIdleLimit())
            removeUserData("Session Expired !");
        return super.dispatchTouchEvent(ev);
    }
}
