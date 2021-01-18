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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import base.network.FormJson;
import base.network.ResponseCallback;
import base.network.RetreiveJson;
import base.network.RetreiveNewListJson;
import base.network.SetDataJson;
import base.screen.BaseDialogActivity;
import base.sqlite.ContentModel;
import base.sqlite.DataModel;
import base.sqlite.TaskListDetailModel;
import base.utils.DataLevel;
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
import user.login.LoginActivity;

public class FullEntryList extends BaseDialogActivity implements FullEntryCallback {

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
    private FullEntryListAdapter fullEntryAdapter;
    private Dialog dialog;
    private String regnumb,tc,typelist, sectionname, allowNewItem;

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
//        retreiveData(regnumb);
        retreiveDatanewList(regnumb);

    }
    public void retreiveDatanewList(final String regno){
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
            final RetreiveNewListJson.RetreiveRequest request = new RetreiveNewListJson().new RetreiveRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(getIntent().getExtras().getString(ParameterKey.TC));
            request.setType(getIntent().getExtras().getString(ParameterKey.TYPE));
            request.setFormname(getIntent().getExtras().getString(ParameterKey.FORM_NAME));
            request.setDataLevel(DataLevel.NEWLIST);

            Call<RetreiveNewListJson.RetreiveCallback> callBack = endPoint.getDataRetreiveNewList(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<RetreiveNewListJson.RetreiveCallback>() {
                @Override
                public void onResponse(Call<RetreiveNewListJson.RetreiveCallback> call, Response<RetreiveNewListJson.RetreiveCallback> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
                            allowNewItem = response.body().getAllowNewItem();
                            if (response.body().getAllowNewItem().equalsIgnoreCase(ResponseCallback.FAILED)) {
                                surveyFormSubmitButton.setVisibility(View.GONE);
                            }
                            if (response.body().getData().size() <= 0) {
                                dialogMessage(response.body().getMessage());
                            } else {
                                for (RetreiveNewListJson.RetreiveCallback.Data datamodel : response.body().getData()) {
                                    TaskListDetailModel model = new TaskListDetailModel();
                                    model.setDoc_code(datamodel.getDoc_code());
                                    model.setDoc_desc(datamodel.getDoc_desc());
                                    model.setDoc_group(datamodel.getDoc_group());
                                    model.setMultiple_file(datamodel.getMultiple_file());
                                    contents = new ArrayList<>();
                                    for (RetreiveNewListJson.RetreiveCallback.Content content : datamodel.getContent()) {
                                        TaskListDetailModel.Content contentmodel = new TaskListDetailModel().new Content();
                                        contentmodel.setKeyFieldName(content.getKeyFieldName());
                                        contentmodel.setDataId(content.getDataId());
                                        contentmodel.setDataDesc(content.getDataDesc());
                                        contentmodel.setIcon(content.getIcon());
                                        contents.add(contentmodel);
                                    }
                                    model.setContent(contents);
                                    model.setFormName(getIntent().getExtras().getString(ParameterKey.FORM_NAME));
                                    model.setSectionName(getIntent().getExtras().getString(ParameterKey.SECTION_NAME));
                                    model.setTableName(getIntent().getExtras().getString(ParameterKey.TABLE_NAME));
                                    taskListList.add(model);
                                }
                            }
                            dialog.dismiss();
                            setAdapter(regno);
                        }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.INVALID_LOGIN)){
                            dialog.dismiss();
                            removeUserData(response.body().getMessage());
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
                public void onFailure(Call<RetreiveNewListJson.RetreiveCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialogMessage("Error " + t);
                }
            });
        }

    }


    private void setToolbar(){

        String id = (getIntent().getExtras().getString(ParameterKey.REGNO) == null ? "" : getIntent().getExtras().getString(ParameterKey.REGNO));
        String fullname = (getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH) == null ? "": getIntent().getExtras().getString(ParameterKey.NAMA_NASABAH));
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
        txtFullname.setAllCaps(true);
        sectionname = getIntent().getStringExtra(ParameterKey.SECTION_NAME);
        titleList.setText(sectionname);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(FullEntryList.this).setTitle(R.string.companyName).setView(addView)
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
        fullEntryAdapter = new FullEntryListAdapter(this,taskListList,this);
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
                        } else if(response.body().getStatus().equalsIgnoreCase("100")){
                            removeUserData(response.body().getMessage());
                        }else {
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

    @Override
    public void detailSection(Integer position) {

    }

    @Override
    public void detailDokumen(Integer position) {

        if(taskListList != null && taskListList.get(position).getMultiple_file().equalsIgnoreCase("0")) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra(ParameterKey.SECTION_NAME, taskListList.get(position).getSectionName());
            intent.putExtra(ParameterKey.REGNO, regnumb);
            Log.e("IMAGEID"," DARI DATA ID :" + taskListList.get(position).getContent().get(0).getDataId());
            intent.putExtra(ParameterKey.IMAGEID, taskListList.get(position).getContent().get(0).getDataId());
            intent.putExtra(ParameterKey.TC, tc);
            intent.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intent.putExtra(ParameterKey.TABLE_NAME, taskListList.get(position).getTableName());
            intent.putExtra(ParameterKey.FORM_NAME, taskListList.get(position).getFormName());
            intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else{
            List<ContentModel> listContent = new ArrayList<ContentModel>();
            for(TaskListDetailModel.Content contentmodel : taskListList.get(position).getContent()){
                ContentModel model = new ContentModel();
                model.setKeyFieldName(contentmodel.getKeyFieldName());
                model.setDataId(contentmodel.getDataId());
                model.setDataDesc(contentmodel.getDataDesc());
                model.setIcon(contentmodel.getIcon());
                model.setSectionName(taskListList.get(position).getSectionName());
                model.setTableName(taskListList.get(position).getTableName());
                model.setFormName(taskListList.get(position).getFormName());
                listContent.add(model);
            }
            Log.e("SIZE OF ","CONTENT AWAL : " + listContent.size());
            FullEntryListImage fullEntryListImage = new FullEntryListImage();
            fullEntryListImage.contentModels = new ArrayList<>();
            fullEntryListImage.contentModels = listContent;

            Intent intent = new Intent(this, FullEntryListImage.class);
            intent.putExtra(ParameterKey.SECTION_NAME, taskListList.get(position).getSectionName());
            intent.putExtra(ParameterKey.REGNO, regnumb);
            intent.putExtra(ParameterKey.TC, tc);
            intent.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intent.putExtra(ParameterKey.TABLE_NAME, taskListList.get(position).getTableName());
            intent.putExtra(ParameterKey.FORM_NAME, taskListList.get(position).getFormName());
            intent.putExtra(ParameterKey.DOC_CODE, taskListList.get(position).getDoc_code());
            intent.putExtra(ParameterKey.ALLOW_NEW_ITEM,allowNewItem);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void detailListDokumen(Integer position) {

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