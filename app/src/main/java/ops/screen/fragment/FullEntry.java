package ops.screen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import base.network.FormJson;
import base.network.RetreiveJson;
import base.network.SetDataJson;
import base.screen.BaseDialogActivity;
import base.sqlite.ContentModel;
import base.sqlite.TaskListDetailModel;
import base.utils.DataLevel;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import ops.screen.offline.DokumenOfflineList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;

public class FullEntry extends BaseDialogActivity implements FullEntryCallback {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btnback_toolbar) ImageView btnback_toolbar;
    @BindView(R.id.recycleMenuEntry) RecyclerView recyclerView;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;
    @BindView(R.id.surveyFormSubmitButton) Button surveyFormSubmitButton;
    @BindView(R.id.surveyBackToPullButton) Button surveyBackToPullButton;
    @BindView(R.id.surveyReject) Button surveyReject;
    @BindView(R.id.titleList) TextView titleList;
    @BindView(R.id.layoutButton) LinearLayout layoutButton;

    private ArrayList<TaskListDetailModel> taskListList;
    private FullEntryAdapter fullEntryAdapter;
    private Dialog dialog;
    private static String regnumb,tc,typelist, reasonRejectId, reasonRejectDesc;
    private ArrayList<String> reasonRejectList;
    private ArrayList<ContentModel> reasonRejectDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullentry_fragment);
        ButterKnife.bind(this);
        initiateApiData();
        getLastLocation();
        toolbar.inflateMenu(R.menu.menu_actionbar);
        setToolbar();
        taskListList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        tc = getIntent().getStringExtra(ParameterKey.TC);
        typelist = getIntent().getStringExtra(ParameterKey.TYPELIST);
        if(typelist != null) {
            if (typelist.equalsIgnoreCase("unassigned")) {
                surveyFormSubmitButton.setText(R.string.buttonAssign);
            }
        }
        setRegno();


    }
    private void setRegno(){
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
            regnumb = getIntent().getExtras().getString(ParameterKey.REGNO);
            if (regnumb.equalsIgnoreCase("")) {
                final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
                request.setRegno("mobile");
                request.setUserid(userdata.select().getUserid());
                request.setTc(getIntent().getExtras().getString(ParameterKey.TC));
                request.setStatus(getIntent().getExtras().getString(ParameterKey.STATUS));
                request.setLon(String.valueOf(getLongitude()));
                request.setLat(String.valueOf(getLatitude()));
                request.setAddr(String.valueOf(getAddress()));


                Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
                call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                    @Override
                    public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                regnumb = response.body().getRegno();
                                retreiveData(regnumb);
                            } else if(response.body().getStatus().equalsIgnoreCase("100")){
                                dialog.dismiss();
                                removeUserData(response.body().getMessage());
                            }else {
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

                    }
                });
            } else {
                retreiveData(regnumb);
            }
        }
    }

    public void retreiveData(final String regno){
        final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();
        request.setRegno(regno);
        request.setUserid(userdata.select().getUserid());
        request.setTc(getIntent().getExtras().getString(ParameterKey.TC));
        request.setType(getIntent().getExtras().getString(ParameterKey.TYPE));
        request.setDataLevel(DataLevel.SECTION);

        Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(),request);
        callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
            @Override
            public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
               try{
                   if(response.isSuccessful()) {
                       if (response.body().getStatus().equalsIgnoreCase("1")) {
                           if (response.body().getAllowBackPool().equalsIgnoreCase("1")) {
                               surveyBackToPullButton.setVisibility(View.VISIBLE);
                           }
                           if (response.body().getAllowReject().equalsIgnoreCase("1")) {
                               surveyReject.setVisibility(View.VISIBLE);
                           }
                           if (response.body().getAllowSubmit().equalsIgnoreCase("0")) {
                               surveyFormSubmitButton.setVisibility(View.GONE);
                           }
                           if (response.body().getData().size() <= 0) {
                               dialog.dismiss();
                               dialog(R.string.dataempty);
                           } else if (response.body().getData().size() > 0) {
                               for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
//                            Log.e("getDataRetreive","KOKOKOK " + datamodel.getSectionName());
//                                   Log.e("SECTION TYPE", "KOKOKOK " + datamodel.getSectionType());
                                   TaskListDetailModel model = new TaskListDetailModel();
                                   model.setFormName(datamodel.getFormName());
                                   model.setSectionName(datamodel.getSectionName());
                                   model.setTableName(datamodel.getTableName());
                                   model.setSectionType(datamodel.getSectionType());
                                   model.setIcon(datamodel.getIcon());
                                   if (datamodel.getFormName().equalsIgnoreCase("INFAPP01"))
                                       layoutButton.setVisibility(View.GONE);
                                   taskListList.add(model);
                               }
                           }
                           setAdapter();
                       }else if(response.body().getStatus().equalsIgnoreCase("100")){
                           dialog.dismiss();
                           removeUserData(response.body().getMessage());
                       } else if(response.body().getStatus().equalsIgnoreCase("111")){
                           dialog.dismiss();
                           popUpMessage(response.body().getMessage());
                       }else{
                           dialog.dismiss();
                           dialogMessage(response.body().getMessage());
                       }
                   }else{
                       dialog.dismiss();
                       dialogMessage(response.body().getMessage());
                   }
               }catch (Exception e){
                   dialog(R.string.errorBackend);
               }
            }

            @Override
            public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {

            }
        });

    }


    /*protected void fillListMenu() {

        Log.e("COUNT "," FORM : " + formData.countByNameForm("FORM_SURVEY_REFERENCE"));
        if(formData.countByNameForm("FORM_SURVEY_REFERENCE") <= 0) {
            if (!networkConnection.isNetworkConnected()) {
                dialog.dismiss();
                dialog(R.string.errorNoInternetConnection);
            } else {
                final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
                requestForm.setType("reference");
                String token = userdata.select().getAccesstoken();
                Call<FormJson.CallbackForm> callForm = endPoint.getDataMaster(token, requestForm);
                callForm.enqueue(new Callback<FormJson.CallbackForm>() {
                    @Override
                    public void onResponse(Call<FormJson.CallbackForm> call, final Response<FormJson.CallbackForm> response) {
                        if (response.isSuccessful()) {
                            saveReferences(response);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

            }
        }else{
            dialog.dismiss();
        }
    }*/
    public void saveReferences(Response<FormJson.CallbackForm> response){
        String successResponsedata="";
        if(response.body().getStatus().equalsIgnoreCase("1")) {
            for (FormJson.CallbackForm.Data model : response.body().getData()) {
                try{
                    Gson gsondata = new Gson();
                    successResponsedata = gsondata.toJson(model);
                    Log.e("Data Reference TABLE ", " : " + model.getTableName());
                    Log.e("Data Reference ", " : " + successResponsedata);
                    formData.save("1", "FORM_SURVEY_REFERENCE", model.getTableName(), successResponsedata);
                }catch (Exception e){}
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }



    private void setToolbar(){

        String id = (getIntent().getExtras().getString(ParameterKey.REGNO) == null ? "" : getIntent().getExtras().getString(ParameterKey.REGNO));
        String fullname = (getIntent().getExtras().getString("NAMA_NASABAH") == null ? "": getIntent().getExtras().getString("NAMA_NASABAH"));
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
        txtFullname.setAllCaps(true);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(FullEntry.this).setTitle(R.string.companyName).setView(addView)
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
        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
    }

    private  void setAdapter(){
        if(taskListList.size() <= 0){
            surveyFormSubmitButton.setVisibility(View.GONE);
        }
        dialog.dismiss();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fullEntryAdapter = new FullEntryAdapter(taskListList, this, this);
        recyclerView.setAdapter(fullEntryAdapter);

    }

    @OnClick(R.id.surveyFormSubmitButton)
    public void dialogSubmit(){
        new MaterialDialog.Builder(this)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .content(typelist != null && typelist.equalsIgnoreCase("unassigned") ? R.string.askAssign :R.string.askSubmit)
                .title(R.string.app_name)
                .positiveText(typelist != null && typelist.equalsIgnoreCase("unassigned") ? R.string.buttonAssign : R.string.buttonSubmit)
                .negativeText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        submitData();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }


    public void submitData(){
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

                final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
                request.setRegno(regnumb);
                request.setUserid(userdata.select().getUserid());
                request.setTc(tc);
                Log.e("TYPELIST", " : " + typelist);
                Log.e("TC", " : " + tc);
                if(typelist != null && typelist.equalsIgnoreCase("unassigned"))
                {request.setStatus("1");}
                else {request.setStatus("1");}
                request.setLon(String.valueOf(getLongitude()));
                request.setLat(String.valueOf(getLatitude()));
                request.setAddr(String.valueOf(getAddress()));

            Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
                call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                    @Override
                    public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                popUpMessage(response.body().getMessage());
                            } else if(response.body().getStatus().equalsIgnoreCase("100")){
                                removeUserData(response.body().getMessage());
                            }else {
                                dialogMessage(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                });

            }
    }

    @OnClick(R.id.surveyReject)
    public void selectBranch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.reject_reason, null);
        final TextView _namaAplikasi = (TextView) dialogView.findViewById(R.id.namaUserLabel);
        final TextView _noregAplikasiLabel = (TextView) dialogView.findViewById(R.id.nikUserLabel);
        final TextView _etName = (TextInputEditText) dialogView.findViewById(R.id.etNama);
        final TextView _etNoreg = (TextInputEditText) dialogView.findViewById(R.id.etNik);
        final TextView _etDesc = (TextInputEditText) dialogView.findViewById(R.id.etDesc);
        final Spinner _branchSpinner = (Spinner) dialogView.findViewById(R.id.branchSpinner);
        final Button _branchSaveButton = (Button) dialogView.findViewById(R.id.branchSaveButton);
        final Button _branchCancelButton = (Button) dialogView.findViewById(R.id.branchCancelButton);
        _etName.setText(getIntent().getExtras().getString("NAMA_NASABAH") == null ? "" : getIntent().getExtras().getString("NAMA_NASABAH"));
        _etNoreg.setText(getIntent().getExtras().getString("REGNO") == null ? "" : getIntent().getExtras().getString("REGNO"));

        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
            requestForm.setType("rejectreference");
            requestForm.setUserid(userdata.select().getUserid());
            String token = userdata.select().getAccesstoken();
            Call<FormJson.CallbackForm> callForm = endPoint.getDataMaster(token, requestForm);
            callForm.enqueue(new Callback<FormJson.CallbackForm>() {
                @Override
                public void onResponse(Call<FormJson.CallbackForm> call, final Response<FormJson.CallbackForm> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getStatus().equalsIgnoreCase("1")) {
                            reasonRejectDataList = new ArrayList<ContentModel>();
                            // Create an array to populate the spinner
                            reasonRejectList = new ArrayList<String>();
                            for (FormJson.CallbackForm.Data data : response.body().getData()) {
                                for (FormJson.CallbackForm.Data.Content model : data.getContent()) {
                                    ContentModel contentmodel = new ContentModel();
                                    contentmodel.setDataId(model.getDataId());
                                    contentmodel.setDataDesc(model.getDataDesc());
                                    reasonRejectDataList.add(contentmodel);
                                    reasonRejectList.add(model.getDataDesc());
                                }
                            }
                            ArrayAdapter<String> branchListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, reasonRejectList);
                            branchListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            _branchSpinner.setAdapter(branchListAdapter);
                            _branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                        ((TextView) adapterView.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                    }
                                    reasonRejectId = reasonRejectDataList.get(position).getDataId();
                                    reasonRejectDesc = reasonRejectDataList.get(position).getDataDesc();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }

                            });
                            dialog.dismiss();
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            dialog.dismiss();
                            removeUserData(response.body().getMessage());
                        }else{
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    } else {
                        dialog.dismiss();
                        dialogMessage(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

        _branchSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!reasonRejectId.equalsIgnoreCase("0") && _etDesc.getText().length() > 0)
                    sendreject(_etDesc.getText().toString());
                else
                    dialogbacktoPool();
            }
        });

        _branchCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.cancel();
            }
        });
    }

    @OnClick(R.id.surveyBackToPullButton) public void reverseData(){
        new MaterialDialog.Builder(this)
                .content(R.string.askReverse)
                .title(R.string.app_name)
                .positiveText(R.string.reverse)
                .negativeText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        sendbacktopool();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();

    }
    public void sendbacktopool() {
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
            request.setTc(tc);
            request.setStatus("2");
            request.setLon(String.valueOf(getLongitude()));
            request.setLat(String.valueOf(getLatitude()));
            request.setAddr(String.valueOf(getAddress()));

            Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
            call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    if (response.isSuccessful()){
                        dialog.dismiss();
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            popUpMessage(response.body().getMessage());
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            removeUserData(response.body().getMessage());
                        }else {
                            dialogMessage(response.body().getMessage());
                        }
                    }else{
                        dialog.dismiss();
                        dialogMessage(response.body().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

                }
            });
        }

    }




    public void dialogbacktoPool() {
        new MaterialDialog.Builder(this)
                .content("Mohon pilih alasan Reject dan berikan deskripsi !")
                .positiveText(R.string.buttonTutup)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }

    public void sendreject(String reasonDesc) {
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
            request.setTc(tc);
            request.setStatus("3");
            request.setRejectreason(reasonRejectId);
            request.setRejectdesc(reasonDesc);
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
                            popUpMessage(response.body().getMessage());
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
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


    protected void popUpMessage(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(rString)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent i = new Intent(getApplicationContext(), MainActivityDashboard.class);
//                        i.putExtra("FLAG_SUBMIT","1");
                        i.putExtra(ParameterKey.SCREEN_TASK, ParameterKey.SCREEN_TASK == null? "" : ParameterKey.SCREEN_TASK);
                        i.putExtra(ParameterKey.SCREEN_HISTORY, ParameterKey.SCREEN_HISTORY == null? "" : ParameterKey.SCREEN_HISTORY);
                        i.putExtra(ParameterKey.SCREEN_HOME, ParameterKey.SCREEN_HOME == null? "" : ParameterKey.SCREEN_HOME);
                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .build();

        dialog.show();
    }

    @Override
    public void detailSection(Integer position) {

        if(!taskListList.get(position).getSectionType().equalsIgnoreCase("list")) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra(ParameterKey.SECTION_NAME, taskListList.get(position).getSectionName());
            intent.putExtra(ParameterKey.REGNO, regnumb);
            intent.putExtra(ParameterKey.TC, tc);
            intent.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intent.putExtra(ParameterKey.STATUS,getIntent().getStringExtra(ParameterKey.STATUS));
            intent.putExtra(ParameterKey.NEW_DATA, getIntent().getStringExtra(ParameterKey.NEW_DATA));
            intent.putExtra(ParameterKey.TABLE_NAME, taskListList.get(position).getTableName());
            intent.putExtra(ParameterKey.FORM_NAME, taskListList.get(position).getFormName());
            intent.putExtra(ParameterKey.SECTION_TYPE, taskListList.get(position).getSectionType());
            intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intent.putExtra(ParameterKey.TYPELIST, getIntent().getStringExtra(ParameterKey.TYPELIST));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            if(taskListList.get(position).getSectionName().equalsIgnoreCase("DOKUMEN OFFLINE")) {
                Intent intent = new Intent(this, DokumenOfflineList.class);
                intent.putExtra(ParameterKey.SECTION_NAME, taskListList.get(position).getSectionName());
                intent.putExtra(ParameterKey.REGNO, regnumb);
                intent.putExtra(ParameterKey.TC, tc);
                intent.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
                intent.putExtra(ParameterKey.STATUS,getIntent().getStringExtra(ParameterKey.STATUS));
                intent.putExtra(ParameterKey.TABLE_NAME, taskListList.get(position).getTableName());
                intent.putExtra(ParameterKey.FORM_NAME, taskListList.get(position).getFormName());
                intent.putExtra(ParameterKey.SECTION_TYPE, taskListList.get(position).getSectionType());
                intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, FullEntryList.class);
                intent.putExtra(ParameterKey.SECTION_NAME, taskListList.get(position).getSectionName());
                intent.putExtra(ParameterKey.REGNO, regnumb);
                intent.putExtra(ParameterKey.TC, tc);
                intent.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
                intent.putExtra(ParameterKey.STATUS,getIntent().getStringExtra(ParameterKey.STATUS));
                intent.putExtra(ParameterKey.TABLE_NAME, taskListList.get(position).getTableName());
                intent.putExtra(ParameterKey.FORM_NAME, taskListList.get(position).getFormName());
                intent.putExtra(ParameterKey.SECTION_TYPE, taskListList.get(position).getSectionType());
                intent.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
                startActivity(intent);
            }
        }

    }

    @Override
    public void detailDokumen(Integer position) {

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