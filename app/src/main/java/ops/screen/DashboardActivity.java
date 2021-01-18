package ops.screen;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.List;

import base.network.Slider;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.R;
public class DashboardActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener{

    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    private Integer layout;


    private DashboardFragment dashboardFragment;
    private SQLiteConfigKu sqLiteConfigKu;
    private SliderSQL slidersql;

    private String sk;
    private boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        ButterKnife.bind(this);

        if (
                (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED)
                ) {
            dialogPermission();
        } else {
            sqLiteConfigKu = new SQLiteConfigKu(this);
            slidersql = new SliderSQL(this);

            sk = getIntent().getExtras().getString("sk");
            Bundle arguments = new Bundle();
            arguments.putString("sk", sk);

            dashboardFragment = new DashboardFragment();
            dashboardFragment.setArguments(arguments);

            loadFragment();

            Integer slidesize = slidersql.count();
            if(slidesize > 0){
                sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));

                for(int i = 1 ; i <= slidesize; i++){
                    Slider temp = slidersql.select(i);

                    TextSliderView textSliderView = new TextSliderView(DashboardActivity.this);
                    textSliderView
                            .description(temp.getName())
                            .image(temp.getImage())
                            .error(R.drawable.defaultslide)
                            .empty(R.drawable.defaultslide)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(DashboardActivity.this);

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra",temp.getLink());

                    textSliderView.getBundle()
                            .putString("package",temp.getPackage_name());

                    sliderLayout.addSlider(textSliderView);

                }

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(6000);
                sliderLayout.addOnPageChangeListener(DashboardActivity.this);
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    private void loadFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, dashboardFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        //BIKIN WEB ACTION DISINI
        String link = slider.getBundle().getString("extra");
        String package_name = slider.getBundle().getString("package");

        if(package_name.length() > 1){
            if(isPackageExisted(package_name)){
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(package_name);
                startActivity(launchIntent);//null pointer check in case package name was not found
            }else{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
                startActivity(browserIntent);
            }
        }else if(!link.equalsIgnoreCase("") && link.length() > 10){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
            startActivity(browserIntent);
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            getFragmentManager().popBackStack();

        }

    }

    public boolean isPackageExisted(String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    protected void dialogPermission() {
        new MaterialDialog.Builder(this)
                .content("Please allow all permission on your app setting, thank you")
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .cancelable(false)
                .show();
    }


      /*  private void getSliderAdapter(){
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        layout = adapterView.getCount();


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        addBottomDotsForSlider(0);
        changeStatusBarColor();

        viewPager.setAdapter(adapterView);
        viewPager.addOnPageChangeListener(sliderPageChangeListener);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        int current = getItem(+1);
                        if (current == layout){
                            current = 0;
                        }
                        viewPager.setCurrentItem(current, true);
                    }
                });
            }
        },6000 , 5000);
    }

    private void addBottomDotsForSlider(int currentPage) {
        dots = new TextView[layout];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
*/


}

