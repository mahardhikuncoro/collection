package user.changepassword;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import base.network.ChangePassJson;
import base.screen.BaseDialogActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.login.LoginActivity;

public class ChangePasswordActivity extends BaseDialogActivity {

    @BindView(R.id.etOldpass) EditText etOldpass;
    @BindView(R.id.etNewpass) EditText etNewpass;
    @BindView(R.id.etNewpassconf) EditText etNewpassconf;
    @BindView(R.id.input_layout_password) TextInputLayout layoutInputPassword;
    @BindView(R.id.input_layout_password_confirm) TextInputLayout layoutInputPasswordconfirm;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ButterKnife.bind(this);
        initiateApiData();
        getLastLocation();
        setToolbar();
    }

    @OnClick(R.id.buttonSavePass)
    public void saveNewPassword(){
        if(passwordIdentic(etNewpass.getText().toString(),etNewpassconf.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setView(R.layout.progress_bar).setCancelable(false);
            }
            dialog = builder.create();
            dialog.show();
            if (!networkConnection.isNetworkConnected()) {
                dialog.dismiss();
                dialog(R.string.errorNoInternetConnection);
            } else {

                final ChangePassJson.ChangePassRequest request = new ChangePassJson().new ChangePassRequest();
                request.setUserid(userdata.select().getUserid());
                request.setPassword(encryptPassword(etOldpass.getText().toString()));
                request.setNewpwd(encryptPassword(etNewpass.getText().toString()));
                request.setNewpwd2(encryptPassword(etNewpassconf.getText().toString()));
                request.setLon(String.valueOf(getLongitude()));
                request.setLat(String.valueOf(getLatitude()));
                request.setAddr(String.valueOf(getAddress()));

                Call<ChangePassJson.ChangePassCallback> callChangePass = endPoint.changePassword(userdata.select().getAccesstoken(), request);
                callChangePass.enqueue(new Callback<ChangePassJson.ChangePassCallback>() {
                    @Override
                    public void onResponse(Call<ChangePassJson.ChangePassCallback> call, Response<ChangePassJson.ChangePassCallback> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equalsIgnoreCase("1")){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.successupdatepassword), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                userdata.deleteAll();
                                formData.deleteAll();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }else if(response.body().getStatus().equalsIgnoreCase("100")){
                                dialog.dismiss();
                                removeUserData(response.body().getMessage());
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePassJson.ChangePassCallback> call, Throwable t) {
                        dialog.dismiss();
                    }
                });


                Log.e("SAMA", "OKE ");
            }
        }else{
            Log.e("BEDA","OKE ");
            layoutInputPasswordconfirm.setError(getResources().getString(R.string.passnotmatch));
        }
    }

    public boolean passwordIdentic(String newPass, String confirmPass){
        return newPass.equalsIgnoreCase(confirmPass);
    }

    private void setToolbar() {

        String id = userdata.select().getGroupname().equalsIgnoreCase("")? "Group Name" : userdata.select().getGroupname();
        String fullname = userdata.select().getFullname();
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
    }

    @OnClick(R.id.btnback_toolbar)
    public void backButton(){
        onBackPressed();
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
