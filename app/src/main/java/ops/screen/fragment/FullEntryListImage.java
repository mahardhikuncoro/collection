package ops.screen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import base.network.ResponseCallback;
import base.network.RetreiveJson;
import base.network.RetreiveNewListJson;
import base.network.SetDataJson;
import base.screen.BaseDialogActivity;
import base.sqlite.ContentModel;
import base.sqlite.TaskListDetailModel;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;

public class FullEntryListImage extends BaseDialogActivity implements FullEntryCallback{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.titleList) TextView titleList;
    @BindView(R.id.btnback_toolbar) ImageView btnback_toolbar;
    @BindView(R.id.recycleMenuEntry) RecyclerView recyclerView;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;
    @BindView(R.id.surveyBackToPullButton) Button surveyBackToPullButton;
    @BindView(R.id.surveyFormSubmitButton) Button surveyFormSubmitButton;

    private ArrayList<TaskListDetailModel> taskListList;
    private ArrayList<TaskListDetailModel.Content> contents;
    private FullEntryListImageAdapter fullEntryAdapter;
    private Dialog dialog;
    private String regnumb,tc,typelist, sectionname, allowNewItem;
    public static List<ContentModel> contentModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullentry_fragment);
        ButterKnife.bind(this);
        initiateApiData();
        toolbar.inflateMenu(R.menu.menu_actionbar);
        setToolbar();
        getLastLocation();
        taskListList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        tc = getIntent().getStringExtra(ParameterKey.TC);
        typelist = getIntent().getStringExtra(ParameterKey.TYPELIST);
        regnumb = getIntent().getExtras().getString(ParameterKey.REGNO);
        surveyBackToPullButton.setVisibility(View.GONE);
        surveyFormSubmitButton.setText("TAMBAH");
        if(!tc.equalsIgnoreCase("5.0"))
            surveyBackToPullButton.setVisibility(View.GONE);
        retreiveListImage(regnumb);
        if(getIntent().getStringExtra(ParameterKey.ALLOW_NEW_ITEM) != null){
            allowNewItem = getIntent().getStringExtra(ParameterKey.ALLOW_NEW_ITEM);
            if(allowNewItem.equalsIgnoreCase(ResponseCallback.FAILED)){
                surveyFormSubmitButton.setVisibility(View.GONE);
            }
        }

        Log.e("DOC CODE NYA DAPAT ", " HAHAHHAHA : " + getIntent().getStringExtra(ParameterKey.DOC_CODE));
    }

    private void setToolbar(){

        String id = (getIntent().getExtras().getString(ParameterKey.REGNO) == null ? "" : getIntent().getExtras().getString(ParameterKey.REGNO));
        String fullname = (getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH) == null ? "": getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH));
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
        txtFullname.setAllCaps(true);
        sectionname = getIntent().getExtras().getString(ParameterKey.SECTION_NAME);
        titleList.setText(sectionname);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(FullEntryListImage.this).setTitle(R.string.app_name).setView(addView)
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
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intentform= new Intent(getApplicationContext(), FullEntry.class);
        intentform.putExtra(ParameterKey.SECTION_NAME, getIntent().getStringExtra(ParameterKey.SECTION_NAME));
        intentform.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
        intentform.putExtra(ParameterKey.REGNO,getIntent().getStringExtra(ParameterKey.REGNO));
        intentform.putExtra(ParameterKey.STATUS,getIntent().getStringExtra(ParameterKey.STATUS));
        intentform.putExtra(ParameterKey.TC,getIntent().getStringExtra(ParameterKey.TC));
        intentform.putExtra(ParameterKey.NAMA_NASABAH,getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
        startActivity(intentform);
        finish();
    }

    private  void setAdapter(String regno){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fullEntryAdapter = new FullEntryListImageAdapter(this,this,contentModels);
        recyclerView.setAdapter(fullEntryAdapter);
    }

    @OnClick(R.id.surveyFormSubmitButton)
    public void submitData(){
                        Log.e("Berhasil Load"," ");
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);

        intent.putExtra(ParameterKey.SECTION_NAME, getIntent().getExtras().getString(ParameterKey.SECTION_NAME));
        intent.putExtra(ParameterKey.REGNO, getIntent().getExtras().getString(ParameterKey.REGNO));
        intent.putExtra(ParameterKey.TC, getIntent().getExtras().getString(ParameterKey.TC));
        intent.putExtra(ParameterKey.TYPE, getIntent().getExtras().getString(ParameterKey.TYPE));

        intent.putExtra(ParameterKey.TABLE_NAME, getIntent().getExtras().getString(ParameterKey.TABLE_NAME));
        intent.putExtra(ParameterKey.FORM_NAME, getIntent().getExtras().getString(ParameterKey.FORM_NAME));
        intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH));
        intent.putExtra(ParameterKey.LISTITEMID, "NEW");
        intent.putExtra(ParameterKey.DOC_CODE, getIntent().getStringExtra(ParameterKey.DOC_CODE));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); }

    @OnClick(R.id.surveyBackToPullButton)
    public void submitAllasigned() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regnumb);
            request.setUserid(userdata.select().getUserid());
            request.setTc("5.0");
            request.setStatus("2");
            request.setLon(String.valueOf(getLongitude()));
            request.setLat(String.valueOf(getLatitude()));
            request.setAddr(String.valueOf(getAddress()));

            Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
            call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()){
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogMessage(response.body().getMessage());
                            Intent i = new Intent(getApplicationContext(), MainActivityDashboard.class);
                            i.putExtra("FLAG_SUBMIT","1");
                            startActivity(i);
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            removeUserData(response.body().getMessage());
                        } else {
                            dialogMessage(response.body().getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

                }
            });
        }
    }


    private void retreiveListImage(String regno){

        Intent i = getIntent();
        setAdapter(regno);
//        Log.e("SIZE OF ","CONTENT : " + contentModels.size());
    }

    @Override
    public void detailSection(Integer position) {

    }

    @Override
    public void detailDokumen(Integer position) {

    }

    @Override
    public void detailListDokumen(Integer position) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(ParameterKey.SECTION_NAME, contentModels.get(position).getSectionName());
        intent.putExtra(ParameterKey.REGNO, regnumb);
        intent.putExtra(ParameterKey.IMAGEID, contentModels.get(position).getDataId());
        intent.putExtra(ParameterKey.TC, tc);
        intent.putExtra(ParameterKey.TYPE, getIntent().getExtras().getString(ParameterKey.TYPE));
        intent.putExtra(ParameterKey.TABLE_NAME, contentModels.get(position).getTableName());
        intent.putExtra(ParameterKey.FORM_NAME, contentModels.get(position).getFormName());
        intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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