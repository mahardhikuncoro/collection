package user.login;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import base.sqlite.SliderSQL;
import base.utils.Security;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import ops.screen.offline.DokumenOfflineList;

public class LoginActivity extends LoginAcitivityApiData {

    @BindView(R.id.input_layout_user) TextInputLayout layoutInputUser;
    @BindView(R.id.input_layout_password) TextInputLayout layoutInputPassword;
    @BindView(R.id.wrong_pasword) TextInputLayout wrong_pasword;
    @BindView(R.id.inputUser) EditText inputUser;
    @BindView(R.id.inputPassword) EditText inputPassword;
    @BindView(R.id.txtLupasPassword) TextView txtLupaPass;
//    @BindView(R.id.txtRegister) TextView txtReg;
    @BindView(R.id.buttonLogin) Button btnLogin;
    @BindView(R.id.bc_login) RelativeLayout bc_login;
    @BindView(R.id.compName) TextView companyName;
    @BindView(R.id.compnameBelow) TextView compnameBelow;
    @BindView(R.id.imagePropic) ImageView imagePropic;
    @BindView(R.id.txtViewUsername) TextView txtviewusername;
    @BindView(R.id.imageOffline) ImageView _imageOffline;
    private Boolean doubleBackToExitPressedOnce = false;

    @BindString(R.string.buildName) String buildName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_signin);
        ButterKnife.bind(this);
        transparentStatusbar();
        initiateApiData();
        if(userdata.count()>0){
            Intent intent = new Intent(getApplicationContext(),MainActivityDashboard.class);
            startActivity(intent);
        }
        else{
            try {
                prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sliderdql = new SliderSQL(this);
        getLastLocation();
    }

    private void prepare() throws IOException {
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if(extras == null) {//screen masukkan number
//            layoutInputPassword.setVisibility(View.GONE);
//            inputPassword.setVisibility(View.GONE);
//            txtLupaPass.setVisibility(View.GONE);
//            loadImage("","");
//        }
//        else{
//            bc_login.setBackgroundResource(R.drawable.bc_login);
//            layoutInputUser.setVisibility(View.GONE);
//            inputUser.setVisibility(View.GONE);
//            layoutInputPassword.setVisibility(View.VISIBLE);
//            inputPassword.setVisibility(View.VISIBLE);
//            txtLupaPass.setVisibility(View.VISIBLE);
////            txtReg.setVisibility(View.VISIBLE);
////            txtReg.setVisibility(View.GONE);
//            companyName.setText(" ");
////            compnameBelow.setVisibility(View.VISIBLE);
//            btnLogin.setText("Login");
//            imagePropic.setVisibility(View.VISIBLE);
//            txtviewusername.setVisibility(View.VISIBLE);
//            loadImage(extras.getString("photoprofile"),extras.getString("username"));
//        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
                Intent intentBack = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentBack);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                Intent intentok = new Intent(Intent.ACTION_MAIN);
                intentok.addCategory(Intent.CATEGORY_HOME);
                intentok.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentok);
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
//        if (doubleBackToExitPressedOnce) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//                return;
//            }
//            doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 2000);
    }

    @OnClick(R.id.buttonLogin)
    public void login(){
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if(extras == null) {//Screen Input number
//            if (inputUser.getText().toString().equalsIgnoreCase(""))
//                layoutInputUser.setError(getResources().getString(R.string.errorEmptyUsername));
//            else
//                verifyUser(inputUser.getText().toString(),imagePropic);
//        }
//        else {
////            wrong_pasword.setEnabled(true);
////            wrong_pasword.setError(getResources().getString(R.string.errorPassword));
//            Log.e("HALLO ","USER ID " + extras.getString("username"));
//            callLoginAuthentication(extras.getString("username"), encryptPassword(inputPassword.getText().toString()));
//        }

        ///LOGIN LDAP
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("HALLO ","USER ID MASUK SUINI");
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE }, 0);
            return;
        }else{
            if (inputUser.getText().toString().equalsIgnoreCase("")) {
                Log.e("HALLO ","USER ID MASUK SINI LALLA");
                layoutInputUser.setError(getResources().getString(R.string.errorEmptyUsername));
            }else if(inputPassword.getText().toString().equalsIgnoreCase("")) {
                Log.e("HALLO ","USER ID MASUK SINI LOLLO");
                layoutInputPassword.setError(getResources().getText(R.string.errorEmptyPassword));
            }
           else {
                try {

//                    DateFormat dateFormat = new SimpleDateFormat("ddMMHHmm");
//                    Date date = new Date();
//                    String dateEncrypt = dateFormat.format(date)
//                            .replace("0","A")
//                            .replace("1","B")
//                            .replace("2","C")
//                            .replace("3","D")
//                            .replace("4","E")
//                            .replace("5","F")
//                            .replace("6","G")
//                            .replace("7","H")
//                            .replace("8","I")
//                            .replace("9","J");
//
//                    String dateEncrypt2 = dateFormat.format(date)
//                            .replace("0","a")
//                            .replace("1","b")
//                            .replace("2","c")
//                            .replace("3","d")
//                            .replace("4","e")
//                            .replace("5","f")
//                            .replace("6","g")
//                            .replace("7","h")
//                            .replace("8","i")
//                            .replace("9","j");
//                    Log.e("HALLO ","dateEncrypt " + dateEncrypt);
//                    Log.e("HALLO ","dateEncrypt2 " + dateEncrypt2);

//                    String ini = Security.encryptStrAndToBase64("ThisIsUrPassword", "ThisIsUrPassword",  inputPassword.getText().toString());
                    String ini = Security.Encrypt(inputPassword.getText().toString(), "ThisIsUrPassword" );
                    callNewLogin(inputUser.getText().toString(), ini);


                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//
//                    Log.e("HALLO ","USER ID MASUK SINI LLULU" + rjebdn(inputPassword.getText().toString()));
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                callNewLogin(inputUser.getText().toString(), inputPassword.getText().toString());

            }
        }

    }

    @OnClick(R.id.txtLupasPassword)
    public void forgetPassword(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("nohp", "");
        intent.putExtra("screen", "fp");
        startActivity(intent);
        String noUser = inputUser.getText().toString();
    }

//    @OnClick(R.id.txtRegister)
//    public void registerNewUser(){
////        registNewUser();
//    }

    private void validate(){
        if (inputUser.getText().toString().length() > 0 && inputPassword.getText().toString().length() > 0){
            try{
                DateFormat dateFormat = new SimpleDateFormat("ddMMHHmm");
                Date date = new Date();
                String dateEncrypt = dateFormat.format(date)
                        .replace("0","A")
                        .replace("1","B")
                        .replace("2","C")
                        .replace("3","D")
                        .replace("4","E")
                        .replace("5","F")
                        .replace("6","G")
                        .replace("7","H")
                        .replace("8","I")
                        .replace("9","J");

                String dateEncrypt2 = dateFormat.format(date)
                        .replace("0","a")
                        .replace("1","b")
                        .replace("2","c")
                        .replace("3","d")
                        .replace("4","e")
                        .replace("5","f")
                        .replace("6","g")
                        .replace("7","h")
                        .replace("8","i")
                        .replace("9","j");

                String ini = Security.encryptStrAndToBase64(dateEncrypt, dateEncrypt2,  inputPassword.getText().toString());
                Log.e("error", "callSignIn");
                //callSignIn(inputUser.getText().toString(), ini, buildName,dateEncrypt+"-"+dateEncrypt2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (inputUser.getText().toString().isEmpty() || inputUser.getText().toString().equals("")){
                layoutInputUser.setError(getString(R.string.errorEmptyUsername));
            }else if (inputPassword.getText().toString().isEmpty() || inputPassword.getText().toString().equals("")){
                layoutInputPassword.setError(getString(R.string.errorEmptyPassword));
            }else if (inputUser.getText().toString().isEmpty() || inputUser.getText().toString().equals("") &&
                    inputPassword.getText().toString().isEmpty() || inputPassword.getText().toString().equals("")){
                layoutInputUser.setError(getString(R.string.errorEmptyUsername));
                layoutInputPassword.setError(getString(R.string.errorEmptyPassword));
            }
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void loadImage(String photoprofile,String name) {
        String img_url = photoprofile;
        if (!img_url.equalsIgnoreCase("")) {
            Picasso.with(getApplicationContext()).load(img_url).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                    .error(R.drawable.ic_person_white_24dp)
                    .resize(200, 200)
                    .centerCrop()
//                    .rotate(90)
                    .into(imagePropic);
            txtviewusername.setText(name.toUpperCase());
        }else{
            Picasso.with(getApplicationContext())
                    .load(R.mipmap.ic_profile_login).placeholder(R.mipmap.ic_profile_login)// Place holder image from drawable folder
                    .error(R.mipmap.ic_profile_login)
                    .resize(200, 200)
                    .centerCrop()
//                    .rotate(90)
                    .into(imagePropic);
        }
    }

    @OnClick(R.id.imageOffline)public void goOffline(){
        startActivity(new Intent(LoginActivity.this,DokumenOfflineList.class));
    }



}
