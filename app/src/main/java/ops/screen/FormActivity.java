package ops.screen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import base.network.FormJson;
import base.network.ResponseCallback;
import base.network.RetreiveCallbackJson;
import base.network.RetreiveJson;
import base.network.RetreiveRequestJson;
import base.screen.BaseDialogActivity;
import base.sqlite.ContentModel;
import base.sqlite.DataModel;
import base.sqlite.FieldModel;
import base.utils.DataLevel;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.BuildConfig;
import id.collection.mobile.application.R;
import base.sqlite.TaskListDetailModel;
import ops.DinamicLayout;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;

public class FormActivity extends BaseDialogActivity {

    private DinamicLayout dynamicLayout;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.linearLayout)
    LinearLayout topLayout;
    @BindView(R.id.surveyFormSaveButton)
    Button surveyFormSaveButton;
    @BindView(R.id.surveyFormToolbar)
    Toolbar toolbar;
    @BindView(R.id.titletxtform)
    TextView titletxtform;
    @BindView(R.id.surveyFormDelete)
    Button surveyFormDelete;
    @BindView(R.id.layoutButton) LinearLayout layoutButton;

    ArrayList<TaskListDetailModel> menulist;
    public ArrayList<FieldModel> fieldArrayList;
    public ArrayList<FieldModel> fieldSingleArrayList;
    public ArrayList<DataModel> dataModelArrayList;
    public DataModel dataModel;
    private Dialog dialog;
    private String datalevel;
    private String typelist="";
    private String imageId="";
    public ArrayList<FieldModel> fieldArrayListNew;
    private boolean isNew = false;
    private String idNew ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menulist = new ArrayList<TaskListDetailModel>();
        fieldArrayList = new ArrayList<FieldModel>();
        fieldSingleArrayList = new ArrayList<FieldModel>();
        dataModelArrayList = new ArrayList<DataModel>();
        dataModel = new DataModel();
        imageId = getIntent().getExtras().getString("IMAGEID");
        initiateApiData();
        getLastLocation();
//        initViews();
        setToolbar();
        if(BuildConfig.FLAVOR.equalsIgnoreCase("bpdbali")) {
            setFormMaster(userdata.select().getAccesstoken());
        }else{
            callRetreiveData();
        }
    }
    private void initViews() {
        setSupportActionBar(toolbar);
        if(getIntent().getExtras() != null)
            getSupportActionBar().setTitle(getIntent().getStringExtra("SECTION_NAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Dialog showProgressDialog(Context context, String message){
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public void dismissprogress(Context context){
        dialog.dismiss();
    }

    public void retreiveData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();

        final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();
        request.setRegno(getIntent().getExtras().getString("REGNO"));
        request.setUserid(userdata.select().getUserid());
        request.setTc(getIntent().getExtras().getString("TC"));
        request.setType(getIntent().getExtras().getString("TYPE"));
        if(getIntent().getStringExtra("IMAGEID")!= null && getIntent().getStringExtra("IMAGEID")!="") {
            request.setDataLevel("listfield");
            request.setListItemId(getIntent().getExtras().getString("IMAGEID"));
            imageId = getIntent().getExtras().getString("IMAGEID");
            datalevel = "listfield";
        }
        else if(getIntent().getStringExtra("LISTITEMID") != null){
            request.setDataLevel("listfield");
            request.setListItemId("new");
            isNew = true;
            if(getIntent().getStringExtra("DOC_CODE") != null){
                request.setMultiple_doccode(getIntent().getStringExtra("DOC_CODE") );
            }
            datalevel = "listfield";
        }
        else {
            request.setListItemId(getIntent().getExtras().getString("IMAGEID"));
            request.setDataLevel("field");
            datalevel = "field";
        }
        request.setFormname(getIntent().getExtras().getString("FORM_NAME"));

        Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(),request);
        callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
            @Override
            public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
                try {
                    if(response.isSuccessful()) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            if (response.body().getAllowDeleteItem().equalsIgnoreCase("1")) {
                                surveyFormDelete.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getAllowSave().equalsIgnoreCase("0")) {
                                surveyFormSaveButton.setVisibility(View.GONE);
                            }
                            if (response.body().getData().size() <= 0) {
//                            dialogMessage(response.body().getMessage());
                            } else {
                                for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
                                    dataModel.setFormName(datamodel.getFormName());
                                    dataModel.setTableName(datamodel.getTableName());
                                    for (RetreiveJson.RetreiveCallback.Data.Field fieldmodel : datamodel.getField()) {
                                        FieldModel model = new FieldModel();
                                        model.setFieldName(fieldmodel.getFieldName());
                                        model.setFieldId(fieldmodel.getFieldId());
                                        model.setFieldValue(fieldmodel.getFieldValue());
                                        fieldArrayList.add(model);
                                        if(fieldmodel.getFieldValue().equalsIgnoreCase("new")){
                                            idNew = fieldmodel.getFieldName();
                                        }
                                    }
                                }
                            }
                            getFieldData(fieldArrayList);
                        } else if (response.body().getStatus().equalsIgnoreCase("100")) {
                            dialog.dismiss();
                            removeUserData(response.body().getMessage());
                        } else if (response.body().getStatus().equalsIgnoreCase("0")) {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        } else {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }else{
                        dialog.dismiss();
                        dialogMessage(response.body().getMessage());
                    }
                }catch (Exception e){
                    dialog.dismiss();
                    dialogMessage(e.toString());
                }
            }
            @Override
            public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {

            }
        });
    }

    private void callForm() {

        String section = formData.select(getIntent().getStringExtra("FORM_NAME"));
        Log.e("FORM DATA ", " SECTION : " + section);
        if(getIntent().getStringExtra("FORM_NAME") != null) {
            if (getIntent().getStringExtra("FORM_NAME").equalsIgnoreCase("SDE05")) {
                surveyFormSaveButton.setVisibility(View.GONE);
            }
        }
        if(getIntent().getExtras()!= null) {
//            Log.e("LAHHHHAHHHA ", " : " +  getIntent().getStringExtra("FORM_NAME") );
            dynamicLayout = new DinamicLayout(this,
                    fieldArrayListNew,
                    datalevel,
                    getIntent().getStringExtra("SECTION_NAME"),
                    getIntent().getStringExtra("REGNO"),
                    getIntent().getStringExtra("TC"),
                    getIntent().getStringExtra("TYPE"),
                    getIntent().getStringExtra("TABLE_NAME"),
                    getIntent().getStringExtra("FORM_NAME"),
                    getIntent().getStringExtra("NEW_DATA"),
                    imageId,
                    getIntent().getStringExtra("NAMA_NASABAH"),
                    section,
                    isNew,
                    getIntent().getStringExtra(ParameterKey.TYPELIST),
                    "",
                    getIntent().getStringExtra(ParameterKey.STATUS),
                    getIntent().getStringExtra(ParameterKey.SECTION_TYPE)
            );
        }else {
            dynamicLayout = new DinamicLayout(this,
                    fieldArrayListNew,
                    datalevel,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    isNew,
                    "",
                    "",
                    "",
                    ""
            );
        }

        dynamicLayout.setScrollView(scrollView);
        dynamicLayout.setTopLayout(topLayout);
        dynamicLayout.countitem = dynamicLayout.countSpinner(section);
        dynamicLayout.create(section);
        dialog.dismiss();

    }

    private void setToolbar(){
        titletxtform.setText(getIntent().getStringExtra("SECTION_NAME"));
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(getApplicationContext()).setTitle(R.string.app_name).setView(addView)
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
//                    case R.id.btnback_toolbar:
//                        Intent intentform = new Intent(getApplicationContext(), FormActivity.class);
//                        startActivity(intentform);
//                        finish();
//                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @OnClick(R.id.btnback_toolbar)public void backToform(){
        if(getIntent().getStringExtra(ParameterKey.IMAGEID) == null && getIntent().getStringExtra(ParameterKey.LISTITEMID) == null) {
            Intent intentform = new Intent(getApplicationContext(), FullEntry.class);
            intentform.putExtra(ParameterKey.SECTION_NAME, getIntent().getStringExtra(ParameterKey.SECTION_NAME));
            intentform.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intentform.putExtra(ParameterKey.REGNO, getIntent().getStringExtra(ParameterKey.REGNO));
            intentform.putExtra(ParameterKey.STATUS, getIntent().getStringExtra(ParameterKey.STATUS));
            intentform.putExtra(ParameterKey.TC, getIntent().getStringExtra(ParameterKey.TC));
            intentform.putExtra(ParameterKey.SECTION_TYPE, getIntent().getStringExtra(ParameterKey.SECTION_TYPE));
            intentform.putExtra(ParameterKey.TABLE_NAME, getIntent().getStringExtra(ParameterKey.TABLE_NAME));
            intentform.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intentform.putExtra(ParameterKey.TYPELIST, typelist);
            startActivity(intentform);
            finish();
        } else if(getIntent().getStringExtra(ParameterKey.LISTITEMID) != null){
            Intent intentform = new Intent(getApplicationContext(), FullEntryList.class);
            intentform.putExtra(ParameterKey.SECTION_NAME, getIntent().getStringExtra(ParameterKey.SECTION_NAME));
            intentform.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intentform.putExtra(ParameterKey.REGNO, getIntent().getStringExtra(ParameterKey.REGNO));
            intentform.putExtra(ParameterKey.STATUS, getIntent().getStringExtra(ParameterKey.STATUS));
            intentform.putExtra(ParameterKey.TC, getIntent().getStringExtra(ParameterKey.TC));
            intentform.putExtra(ParameterKey.FORM_NAME, getIntent().getStringExtra(ParameterKey.FORM_NAME));
            intentform.putExtra(ParameterKey.TABLE_NAME, getIntent().getStringExtra(ParameterKey.TABLE_NAME));
            intentform.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intentform.putExtra(ParameterKey.TYPELIST, typelist);
            startActivity(intentform);
            finish();
        }else{
            Intent intentform = new Intent(getApplicationContext(), FullEntryList.class);
            intentform.putExtra(ParameterKey.SECTION_NAME, getIntent().getStringExtra(ParameterKey.SECTION_NAME));
            intentform.putExtra(ParameterKey.TYPE, getIntent().getStringExtra(ParameterKey.TYPE));
            intentform.putExtra(ParameterKey.REGNO, getIntent().getStringExtra(ParameterKey.REGNO));
            intentform.putExtra(ParameterKey.STATUS, getIntent().getStringExtra(ParameterKey.STATUS));
            intentform.putExtra(ParameterKey.TC, getIntent().getStringExtra(ParameterKey.TC));
            intentform.putExtra(ParameterKey.FORM_NAME, getIntent().getStringExtra(ParameterKey.FORM_NAME));
            intentform.putExtra(ParameterKey.IMAGEID, getIntent().getStringExtra(ParameterKey.IMAGEID));
            intentform.putExtra(ParameterKey.TABLE_NAME, getIntent().getStringExtra(ParameterKey.TABLE_NAME));
            intentform.putExtra(ParameterKey.NAMA_NASABAH, getIntent().getStringExtra(ParameterKey.NAMA_NASABAH));
            intentform.putExtra(ParameterKey.TYPELIST, typelist);
            startActivity(intentform);
            finish();
        }
    }

    public void getFieldData(final List<FieldModel> fieldModel) {
        fieldArrayListNew = new ArrayList<>();
        List<ContentModel> content = new ArrayList<>();
        for(FieldModel fieldmodel : fieldModel){
            ContentModel contentmodel = new ContentModel();
            contentmodel.setFieldName(fieldmodel.getFieldName());
            contentmodel.setFieldValue(fieldmodel.getFieldValue().equalsIgnoreCase("")? "null":fieldmodel.getFieldValue());
            content.add(contentmodel);
        }

        final ArrayList<FieldModel> list = new ArrayList<>();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final RetreiveRequestJson requestForm = new RetreiveRequestJson();
            requestForm.setRegno(getIntent().getStringExtra(
                    ParameterKey.REGNO));
            requestForm.setUserid(userdata.select().getUserid());
            requestForm.setTc(getIntent().getStringExtra(ParameterKey.TC));
            requestForm.setFormname(getIntent().getStringExtra(ParameterKey.TYPE));
            requestForm.setSectionId(getIntent().getStringExtra(ParameterKey.FORM_NAME));
            requestForm.setTableName(getIntent().getStringExtra(ParameterKey.TABLE_NAME));
            requestForm.setFlag("");
            requestForm.setType("newcascading");

            if(getIntent().getStringExtra(ParameterKey.IMAGEID)!= null && getIntent().getStringExtra(ParameterKey.IMAGEID)!="") {
                requestForm.setDataLevel(DataLevel.
                        LISTFIELD);
                requestForm.setListItemId(getIntent().getExtras().getString(ParameterKey.IMAGEID));
                datalevel = DataLevel.LISTFIELD;
            }
            else if(getIntent().getStringExtra(ParameterKey.LISTITEMID) != null){
                requestForm.setDataLevel(DataLevel.LISTFIELD);
                requestForm.setListItemId("new");
                if(getIntent().getStringExtra(ParameterKey.DOC_CODE) != null) {
                    requestForm.setMultiple_doccode(getIntent().getStringExtra(ParameterKey.DOC_CODE));
                    isNew = true;
                }
                datalevel = DataLevel.LISTFIELD;
            }
            else {
                requestForm.setListItemId(getIntent().getExtras().getString(ParameterKey.IMAGEID));
                requestForm.setDataLevel(DataLevel.FIELD);
                datalevel = DataLevel.FIELD;
            }
//            requestForm.setDataLevel("field");
            requestForm.setContent(content);

            Call<RetreiveCallbackJson> callField = endPoint.getCascadingNew(userdata.select().getAccesstoken(),requestForm);
            callField.enqueue(new Callback<RetreiveCallbackJson>() {
                @Override
                public void onResponse(Call<RetreiveCallbackJson> call, Response<RetreiveCallbackJson> response) {
                    try{
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
//                                if(response.body().getData().size() > 0){
                                    for (DataModel data : response.body().getData()) {
                                        for (FieldModel field : data.getField()) {
                                            FieldModel fieldmodel = new FieldModel();
                                            fieldmodel.setFieldId(field.getFieldId());
                                            fieldmodel.setFieldName(field.getFieldName());
                                            fieldmodel.setFieldValue(String.valueOf(field.getFieldValue()));
                                            fieldmodel.setFieldFlagEnabled(field.getFieldFlagEnabled());
                                            fieldmodel.setFieldFlagHide(field.getFieldFlagHide());
                                            Log.e("MASUK ", "SINI32 " + isNew);
                                            if(isNew && field.getFieldName().equalsIgnoreCase(idNew)){
                                                imageId = fieldmodel.getFieldValue();
                                                Log.e("IMAGE ID ADALAH "," : " + imageId);
                                            }
                                            fieldmodel.setFieldValueList(field.getFieldValueList());
                                            fieldArrayListNew.add(fieldmodel);
                                            if(field.getFieldValueList() != null) {
                                                if (field.getFieldValueList().size() > 0) {
                                                    for (ContentModel fieldValueList : field.getFieldValueList()) {
                                                        ContentModel content = new ContentModel();
                                                        content.setDataId(fieldValueList.getDataId());
                                                        content.setDataDesc(fieldValueList.getDataDesc());
                                                        content.setSelected(fieldValueList.getSelected());
                                                    }
                                                }
                                            }
                                        }
//                                    }
                                }
                                callForm();
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
                    }catch (Exception e){
                        dialog.dismiss();
                        dialogMessage(e.toString());
                    }

                }

                @Override
                public void onFailure(Call<RetreiveCallbackJson> call, Throwable t) {

                }
            });
        }

    }

    protected void setFormMaster(String token) {

        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();

        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
            requestForm.setType("form");
            requestForm.setUserid(userdata.select().getUserid());
            requestForm.setTc(getIntent().getStringExtra(ParameterKey.TC));
            requestForm.setRegno(getIntent().getStringExtra(ParameterKey.REGNO));
//            String token = userdata.select().getAccesstoken();

            Call<FormJson.CallbackForm> callForm = endPoint.getDataMaster(token,requestForm);
            callForm.enqueue(new Callback<FormJson.CallbackForm>() {
                @Override
                public void onResponse(Call<FormJson.CallbackForm> call, final Response<FormJson.CallbackForm> response) {
                    try {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if(response.body().getStatus().equalsIgnoreCase("1")) {
//                                new Thread(new Runnable() {
//                                    public void run() {
                                        String successResponsedata = "", successResponse = "";
                                        Gson gsondatabody = new Gson();
                                        successResponse = gsondatabody.toJson(response.body());
                                        formData.save("1", "FORM_SURVEY_DATA", "SECTION", successResponse);
                                        for (FormJson.CallbackForm.Data model : response.body().getData()) {
                                            Gson gsondata = new Gson();
                                            TaskListDetailModel datamodel = new TaskListDetailModel();
                                            datamodel.setNamaNasabah(model.getSectionName());
                                            successResponsedata = gsondata.toJson(model);
//                                                for (FormJson.CallbackForm.Data.Field fieldmodel : model.getField()) {
//                                                formData.save("1", "FORM_SURVEY", model.getSectionName(), successResponsedata);
                                            formData.save("1", "FORM_SURVEY", model.getSectionId(), successResponsedata);
//                                                }
                                            Log.e("Data SECTION ", " : " + model.getSectionName());
                                            Log.e("FORMDATA ", " : " + successResponsedata);
                                        }
//                                    }
//                                }).start();
                            }else if(response.body().getStatus().equalsIgnoreCase("0")){
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
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
                    }catch (Exception e){
                    }
                    callRetreiveData();
                }
                @Override
                public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                    dialog(R.string.errorBackend);
                    dialog.dismiss();
                }

            });

        }
    }

    private void callRetreiveData(){
        if(getIntent().getExtras() != null) {
            if(getIntent().getExtras().getString("FORM_NAME").equalsIgnoreCase("INFAPP01"))
                layoutButton.setVisibility(View.GONE);

            if(getIntent().getExtras().getString(ParameterKey.TYPELIST) != null
                    && getIntent().getExtras().getString(ParameterKey.TYPELIST).equalsIgnoreCase("unassigned")) {
                surveyFormSaveButton.setVisibility(View.GONE);
                typelist = getIntent().getExtras().getString(ParameterKey.TYPELIST);
            }

            retreiveData();
        }else{
            if ((ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

                ActivityCompat.requestPermissions(FormActivity.this, new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            }else {
                callForm();
            }
        }
    }

    @OnClick(R.id.surveyFormSaveButton)
    public void saveData() {
        String section = formData.select(getIntent().getStringExtra(ParameterKey.FORM_NAME));
        dynamicLayout.getTextInput(section,
                getIntent().getExtras().getString(ParameterKey.REGNO).equalsIgnoreCase("") ? "1234567" : getIntent().getExtras().getString(ParameterKey.REGNO),
                getIntent().getExtras().getString(ParameterKey.TC),
                getIntent().getExtras().getString(ParameterKey.FORM_NAME),
                getIntent().getExtras().getString(ParameterKey.TABLE_NAME),
                true,
                getLongitude(),getLatitude(),getAddress());
    }

    @OnClick(R.id.surveyFormDelete)
    public void deleteData() {
        String section = formData.select(getIntent().getStringExtra(ParameterKey.FORM_NAME));
        dynamicLayout.getTextInput(section,
                getIntent().getExtras().getString(ParameterKey.REGNO).equalsIgnoreCase("") ? "1234567" : getIntent().getExtras().getString(ParameterKey.REGNO),
                getIntent().getExtras().getString(ParameterKey.TC),
                getIntent().getExtras().getString(ParameterKey.FORM_NAME),
                getIntent().getExtras().getString(ParameterKey.TABLE_NAME),
                false,
                getLongitude(),getLatitude(),getAddress());
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
