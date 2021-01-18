package ops.screen.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import base.network.ResponseCallback;
import base.network.RetreiveHistoryListJson;
import base.screen.BaseDialogActivity;
import base.sqlite.TasklistHistoryModel;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import ops.screen.TaskListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;


public class HistoryListDetail extends BaseDialogActivity {


    @BindView(R.id.recyclePosisi) RecyclerView _recyclePosisi;
    @BindView(R.id.recycleRiwayat) RecyclerView _recycleRiwayat;
    private ArrayList<TasklistHistoryModel> taskListListPosisi;
    private ArrayList<TasklistHistoryModel> taskListListRiwayat;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btnback_toolbar) ImageView btnback_toolbar;

    private Dialog dialog;
    protected Bundle bundle;
    private RiwayatAdapter _riwayatAdapter;
    private PosisiAdapter _posisiAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_detail_fragment);
        ButterKnife.bind(this);
        transparentStatusbar();
        initiateApiData();
        getLastLocation();
        toolbar.inflateMenu(R.menu.menu_actionbar);
        setToolbar();
        taskListListPosisi = new ArrayList<>();
        taskListListRiwayat = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _recycleRiwayat.setLayoutManager(linearLayoutManager);
        _recycleRiwayat.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        _recyclePosisi.setLayoutManager(linearLayoutManager2);
        _recyclePosisi.setHasFixedSize(true);

        if(getIntent().getStringExtra(ParameterKey.REGNO) != null){
            retreiveDataDetailHistory(getIntent().getStringExtra(ParameterKey.REGNO));
        }
    }




    public void retreiveDataDetailHistory(String regno){


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
            final RetreiveHistoryListJson.RetreiveRequest request = new RetreiveHistoryListJson().new RetreiveRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc("SLA");

            Log.e("TC ", " TOKEN " + userdata.select().getAccesstoken());
            Call<RetreiveHistoryListJson.RetreiveCallback> callBack = endPoint.getDataHistory(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<RetreiveHistoryListJson.RetreiveCallback>() {
                @Override
                public void onResponse(Call<RetreiveHistoryListJson.RetreiveCallback> call, Response<RetreiveHistoryListJson.RetreiveCallback> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
                            if(response.body().getCurrent() != null && response.body().getCurrent().size() > 0) {
                                for (TasklistHistoryModel datamodel : response.body().getCurrent()) {
                                    TasklistHistoryModel model = new TasklistHistoryModel();
                                    model.setAppnumber(datamodel.getAppnumber());
                                    model.setCurrtrcode(datamodel.getCurrtrcode());
                                    model.setTr_desc(datamodel.getTr_desc());
                                    model.setNexttrby(datamodel.getNexttrby());
                                    model.setProcessby(datamodel.getProcessby());
                                    model.setLasttrdate(datamodel.getLasttrdate());
                                    model.setLasttrcode(datamodel.getLasttrcode());
                                    model.setLasttrby(datamodel.getLasttrby());
                                    model.setSla(datamodel.getSla());
                                    model.setAging(datamodel.getAging());
                                    taskListListPosisi.add(model);
                                }
                                setAdapterPosisi();
                            }
                            if(response.body().getHistory() != null && response.body().getHistory().size() > 0) {
                                for (TasklistHistoryModel datamodel : response.body().getHistory()) {
                                    TasklistHistoryModel model = new TasklistHistoryModel();
                                    model.setAppnumber(datamodel.getAppnumber());
                                    model.setTh_seq(datamodel.getTh_seq());
                                    model.setTrack_code(datamodel.getTr_code());
                                    model.setTr_date(datamodel.getTr_date());
                                    model.setTr_by(datamodel.getTr_by());
                                    model.setTrack_tpl(datamodel.getTrack_tpl());
                                    model.setTr_desc(datamodel.getTr_desc());
                                    model.setJabatan(datamodel.getJabatan());
                                    model.setAging(datamodel.getAging());
                                    taskListListRiwayat.add(model);
                                }
                                setAdapterRiwayat();
                            }
                            dialog.dismiss();
                        }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.INVALID_LOGIN)){
                            dialog.dismiss();
                            removeUserData(response.body().getMessage());
                        }else if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                            Toast.makeText(getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                    .show();
                            userdata.deleteAll();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        }else{
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }else{
                        dialog.dismiss();
                        dialogMessage(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RetreiveHistoryListJson.RetreiveCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialogMessage("Error " + t);
                }
            });
        }

    }

    private  void setAdapterPosisi(){
        dialog.dismiss();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _recyclePosisi.setLayoutManager(linearLayoutManager);
        _recyclePosisi.setHasFixedSize(true);
        _posisiAdapter = new PosisiAdapter(taskListListPosisi,this);
        _recyclePosisi.setAdapter(_posisiAdapter);

    }

    private  void setAdapterRiwayat(){
        dialog.dismiss();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _recycleRiwayat.setLayoutManager(linearLayoutManager);
        _recycleRiwayat.setHasFixedSize(true);
        _riwayatAdapter = new RiwayatAdapter(taskListListRiwayat,this);
        _recycleRiwayat.setAdapter(_riwayatAdapter);
    }

    @OnClick(R.id.btntutupDetail)
    public void tutupDetail(){
        Intent i = new Intent(HistoryListDetail.this, MainActivityDashboard.class);
        i.putExtra(ParameterKey.SCREEN_HISTORY, true);
        startActivity(i);
    }

    private void setToolbar(){

        String id = (getIntent().getExtras().getString(ParameterKey.REGNO) == null ? "" : getIntent().getExtras().getString(ParameterKey.REGNO));
        String fullname = (getIntent().getExtras().getString("NAMA_NASABAH") == null ? "": getIntent().getExtras().getString("NAMA_NASABAH"));
//        txtIdUser.setText(id);
//        txtFullname.setText(fullname);
//        txtFullname.setAllCaps(true);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(HistoryListDetail.this).setTitle(R.string.companyName).setView(addView)
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
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @OnClick(R.id.btnback_toolbar)
    public void backButton(){
        Intent i = new Intent(HistoryListDetail.this, MainActivityDashboard.class);
        i.putExtra(ParameterKey.SCREEN_HISTORY, true);
        startActivity(i);
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
