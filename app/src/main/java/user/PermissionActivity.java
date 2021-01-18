package user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import id.collection.mobile.application.R;
import user.login.LoginActivity;


public class PermissionActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    String[] permissionsRequired =  {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_bexi);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ) {
            dialogPermission();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    private void dialogPermission() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[2] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[3] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[4] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[5] != PackageManager.PERMISSION_GRANTED
                    ) {
                        if(!ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[0]) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[1]) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[2]) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[3]) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[4])||
                                !ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[5])
                        ){
                            dialog();
                        }else{
                            dialogPermission();
                        }
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                } else {
                    dialogPermission();
                }
            }
        }
    }

    protected void dialog() {
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

}