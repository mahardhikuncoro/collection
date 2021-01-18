package ops;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import base.NumberSeparatorTextWatcher;
import base.network.EndPoint;
import base.network.FormCascadingJson;
import base.network.FormJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.RetreiveCallbackJson;
import base.network.RetreiveRequestJson;
import base.network.SetDataJson;
import base.sqlite.ContentModel;
import base.sqlite.DataModel;
import base.sqlite.FieldModel;
import base.sqlite.FormData;
import base.sqlite.ParameterModel;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TaskListDetailModel;
import base.sqlite.Userdata;
import base.utils.ParameterKey;
import id.collection.mobile.application.R;
import ops.screen.CameraActivity;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import user.login.LoginActivity;

public class DinamicLayout extends LinearLayout {
    private static String staticArray;
    private Boolean isValid = false;
    private Activity context;
    private Integer index = 0;
    private JSONArray jsonArray, content, array, spinnerArray, arrayObject, multipleArray, resultObject;
    private JSONObject object, objectResult, jsonResult, /*eachObject,*/ data;
    private CardView cardView;
    private EditText editText;
    private TextView labelText, titleText, currencyText;
    private LinearLayout linearLayout, layer1, layer2, layer3, linearContent;
    private RelativeLayout relativeLayout;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button button;
    private Spinner spinner;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private ImageView imageView;
    private String codeSpinner, valueSpinner, strSpinner, codeRadio, valueRadio, strRadio, strArray = "", result = "";
    private DecimalFormat decimalFormat;
    private List<EditText> editTexts = new ArrayList<>();
    private List<TextInputEditText> textInputEditTexts = new ArrayList<>();
    private List<TextInputEditText> textInputEditTextDates = new ArrayList<>();
    private List<TextInputEditText> textInputEditTextCopies = new ArrayList<>();
    private List<RadioButton> radioButtons = new ArrayList<>();
    private List<RadioGroup> radioGroups = new ArrayList<>();
    private List<RelativeLayout> relativeLayouts = new ArrayList<>();
    private List<LinearLayout> layers1 = new ArrayList<>(), layers2 = new ArrayList<>(), linearLayouts = new ArrayList<>(), linearContents = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>(), titleTexts = new ArrayList<>();
    private List<CardView> cardViews = new ArrayList<>();
    private List<Spinner> spinners = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private List<ImageView> imageviews = new ArrayList<>();
    private List<ImageView> imageviewscopy = new ArrayList<>();
    private ScrollView scrollView;
    private LinearLayout topLayout;
    private String formName = "";
    private FormData formData;
    public ArrayList<FieldModel> fieldArrayList;
    public ArrayList<FieldModel> fieldArrayListtemp;
    public ArrayList<ContentModel> finalListContent;
    public boolean isfirstload = true;
    public boolean finished = false;
    public boolean finishedloadSpinner = false;
    public boolean finishedloadSpinnerOnForm = true;
    public boolean finishedloadTextbox = false;
    public Integer countitem = 0;
    public Integer countSpinner = 0;
    public Integer countSpinnerCreated = 0;
    public Integer countTextbox = 0;
    public Integer countTextboxdate = 0;
    public Integer sumSpinner = 0;
    public Integer sumTextbox = 0;

    public Integer countField = 0;
    public Integer sumField = 0;

    public String url = "";
    public String valueProduk = "";
    public String valuetype = "";
    public String valueTahun= "";

    public ArrayList<FieldModel> fieldArrayListCascading;
    public ArrayList<ContentModel> listContentrreference;

    FormActivity formActivity;
    private static ProgressDialog progressDialog;

    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    private Userdata userdata;
    private Dialog dialog;
    private SQLiteConfigKu sqLiteConfigKu;
    private ArrayList<ParameterModel> listspinner;
    public String itemChoose,regno,tc,tablename,sectionname, type, datalevel, newdata,imageId, nama, sectionform, typeList, listitemid, status, sectionType;
    public boolean isNew;

    public DinamicLayout(Activity context,
                         ArrayList<FieldModel> fieldArrayListtemp,
                         String datalevel,
                         String section,
                         String regno,
                         String tc,
                         String type,
                         String table_name,
                         String formName,
                         String newdata,
                         String imageid,
                         String namanasabah,
                         String sectionform,
                         boolean isnew,
                         String typeList,
                         String listitemid,
                         String status,
                         String sectionType
    ) {
        super(context);
        this.context = context;
        this.sectionname = section;
        this.regno= regno;
        this.tc = tc;
        this.type = type;
        this.tablename = table_name;
        this.formName = formName;
        this.datalevel = datalevel;
        this.newdata = newdata;
        this.fieldArrayList = fieldArrayListtemp;
        this.imageId = imageid;
        this.nama = namanasabah;
        this.sectionform = sectionform;
        this.isNew = isnew;
        this.typeList = typeList;
        this.listitemid = listitemid;
        this.status = status;
        this.sectionType = sectionType;
//        Log.e("INIT","FORM " + fieldArrayList.size());
//        Log.e("INIT","FORM " + fieldArrayListtemp.size());
        init();
    }

    private void init() {
        Log.e("INIT","FORM ");
        formData = new FormData(this.context);
        this.decimalFormat = new DecimalFormat("#");
        this.decimalFormat.setMaximumFractionDigits(0);

        sqLiteConfigKu = new SQLiteConfigKu(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofit.create(EndPoint.class);

        networkConnection = new NetworkConnection(getContext());
        userdata = new Userdata(getContext());
        finalListContent = new ArrayList<>();
        isfirstload = true;
        formActivity = new FormActivity();
    }

    public Integer countSpinner(String section){
        Integer countspinner = 0;
        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                if((explrObject.getString("fieldType").equalsIgnoreCase("dropdown"))) {
                    countspinner++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
        }
        return  countspinner;
    }

    public void getFieldData(final List<FieldModel> fieldModel, String flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();

        List<ContentModel> content = new ArrayList<>();
        for(FieldModel fieldmodel : fieldModel){
            ContentModel contentmodel = new ContentModel();
            contentmodel.setFieldName(fieldmodel.getFieldName());
            contentmodel.setFieldValue(fieldmodel.getFieldValue()==null? "null":fieldmodel.getFieldValue());
            content.add(contentmodel);
        }
        final ArrayList<FieldModel> list = new ArrayList<>();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final RetreiveRequestJson requestForm = new RetreiveRequestJson();
            requestForm.setRegno(regno);
            requestForm.setUserid(userdata.select().getUserid());
            requestForm.setTc(tc);
            requestForm.setFormname(type);
            requestForm.setSectionId(formName);
            requestForm.setTableName(tablename);
            requestForm.setType("newcascading");
            requestForm.setDataLevel(datalevel);
            requestForm.setContent(content);
            requestForm.setFlag(flag);
//            if(isNew){
            requestForm.setListItemId(imageId);
//            }

            Call<RetreiveCallbackJson> callField = endPoint.getCascadingNew(userdata.select().getAccesstoken(),requestForm);
            callField.enqueue(new Callback<RetreiveCallbackJson>() {
                @Override
                public void onResponse(Call<RetreiveCallbackJson> call, Response<RetreiveCallbackJson> response) {
                    if(response.isSuccessful()){
                        if(response.body().getStatus().equalsIgnoreCase("1")) {
                            dialog.dismiss();
                            if(response.body().getNeedChangeForm().equalsIgnoreCase("1")){
                                formName = response.body().getSectionIdAfter();
                                loadNewForm();
                            }else {
                                try {
                                    fieldArrayList = new ArrayList<>();
                                    sumSpinner = 0;
                                    for (DataModel data : response.body().getData()) {
                                        for (FieldModel field : data.getField()) {
                                            FieldModel fieldmodel = new FieldModel();
                                            fieldmodel.setFieldId(field.getFieldId());
                                            fieldmodel.setFieldName(field.getFieldName());
                                            fieldmodel.setFieldValue(field.getFieldValue());
                                            fieldmodel.setFieldValueList(field.getFieldValueList());
                                            fieldmodel.setFieldFlagEnabled(field.getFieldFlagEnabled());
                                            fieldmodel.setFieldFlagHide(field.getFieldFlagHide());
                                            fieldArrayList.add(fieldmodel);
                                            for (ContentModel fieldValueList : field.getFieldValueList()) {
//                                        Log.e("VALUE " , "FALUELIST 22" + fieldArrayListNew.size());
                                                ContentModel content = new ContentModel();
                                                content.setDataId(fieldValueList.getDataId());
                                                content.setDataDesc(fieldValueList.getDataDesc());
                                                content.setSelected(fieldValueList.getSelected());
                                                content.setContentId(field.getFieldName());
                                                finalListContent.add(content);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("GETDATAMASTER ", "EXCEPTION : " + e.toString());
                                }
                                sumSpinner = 0;
                                updateAllField();
                            }
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            dialog.dismiss();
                            removeUserData(response.body().getMessage());
                        }else{
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    } else{
                        Log.e("TESTMASUK","UNSUCCESS ");
                        dialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<RetreiveCallbackJson> call, Throwable t) {
                    Log.e("TESTMASUK","error " + t);
                }
            });
        }
    }

    public void create(String section){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
//        formActivity.showprogress(context, true);
//        Runnable progressRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                dialog.cancel();
//            }
//        };
//
//        Handler pdCanceller = new Handler();
//        pdCanceller.postDelayed(progressRunnable, countitem * 1000);

        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                createViewForm(explrObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {

        }
        dialog.cancel();
        setFieldFunction();

        Log.e("TYPELIST "," : " + typeList);
        if(typeList != null && typeList.equalsIgnoreCase("unassigned")){
            disableAllfield();
        }

    }
    public void setFieldFunction(){

        for(TextView textViewItem : titleTexts){
            for(FieldModel fieldModel : fieldArrayList){
                if (textViewItem.getTag().toString().equalsIgnoreCase("textview"+fieldModel.getFieldId())){
                    Log.e("TEXT FIELD ID "," ADALAH " +fieldModel.getFieldId());
                    if(fieldModel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                        textViewItem.setEnabled(false);
                    }else{
                        textViewItem.setEnabled(true);
                    }

                    if(fieldModel.getFieldFlagHide().equalsIgnoreCase("0")){
                        textViewItem.setVisibility(VISIBLE);
                    }else{
                        textViewItem.setVisibility(GONE);
                    }

                }

            }
        }

        for(final Spinner spinnerclick : spinners) {

            spinnerclick.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.e("Click SPINNER ", "YUU  : ");
                    hideKeyboard();
                    spinnerclick.performClick();
                    spinnerclick.setFocusableInTouchMode(true);
                    spinnerclick.setFocusable(true);
                    return false;
                }
            });

            spinnerclick.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return false;
                }
            });
            countField++;
            spinnerclick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Log.e("SPINNER countSpinner "," ADALAH " + countSpinner);
                    Log.e("SPINNER sumSpinner "," ADALAH " + sumSpinner);
                    if(countSpinner == sumSpinner){
                        finishedloadSpinner = true;
                    }
                    try {
                        String section = formData.select(formName);
                        ArrayList referenceParameter = new ArrayList<ParameterModel>();
                        String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "", fieldIdToUpdate = "", fieldNametoUpdate = "";
                        JSONObject jsnobject = new JSONObject(section);
                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                        for (int loop = 0; loop < jsonArray.length(); loop++) {
                            JSONObject explrObject = jsonArray.getJSONObject(loop);
                            JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                            for (int index = 0; index < jsonArrayreferences.length(); index++) {
                                JSONObject objectparameter = jsonArrayreferences.getJSONObject(index);
                                fieldvaluename = objectparameter.getString("parameterName");
                                fieldvalue = objectparameter.getString("parameterFieldValue");
                                fieldid = objectparameter.getString("parameterId");
//                                Log.e("SUM CASCAD DALAM22 " ,"Hasil : " + spinnerclick.getTag().toString());
//                                Log.e("SUM CASCAD DALAM33 " ,"Hasil : " + fieldid);
                                if(fieldid.equalsIgnoreCase(spinnerclick.getTag().toString())){
//                                    if(((finishedloadSpinner && countField == sumField))&& finishedloadSpinnerOnForm) {
                                    if(finishedloadSpinner){
                                        countSpinner = -1;
                                        countField = 0;
                                        finishedloadSpinner = false;
//                                        Log.e("SUM CASCAD DALAM44 " ,"Hasil : " + fieldid);

                                        setTextInputCascading(sectionform,"");
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    countSpinner++;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Log.e("SUM CASCAD DALAM44 " ,"Hasil : " + imageId);
            Log.e("SUM CASCAD DALAM55 " ,"Hasil : " + isNew);
            if(spinnerclick.getTag().toString().equalsIgnoreCase("IDF06001")
                    || spinnerclick.getTag().toString().equalsIgnoreCase("IDSTT041")){
                if(imageId != null)
                    spinnerclick.setEnabled(false);
                else {
                    spinnerclick.setEnabled(true);
                    if(isNew == true){
                        spinnerclick.setEnabled(false);
                    }
                }
            }
        }

        for(final TextInputEditText editText : textInputEditTexts){
            countField++;
            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    boolean isCascading = false;
                    try {
                        String section = formData.select(formName);
                        ArrayList referenceParameter = new ArrayList<ParameterModel>();
                        String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "", fieldIdToUpdate = "", fieldNametoUpdate = "";
                        JSONObject jsnobject = new JSONObject(section);
                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                        for (int loop = 0; loop < jsonArray.length(); loop++) {
                            JSONObject explrObject = jsonArray.getJSONObject(loop);
                            JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                            for (int index = 0; index < jsonArrayreferences.length(); index++) {
                                JSONObject objectparameter = jsonArrayreferences.getJSONObject(index);
                                fieldvaluename = objectparameter.getString("parameterName");
                                fieldvalue = objectparameter.getString("parameterFieldValue");
                                fieldid = objectparameter.getString("parameterId");

//                                Log.e("SUM CASCAD DALAM551 ", "Hasil : " + fieldid);
//                                Log.e("SUM CASCAD DALAM552 ", "Hasil : " + countTextbox);
                                if(fieldid.equalsIgnoreCase(editText.getTag().toString())){
                                    isCascading = true;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(((!hasFocus && countTextbox==1 ) &&  countField==sumField) && isCascading){
                        Log.e("HAS FOCUS VALUE", " : " + hasFocus);
                        countField = 0;
                        setTextInputCascading(sectionform,"");
                        finishedloadSpinnerOnForm=false;
                    }else{
                        countTextbox = 1;
                    }
                    finishedloadTextbox = false;
                }

            });

//            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    Log.e("Click SPINNER ", "YUU  : ");
//                    editText.setFocusable(false);
//                    spinner.requestFocus();
//                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                        editText.clearFocus();
//                        spinner.requestFocus();
//                        Log.e("Click SPINNER ", "TAA  : ");
////                        spinner.performClick();
//                    }
//                    return true;
//                }
//            });
        }

        for(final TextInputEditText editTextDate : textInputEditTextDates ){
            countField++;
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editTextDate.setText(sdf.format(myCalendar.getTime()));
                    Log.e("MASUK", "MASUK TAG" + editTextDate.getTag());
                    Log.e("MASUK", "MASUK ID" + sdf.format(myCalendar.getTime()));

                    boolean isCascading = false;
                    try {
                        String section = formData.select(formName);
                        ArrayList referenceParameter = new ArrayList<ParameterModel>();
                        String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "", fieldIdToUpdate = "", fieldNametoUpdate = "";
                        JSONObject jsnobject = new JSONObject(section);
                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                        for (int loop = 0; loop < jsonArray.length(); loop++) {
                            JSONObject explrObject = jsonArray.getJSONObject(loop);
                            JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                            for (int index = 0; index < jsonArrayreferences.length(); index++) {
                                JSONObject objectparameter = jsonArrayreferences.getJSONObject(index);
                                fieldvaluename = objectparameter.getString("parameterName");
                                fieldvalue = objectparameter.getString("parameterFieldValue");
                                fieldid = objectparameter.getString("parameterId");
                                if(fieldid.equalsIgnoreCase(editTextDate.getTag().toString())){
                                    isCascading = true;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(countField==sumField && isCascading){
                        countField = 0;
                        setTextInputCascading(sectionform,"");
                        finishedloadSpinnerOnForm=false;
                    }else{
                        countTextboxdate = 1;
                    }
                }

            };

            editTextDate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Log.e("MASUK ON FOCUS", "MASUK TAG" + editTextDate.getTag());
                    Log.e("MASUK ON FOCUS", "MASUK ID" + sdf.format(myCalendar.getTime()));
                }
            });

            editTextDate.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b){

                    }else {
                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        String myFormat = "dd-MM-yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        Log.e("MASUK ON FOCUS", "MASUK TAG" + editTextDate.getTag());
                        Log.e("MASUK ON FOCUS", "MASUK ID" + sdf.format(myCalendar.getTime()));
                    }
                }
            });
        /*    editTextDate.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    boolean isCascading = false;
                    try {
                        String section = formData.select(formName);
                        ArrayList referenceParameter = new ArrayList<ParameterModel>();
                        String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "", fieldIdToUpdate = "", fieldNametoUpdate = "";
                        JSONObject jsnobject = new JSONObject(section);
                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                        for (int loop = 0; loop < jsonArray.length(); loop++) {
                            JSONObject explrObject = jsonArray.getJSONObject(loop);
                            JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                            for (int index = 0; index < jsonArrayreferences.length(); index++) {
                                JSONObject objectparameter = jsonArrayreferences.getJSONObject(index);
                                fieldvaluename = objectparameter.getString("parameterName");
                                fieldvalue = objectparameter.getString("parameterFieldValue");
                                fieldid = objectparameter.getString("parameterId");
                                if(fieldid.equalsIgnoreCase(editTextDate.getTag().toString())){
                                    isCascading = true;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(((!hasFocus && countTextbox==1 ) &&  countField==sumField) && isCascading){
                        countField = 0;
                        setTextInputCascading(sectionform,"");
                        finishedloadSpinnerOnForm=false;
                    }else{
                        countTextbox = 1;
                    }
                    finishedloadTextbox = false;
                }
            });*/
        }

        for(ImageView buttonclick : imageviews){
            countField++;
            buttonclick.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(" HALLO ", "ID NYA : " + itemChoose + " " + tc + " " + regno);
                    Intent intentuserdata = new Intent(getContext(), CameraActivity.class);
                    intentuserdata.putExtra("SECTION_NAME", sectionname);
                    intentuserdata.putExtra("REGNO", regno);
                    intentuserdata.putExtra("TC", tc);
                    intentuserdata.putExtra("TYPE", type);
                    intentuserdata.putExtra("TABLE_NAME", tablename);
                    intentuserdata.putExtra("FORM_NAME", formName);
                    intentuserdata.putExtra("UPLOAD_TYPE", "surveyDocument");
                    intentuserdata.putExtra("NAMA_NASABAH", nama);
                    intentuserdata.putExtra("IMAGEID", imageId);

                    fieldArrayListCascading = new ArrayList<>();
                    try {
                        JSONObject jsnobject = new JSONObject(sectionform);
                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            setValueCascading(explrObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String doc_code = "", doc_id = "";
                    for (FieldModel model : fieldArrayListCascading) {
                        Log.e("DOC_CODE APALAH", " NAME : " + model.getFieldName());
                        Log.e("DOC_CODE APALAH", " VALUE: " + model.getFieldValue());
                        if (model.getFieldName().equalsIgnoreCase("DOC_CODE")) {
                            doc_code = model.getFieldValue();
                            intentuserdata.putExtra("DOC_CODE", model.getFieldValue());
                            Log.e("APALAH", " DOC_CODE : " + model.getFieldValue());
                        }
////                            Log.e("DOC NAME APALAH22"," : " + model.getFieldName());

                        if (model.getFieldName().equalsIgnoreCase("DOC_ID")) {
                            doc_id = model.getFieldValue();
                            Log.e("APALAH", " DOC_ID : " + model.getFieldValue());
                            intentuserdata.putExtra("DOC_ID", model.getFieldValue());
                        }
                        if (model.getFieldName().equalsIgnoreCase("DIRECTORY")) {
                            Log.e("APALAH", " DIRECTORY : " + model.getFieldValue());
                            intentuserdata.putExtra("DIRECTORY", model.getFieldValue());
                            url = model.getFieldValue();
                        }
//
                    }

                    if (doc_code.equalsIgnoreCase("0")) {
                        dialogMessage("Mohon pilih jenis dokumen !");
                    }else{
                        Log.e("URL IMAGE", " : " + url);
                        if (url.equalsIgnoreCase("")) {
                            intentuserdata.putExtra("UPLOAD_TYPE", "surveyDocument");
                            intentuserdata.putExtra("LISTITEMID","new");
                            if ((ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                                    (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                                    (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                                ActivityCompat.requestPermissions(context, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, 1);
                            } else {
                                context.startActivity(intentuserdata);
                            }

                        } else {
                            showDetailImage(url, doc_code, doc_id);
                        }

//                    showDetailImage();
                    }
                }
            });
        }
        for(final ImageView imagecopyclicked : imageviewscopy){
            countField++;
            imagecopyclicked.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Text Copied !", Toast.LENGTH_SHORT)
                            .show();
                    for(TextInputEditText text : textInputEditTextCopies){
                        if(imagecopyclicked.getTag().toString().replace("imageview", "").equalsIgnoreCase(text.getTag().toString())){
                            Log.e("COPY TEXT","APA YUYU " + text.getText());
                            setTextInputCascading(sectionform,"copyvalue");
                        }
                    }
                }
            });

        }
        for(final TextInputEditText editTextCopy : textInputEditTextCopies ){
            editTextCopy.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus)
                        editTextCopy.setError("Anda dapat melakukan copy dari alamat ktp");
                    else if(!hasFocus){
                        editTextCopy.setError(null);
                    }
                }
            });
        }

    }

    private void disableAllfield(){
        for(final Spinner spinnerclick : spinners) {
            spinnerclick.setEnabled(false);
        }
        for(final TextInputEditText editText : textInputEditTexts){
            editText.setEnabled(false);
        }

        for(TextInputEditText editTextDate : textInputEditTextDates ){
            editTextDate.setEnabled(false);
        }

        for(ImageView buttonclick : imageviews){
            buttonclick.setEnabled(false);
        }
        for(final ImageView imagecopyclicked : imageviewscopy){
            imagecopyclicked.setEnabled(false);
        }
        for(final TextInputEditText editTextCopy : textInputEditTextCopies ){
            editTextCopy.setEnabled(false);
        }

    }

    private void createViewForm(final JSONObject explrObject) {
        try {
            scrollView.requestFocus();
            layer1 = new LinearLayout(this.context);
            layer1.setTag("layer");
            layer1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            layer1.setOrientation(VERTICAL);
            topLayout.addView(layer1);
            layers1.add(layer1);

            titleText = new TextView(this.context);
            titleText.setTag("textview"+explrObject.getString("fieldId"));
            titleText.setText(explrObject.getString("fieldLabel"));
            titleText.setTypeface(Typeface.DEFAULT_BOLD);
            titleText.setPadding(10, 0, 0, 10);
            titleText.setTextSize(16f);
            titleText.setTextColor(getResources().getColor(R.color.black));

            cardView = new CardView(this.context);
            cardView.setTag("cardview"+explrObject.getString("fieldId"));
            cardView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            cardView.setPreventCornerOverlap(true);
            cardView.setUseCompatPadding(true);
            cardView.setContentPadding(15, 15, 15, 0);
            cardView.setRadius(2);
            cardView.requestLayout();

            layer2 = new LinearLayout(this.context);
            layer2.setTag("title");
            layer2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            layer2.setOrientation(VERTICAL);

            labelText = new TextView(this.context);
            labelText.setText(explrObject.getString("fieldLabel"));
            labelText.setTextColor(this.context.getResources().getColor(R.color.textHint));

            layer1.addView(cardView);
            layer2.addView(titleText);
            cardView.addView(layer2);
            cardViews.add(cardView);
            titleTexts.add(titleText);

            linearLayout = new LinearLayout(this.context);
            linearLayout.setTag(explrObject.getString("fieldId"));
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            linearLayout.setPadding(10, 0, 10, 0);
            layer2.addView(linearLayout);
            linearLayouts.add(linearLayout);

            if (explrObject.getString("fieldType").equalsIgnoreCase("dropdown")) {
                spinner = new Spinner(this.context);
//                Log.e("FIELD NAME ", " hasReference :" + explrObject.getString("fieldId"));
                spinner.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                spinner.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1) {{
                    gravity = Gravity.LEFT;
                }});

                ArrayList<ContentModel> listContent = new ArrayList<>();
                final ArrayList<String> listOfSpinner = new ArrayList<>();
                boolean iscascading = false;
                if (explrObject.getString("referenceType").equalsIgnoreCase("reference")) {
                    if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                        spinner.setEnabled(false);
                    }

//                    String item = formData.select(explrObject.getString("referenceName"));
                    spinner.setTag(explrObject.getString("fieldId"));
                    sumSpinner++;
                    sumField++;
                    try {
//                        JSONObject jsnobject1 = new JSONObject(item);
//                        JSONArray jsonArray = jsnobject1.getJSONArray("content");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject explrObjectdesc = jsonArray.getJSONObject(i);
                        for(FieldModel fieldmodelref : fieldArrayList){
                            for (ContentModel contentmod : fieldmodelref.getFieldValueList()) {
                                if(fieldmodelref.getFieldId().equalsIgnoreCase(explrObject.getString("fieldId"))){
                                    ContentModel contentmodel = new ContentModel();
                                    contentmodel.setDataId(contentmod.getDataId());
                                    contentmodel.setDataDesc(contentmod.getDataDesc());
                                    contentmodel.setSelected(contentmod.getSelected());
                                    contentmodel.setContentId(fieldmodelref.getFieldName());
                                    listContent.add(contentmodel);
                                }
                            }
                        }
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if (explrObject.getString("referenceType").equalsIgnoreCase("cascading")) {
                    if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                        spinner.setEnabled(false);
                    }
                    spinner.setTag(explrObject.getString("fieldId"));
//                    spinner.setFocusable(true);
//                    spinner.setFocusableInTouchMode(true);
//                    spinner.requestFocus();
                    sumSpinner++;
                    sumField++;
                    iscascading = true;
//                    String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "";
//                    JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
//                    ArrayList referenceParameter = new ArrayList<ParameterModel>();
//                    Log.e("VALUE ", "SPINSPIN1 " + explrObject.getString("fieldId"));
                    for (FieldModel field : fieldArrayList) {
                        if (explrObject.getString("fieldId").equalsIgnoreCase(field.getFieldId())) {
                            for (ContentModel contentmod : field.getFieldValueList()) {
                                ContentModel contentmodel = new ContentModel();
                                contentmodel.setDataDesc(contentmod.getDataDesc());
                                ;                               contentmodel.setDataId(contentmod.getDataId());
                                contentmodel.setSelected(contentmod.getSelected());
                                contentmodel.setContentId(field.getFieldName());
                                listContent.add(contentmodel);
                            }
                        }
                    }
                }

                for (ContentModel contentModel : listContent) {
                    listOfSpinner.add(contentModel.getDataDesc().toUpperCase());
                }
                spinner.setAdapter(new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,
                        listOfSpinner) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if(position >= listOfSpinner.size()) {
                            position = 0;
                        }
                        View v = super.getView(position, convertView, parent);
                        v.setPadding(v.getPaddingLeft(),15, 0, 15);
                        ((TextView) v).setGravity(Gravity.LEFT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                            itemChoose = ((TextView) v).getText().toString();
                        }
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {

                        View v = super.getDropDownView(position, convertView, parent);

                        ((TextView) v).setGravity(Gravity.CENTER);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                        }
                        return v;
                    }
                    {
                        setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                    }
                });

                for (ContentModel model : listContent) {
                    ContentModel modelcontent = new ContentModel();
                    modelcontent.setDataId(model.getDataId());
                    modelcontent.setDataDesc(model.getDataDesc().toUpperCase());
                    modelcontent.setSelected(model.getSelected());
                    modelcontent.setIndexData(listContent.indexOf(model));
                    modelcontent.setContentId(model.getContentId());
                    finalListContent.add(modelcontent);
                }

                for (FieldModel fieldmodel : fieldArrayList){
                    int ind = 0;
                    boolean isIndexselected= false;
                    if(spinner.getTag().toString().equalsIgnoreCase(fieldmodel.getFieldId())){
                        for (ContentModel model : finalListContent){

                            if(iscascading) {
                                if (model.getSelected().equalsIgnoreCase("true") && model.getContentId().equalsIgnoreCase(fieldmodel.getFieldName())) {
                                    isIndexselected = true;
                                    spinner.setSelection(model.getIndexData());
                                    ind = model.getIndexData();
                                }
                            }else {
                                if (model.getSelected().equalsIgnoreCase("true") && model.getContentId().equalsIgnoreCase(fieldmodel.getFieldName())) {
                                    ind = model.getIndexData();
                                    isIndexselected = true;
                                    spinner.setSelection(model.getIndexData());
                                    if(spinner.getTag().toString().equalsIgnoreCase("MDE01007")) {
                                        valueProduk = model.getDataDesc();
//                                        Log.e("SPINNER AFI 4 ", " ITEM " + model.getDataDesc());
                                    }
                                }
                            }
                        }

                        if(fieldmodel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                            spinner.setEnabled(false);
                        }else{
                            spinner.setEnabled(true);
                        }

                        if(fieldmodel.getFieldFlagHide().equalsIgnoreCase("0")){
                            spinner.setVisibility(VISIBLE);
                        }else{
                            spinner.setVisibility(GONE);
                        }
                    }
                }
                layer2.addView(spinner);
                spinners.add(spinner);
                dialog.cancel();

                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow popupWindow = (ListPopupWindow) popup.get(spinner);
                    popupWindow.setHeight(600);
                } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) { }

            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textbox")){
                textInputEditText = new TextInputEditText(this.context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textInputEditText.setBackground(null);
                }
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setHint(explrObject.getString("fieldLabel"));
                if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                    textInputEditText.setEnabled(false);
                }
                try {
                    int length = Integer.valueOf(explrObject.getString("fieldDataLength"));
                    textInputEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length)});
                }catch(Exception e){}
//                textInputEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Integer.valueOf(explrObject.getString("fieldDataLength"))) });
                sumTextbox++;
                sumField++;
                if(explrObject.getString("fieldRule").equalsIgnoreCase("decimal")) {
                    textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    textInputEditText.addTextChangedListener(new NumberSeparatorTextWatcher(textInputEditText));
                }else if(explrObject.getString("fieldRule").equalsIgnoreCase("numericonly")) {
                    textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER );
                }else{
                    textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
//                textInputEditText.setSingleLine(false);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);
                countTextbox = 1;
                for(final TextInputEditText edittext : textInputEditTexts) {
                    for (FieldModel model : fieldArrayList) {
                        if (edittext.getTag().toString().equalsIgnoreCase(model.getFieldId())) {
                            try {
                                textInputEditText.setText(model.getFieldValue().toUpperCase());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (model.getFieldId().equalsIgnoreCase("MDE01014") && edittext.getTag().toString().equalsIgnoreCase("MDE01014")) {
                                try {
                                    if (Float.valueOf(model.getFieldValue()) > 0L) {
                                        if((valueProduk.equalsIgnoreCase("NEW")
                                                || valueProduk.equalsIgnoreCase("USED"))) {
                                            edittext.setEnabled(true);
                                        }
                                        else {
                                            edittext.setEnabled(true);
                                        }
                                    }else{
                                        edittext.setEnabled(true);
                                    }
                                }catch(Exception e){}
                            }

                            if (model.getFieldId().equalsIgnoreCase("MDE01015") && edittext.getTag().toString().equalsIgnoreCase("MDE01015")){
                                if(valueProduk.equalsIgnoreCase("NEW")
                                        || valueProduk.equalsIgnoreCase("USED")) {
                                    edittext.setEnabled(true);
                                }
                                else {
                                    edittext.setEnabled(false);
                                }
                            }

                            if (model.getFieldId().equalsIgnoreCase("MDE01016") && edittext.getTag().toString().equalsIgnoreCase("MDE01016")){
                                if(valueProduk.equalsIgnoreCase("DNA")) {
                                    edittext.setEnabled(true);
                                }else{
                                    edittext.setEnabled(false);
                                }
                            }

                            if(model.getFieldFlagEnabled().equalsIgnoreCase("0")){
                                edittext.setEnabled(false);
                            }else{
                                edittext.setEnabled(true);
                            }

                            if(model.getFieldFlagHide().equalsIgnoreCase("0")){
                                edittext.setVisibility(VISIBLE);
                            }else{
                                edittext.setVisibility(GONE);
                            }
                        }
                    }
                }

            }else if(explrObject.getString("fieldType").equalsIgnoreCase("label")){

                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setHint(explrObject.getString("fieldLabel"));
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textInputEditText.setBackground(null);
                }
                textInputEditText.setSingleLine(false);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                    textInputEditText.setEnabled(false);
                }

                sumField++;
                for(FieldModel model : fieldArrayList){
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
//                        Log.e("ADDA", " ISI LABEL : " + model.getFieldValue());
                        textInputEditText.setText( model.getFieldValue().toUpperCase());
                        textInputEditText.setEnabled(false);
                    }
                }

                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("upload file")){
//                button = new Button(this.context);
//                button.setTag(explrObject.getString("fieldId"));
//                button.setText("pilih file".toUpperCase());
//                button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
//                button.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1) {{
//                    gravity = Gravity.CENTER;
//                }});
//                sumField++;
//                buttons.add(button);
//                for(FieldModel model : fieldArrayList){
//                    if(!model.getFieldValue().equalsIgnoreCase("")) {
//                        for (Button button : buttons)
//                            if (model.getFieldId().equalsIgnoreCase(button.getTag().toString())) {
//                                imageView = new ImageView(this.context);
//                                imageView.setTag(explrObject.getString("fieldId") + "imgView");
//                                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
//                                layer2.addView(imageView);
//                                imageviews.add(imageView);
//                                URL newurl = new URL( model.getFieldValue());
//                                Picasso.with(getContext()).load(model.getFieldValue()).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
//                                        .error(R.drawable.ic_person_white_24dp).resize(400, 400).centerCrop()
//                                        .into(imageView);
//                            }
//                    }
//                }
//
//                layer2.addView(button);


                imageView = new ImageView(this.context);
                imageView.setTag(explrObject.getString("fieldId"));
//                imageView.setText("pilih file".toUpperCase());
//                imageView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

                Picasso.with(getContext()).load(R.mipmap.ic_add)// Place holder image from drawable folder
                        .error(R.mipmap.ic_add).resize(400, 400).centerCrop()
                        .into(imageView);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1) {{
                    gravity = Gravity.CENTER;
                }});
                imageView.setPadding(0,10,0,20);
                sumField++;
                imageviews.add(imageView);
                layer2.addView(imageView);
                for(FieldModel model : fieldArrayList){
                    if(!model.getFieldValue().equalsIgnoreCase("")) {
                        for (ImageView button : imageviews)
                            if (model.getFieldId().equalsIgnoreCase(button.getTag().toString())) {
//                                button = new ImageView(this.context);
//                                button.setTag(explrObject.getString("fieldId") + "imgView");
                                button.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
//                                layer2.addView(imageView);
//                                imageviews.add(imageView);
                                url = model.getFieldValue();
                                Picasso.with(getContext()).load(model.getFieldValue()).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                                        .error(R.drawable.ic_person_white_24dp).resize(400, 400).centerCrop()
                                        .into(button);

                                if(model.getFieldFlagEnabled().equalsIgnoreCase("0")){
                                    button.setEnabled(false);
                                }else{
                                    button.setEnabled(true);
                                }

                                if(model.getFieldFlagHide().equalsIgnoreCase("0")){
                                    button.setVisibility(VISIBLE);
                                }else{
                                    button.setVisibility(GONE);
                                }
                            }

                    }
                }

            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea")){
                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setText(explrObject.getString("fieldLabel"));
//                textInputEditText.setSingleLine(false);
                if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                    textInputEditText.setEnabled(false);
                }
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                sumField++;
                for(FieldModel model : fieldArrayList){
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        textInputEditText.setText( model.getFieldValue());
//                        textInputEditText.setEnabled(false);

                        if(model.getFieldFlagEnabled().equalsIgnoreCase("0")){
                            textInputEditText.setEnabled(false);
                        }else{
                            textInputEditText.setEnabled(true);
                        }

                        if(model.getFieldFlagHide().equalsIgnoreCase("0")){
                            textInputEditText.setVisibility(VISIBLE);
                        }else{
                            textInputEditText.setVisibility(GONE);
                        }
                    }
                }
                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("date")) {

                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textInputEditText.setBackground(null);
                }
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                    textInputEditText.setEnabled(false);
                }
                sumField++;
                for (FieldModel model : fieldArrayList) {
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        Log.e("24 ADDA", " ISI DATE: " + explrObject.getString("fieldId"));
                        textInputEditText.setText(model.getFieldValue());
//                        textInputEditText.setEnabled(false);
//                        textInputEditTextDates.add(textInputEditText);

                        if(model.getFieldFlagEnabled().equalsIgnoreCase("0")){
                            textInputEditText.setEnabled(false);
                        }else{
                            textInputEditText.setEnabled(true);
                        }

                        if(model.getFieldFlagHide().equalsIgnoreCase("0")){
                            textInputEditText.setVisibility(VISIBLE);
                        }else{
                            textInputEditText.setVisibility(GONE);
                        }
                    }
                }
                countTextboxdate = 1;
                textInputEditTextDates.add(textInputEditText);
                layer2.addView(textInputEditText);
//                for (final TextInputEditText text : textInputEditTextDates) {
//
//
//                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea-copy")) {

                layer3 = new LinearLayout(this.context);
                layer3.setTag("title");
                layer3.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
                layer3.setOrientation(HORIZONTAL);

                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setHint(explrObject.getString("fieldLabel"));
                if (explrObject.getString("readOnly").equalsIgnoreCase("1")) {
                    textInputEditText.setEnabled(false);
                }
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.9f));
                for(FieldModel model : fieldArrayList){
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        textInputEditText.setText( model.getFieldValue());
                        if(model.getFieldFlagEnabled().equalsIgnoreCase("0")){
                            textInputEditText.setEnabled(false);
                        }else{
                            textInputEditText.setEnabled(true);
                        }

                        if(model.getFieldFlagHide().equalsIgnoreCase("0")){
                            textInputEditText.setVisibility(VISIBLE);
                        }else{
                            textInputEditText.setVisibility(GONE);
                        }
                    }
                }

                imageView = new ImageView(this.context);
                imageView.setTag("imageview"+explrObject.getString("fieldId" ));
                Picasso.with(getContext()).load(R.drawable.ic_copy)// Place holder image from drawable folder
                        .error(R.drawable.ic_copy).resize(80, 80).centerCrop()
                        .into(imageView);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f) {{
                    gravity = Gravity.CENTER;
                }});
                imageView.setPadding(0,10,0,20);
                sumField++;
                layer3.addView(textInputEditText);
                textInputEditTextCopies.add(textInputEditText);
                layer3.addView(imageView);
                imageviewscopy.add(imageView);
                layer2.addView(layer3);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createItem(JSONObject object) {
        try {
            scrollView.requestFocus();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            layer1 = new LinearLayout(this.context);
            layer1.setTag(object.getString("data"));
            layer1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            layer1.setOrientation(VERTICAL);
            topLayout.addView(layer1);
            layers1.add(layer1);

            titleText = new TextView(this.context);
            titleText.setTag(object.getString("data"));
            titleText.setText(object.getString("data").replace("_", " "));
            titleText.setPadding(0, 0, 0, 10);
            titleText.setTextSize(16f);
            titleText.setTextColor(getResources().getColor(R.color.black));

            cardView = new CardView(this.context);
            cardView.setTag(object.getString("data"));
            cardView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            cardView.setPreventCornerOverlap(true);
            cardView.setUseCompatPadding(true);
            cardView.setContentPadding(15, 15, 15, 15);
            cardView.setRadius(2);
            cardView.requestLayout();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getTextInput(String section, final String regno, final String tc, final String form_name, final String table_name, boolean send, final Double longitude, final Double latitude, final String address){
        fieldArrayListtemp = new ArrayList<>();
        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            finished = false;
            for (int i = 0; i < jsonArray.length() && !finished; i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                setValue(explrObject, send);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(send && !finished)
            sendData(regno, tc, table_name, form_name, longitude,latitude,address);
        if(!send) {
            new MaterialDialog.Builder(getContext())
                    .content("Delete Data ?")
                    .positiveText(R.string.buttonYes)
                    .negativeText(R.string.buttonNo)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            dialog.dismiss();
                            deleteData(regno, tc, table_name, form_name,longitude,latitude,address);
                        }
                        public void onNegative(MaterialDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .cancelable(true)
                    .show();

        }
    }


    public void setTextInputCascading(String section, String flag){
        fieldArrayListCascading = new ArrayList<>();
        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                setValueCascading(explrObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.e("LEWAT SINI ", "SEKALI " + countSpinner);
        getFieldData(fieldArrayListCascading, flag);
    }

    public void setValue(JSONObject explrObject, boolean isSave){

        try {
            if (explrObject.getString("fieldType").equalsIgnoreCase("dropdown")){
                for(Spinner spinner : spinners) {
                    if(spinner.getTag()!= null){
                        if (spinner.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                            FieldModel model = new FieldModel();
                            model.setFieldName(explrObject.getString("fieldName"));
                            for (ContentModel contentModel : finalListContent) {
                                if(isSave) {
                                    if (explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && spinner.getSelectedItemPosition() == 0) {
                                        dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                        finished = true;
                                        break;
                                    }
                                }
                                if ((spinner.getSelectedItem() != null) &&
                                        contentModel.getDataDesc() != null) {
                                    if (spinner.getSelectedItem().toString().equalsIgnoreCase(contentModel.getDataDesc().toUpperCase())) {
                                        String selectedValue = "";
                                        selectedValue= contentModel.getDataId() == null ? "null" : contentModel.getDataId();
                                        model.setFieldValue(selectedValue);
                                    }
                                } else {
                                    model.setFieldValue("null");
                                }
                            }
                            if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                                fieldArrayListtemp.add(model);
                            }
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea")) {
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(isSave) {
                            if (explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")) {
                                dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                finished = true;
                                break;
                            }
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null" );
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListtemp.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textbox")){
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(isSave) {
                            if (explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")) {
                                dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                finished = true;
                                break;
                            }
                        }
                        try {
                            int length = Integer.valueOf(explrObject.getString("fieldDataLength"));
                            if(text.getText().length() > Integer.valueOf(length)){
                                dialog(R.string.errorMaxNama);
                                finished = true;
                                break ;
                            }
                        }catch(Exception e){}

                        String fieldValue = text.getText().length() > 0 ? text.getText().toString().replace(",", "") :"null";
                        fieldValue = fieldValue.replace(".","");
                        model.setFieldValue(fieldValue);
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListtemp.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("date")){
                for(TextInputEditText text : textInputEditTextDates){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(isSave) {
                            if (explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")) {
                                dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                finished = true;
                                break;
                            }
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null");
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListtemp.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("label")){
                for(TextInputEditText text : textInputEditTexts){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() : "null");
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListtemp.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea-copy")){
                for (TextInputEditText text : textInputEditTextCopies) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(isSave) {
                            if (explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")) {
                                dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                finished = true;
                                break;
                            }
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null" );
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListtemp.add(model);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setValueCascading(JSONObject explrObject){
        try {
            if (explrObject.getString("fieldType").equalsIgnoreCase("dropdown")){

                for(Spinner spinner : spinners) {
                    if(spinner.getTag()!= null){
                        if (spinner.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                            FieldModel model = new FieldModel();
                            model.setFieldName(explrObject.getString("fieldName"));
                            for (ContentModel contentModel : finalListContent) {
                                if ((spinner.getSelectedItem() != null ) &&
                                        contentModel.getDataDesc() != null) {
                                    if (spinner.getSelectedItem().toString().equalsIgnoreCase(contentModel.getDataDesc().toUpperCase())) {
                                        model.setFieldValue(contentModel.getDataId() == null ? "null" : contentModel.getDataId());
                                    }
                                } else {
                                    model.setFieldValue("null");
                                }
                            }
                            Log.e("FIELD SAVE "," LALALLALA: " + explrObject.getString("fieldSave"));
                            if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                                fieldArrayListCascading.add(model);
                            }
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea")) {
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null" );
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textbox")){
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        String fieldValue = text.getText().length() > 0 ? text.getText().toString().replace(",", "") :"null";
                        fieldValue = fieldValue.replace(".","");
                        model.setFieldValue(fieldValue);
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("date")){
                for(TextInputEditText text : textInputEditTextDates){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null");
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("label")){
                for(TextInputEditText text : textInputEditTexts){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() : "null");
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("upload file")){
                for(ImageView imageview : imageviews){
                    if(imageview.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(url);
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea-copy")) {
                for (TextInputEditText text : textInputEditTextCopies) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null" );
                        if (explrObject.getString("fieldSave").equalsIgnoreCase("1")) {
                            fieldArrayListCascading.add(model);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String regno, String tc, String table_name, String form_name, Double longitude, Double latitude, String address){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            for(FieldModel model : fieldArrayListtemp){
                dialog.dismiss();
            }
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(tc);
            request.setFormname(form_name);
            request.setTableName(table_name);
            request.setField(fieldArrayListtemp);
            request.setLon(String.valueOf(longitude));
            request.setLat(String.valueOf(latitude));
            request.setAddr(String.valueOf(address));

            Call<SetDataJson.SetDataCallback> callBack = endPoint.setData(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    Log.e("dialogSukses ","HORE ");
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogsuksessubmit(response.body().getMessage());
                        } else if(response.body().getStatus().equalsIgnoreCase("0")){
                            dialogMessage(response.body().getMessage());
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            removeUserData(response.body().getMessage());
                        }else{
                            dialogMessage(response.body().getMessage());
                        }
                    } else{
                        Log.e("dialog gagal dua ","HORE ");
                        dialogMessage(response.body().getMessage());

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

    public void deleteData(String regno, String tc, String table_name, String form_name, Double longitude, Double latitude, String address){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            for(FieldModel model : fieldArrayListtemp){
                dialog.dismiss();
            }
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(tc);
            request.setFormname(form_name);
            request.setTableName(table_name);
            request.setField(fieldArrayListtemp);
            request.setLon(String.valueOf(longitude));
            request.setLat(String.valueOf(latitude));
            request.setAddr(String.valueOf(address));


            Call<SetDataJson.SetDataCallback> callBack = endPoint.deleteData(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    Log.e("dialogSukses ","HORE ");
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogsuksessubmit(response.body().getMessage());
                        } else if(response.body().getStatus().equalsIgnoreCase("0")){
                            dialogMessage(response.body().getMessage());
                        }else if(response.body().getStatus().equalsIgnoreCase("100")){
                            removeUserData(response.body().getMessage());
                        }else {
                            dialogMessage(response.body().getMessage());
                        }
                    } else{
                        Log.e("dialog gagal dua ","HORE ");
                        dialogMessage(response.body().getMessage());

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


    public ArrayList<FieldModel> getFieldArrayList(){
        ArrayList<FieldModel> fieldModelArrayList = new ArrayList<>();
        fieldModelArrayList = fieldArrayListtemp;
        return fieldModelArrayList;
    }

    public  ArrayList<FieldModel> fieldArrayListtemp(){
        ArrayList<FieldModel> fieldArrayListtemp = new ArrayList<FieldModel>();

        return  fieldArrayListtemp;
    }

    public void setTopLayout(LinearLayout view){
        this.topLayout = view;
    }

    public void setScrollView(ScrollView view){
        this.scrollView = view;
    }


    public void setData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                createItem(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateAllField(){

        for(TextView textViewItem : titleTexts){
            for(FieldModel fieldModel : fieldArrayList){
                if (textViewItem.getTag().toString().equalsIgnoreCase("textview"+fieldModel.getFieldId())){
                    Log.e("TEXT FIELD ID "," ADALAH " +fieldModel.getFieldId());
                    if(fieldModel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                        textViewItem.setEnabled(false);
                    }else{
                        textViewItem.setEnabled(true);
                    }

                    if(fieldModel.getFieldFlagHide().equalsIgnoreCase("0")){
                        textViewItem.setVisibility(VISIBLE);
                    }else{
                        textViewItem.setVisibility(GONE);
                    }

                }

            }
        }

        for(CardView cardviewItem : cardViews){
            Log.e("TEXTVIEW ID "," ADALAH " + cardviewItem.getTag().toString());

            for(FieldModel fieldModel : fieldArrayList){
                if (cardviewItem.getTag().toString().equalsIgnoreCase("cardview"+fieldModel.getFieldId())){
                    if(fieldModel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                        cardviewItem.setEnabled(false);
                    }else{
                        cardviewItem.setEnabled(true);
                    }

                    if(fieldModel.getFieldFlagHide().equalsIgnoreCase("0")){
                        cardviewItem.setVisibility(VISIBLE);
                    }else{
                        cardviewItem.setVisibility(GONE);
                    }

                }

            }
        }

        for(Spinner spinneritem : spinners){
            countField++;
            countSpinnerCreated++;
            countSpinner=-1;
            updateSpinnerItemNew(spinneritem);
            countSpinner++;
        }

        for(TextInputEditText editText : textInputEditTexts){
            countField++;
            for(FieldModel fieldModel : fieldArrayList) {
                if (editText.getTag().toString().equalsIgnoreCase(fieldModel.getFieldId()))
                    editText.setText(fieldModel.getFieldValue().toUpperCase());

                if (editText.getTag().toString().equalsIgnoreCase(fieldModel.getFieldId())){
                    if(fieldModel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                        editText.setEnabled(false);
                    }else{
                        editText.setEnabled(true);
                    }

                    if(fieldModel.getFieldFlagHide().equalsIgnoreCase("0")){
                        editText.setVisibility(VISIBLE);
                    }else{
                        editText.setVisibility(GONE);
                    }
                }
            }
        }
        for(TextInputEditText editText : textInputEditTextDates){
            for(FieldModel fieldModel : fieldArrayList){
                if (editText.getTag().toString().equalsIgnoreCase(fieldModel.getFieldId())){
                    if(fieldModel.getFieldFlagEnabled().equalsIgnoreCase("0")){
                        editText.setEnabled(false);
                    }else{
                        editText.setEnabled(true);
                    }

                    if(fieldModel.getFieldFlagHide().equalsIgnoreCase("0")){
                        editText.setVisibility(VISIBLE);
                    }else{
                        editText.setVisibility(GONE);
                    }
                }
            }

            countField++;
        }

        for(TextInputEditText editText : textInputEditTextCopies) {
            countField++;
            for (FieldModel fieldModel : fieldArrayList) {
                if (editText.getTag().toString().equalsIgnoreCase(fieldModel.getFieldId()))
                    editText.setText(fieldModel.getFieldValue().toUpperCase());
            }
        }

        finishedloadSpinnerOnForm = true;
    }

    protected void dialogsuksessubmit(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

//                        intent.putExtra("SECTION_NAME", sectionname);
                        Log.e("HIII ", " : " + regno);
                        Log.e("HUUU ", " : " + tc + " HO " + formName + " HUOIA " +type);

                        if(datalevel.equalsIgnoreCase("listfield")) {
                            Intent intent = new Intent(context, FullEntryList.class);
                            intent.putExtra("REGNO", regno);
                            intent.putExtra("TC", tc);
                            intent.putExtra("TYPE", type);
                            intent.putExtra("FORM_NAME", formName);
                            intent.putExtra("TABLE_NAME", tablename);
                            intent.putExtra("NAMA_NASABAH", nama);
                            intent.putExtra(ParameterKey.SECTION_NAME, sectionname);
                            context.startActivity(intent);
                        }else {
                            if(newdata != null && newdata.equalsIgnoreCase("1")) {
                                Intent i = new Intent(context, MainActivityDashboard.class);
                                i.putExtra("FLAG_SUBMIT", "1");
                                context.startActivity(i);
                            }else{
                                Intent intent = new Intent(context, FullEntry.class);
                                intent.putExtra("REGNO", regno);
                                intent.putExtra("TC", tc);
                                intent.putExtra("TYPE", type);
                                intent.putExtra("FORM_NAME", formName);
                                intent.putExtra("TABLE_NAME", tablename);
                                intent.putExtra("NAMA_NASABAH", nama);
                                context.startActivity(intent);
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }

    protected void dialog(int rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }

    protected void dialogMessage(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }


    public void updateSpinnerItemNew(final Spinner idspinner){
        ArrayList<String> listring = new ArrayList<>();
        int i = 0;

        for(FieldModel mod : fieldArrayList){
            if(mod.getFieldId().equalsIgnoreCase(idspinner.getTag().toString())) {
                for (ContentModel model : mod.getFieldValueList()) {
                    listring.add(model.getDataDesc().toUpperCase());
                    if(model.getSelected().equalsIgnoreCase("true")) {
                        i = mod.getFieldValueList().indexOf(model);
                        if(i != 0 && idspinner.getTag().toString().equalsIgnoreCase("MDE01007")){
                            valueProduk = model.getDataDesc();
                        }
                        if(i != 0 && idspinner.getTag().toString().equalsIgnoreCase("MDE01009")){
                            valuetype = model.getDataDesc();
                        }
                        if(i != 0 && idspinner.getTag().toString().equalsIgnoreCase("MDE01012")){
                            valueTahun = model.getDataDesc();
                        }
                    }
                    else if(model.getSelected().equalsIgnoreCase("false")){}
                }
                if(mod.getFieldFlagEnabled().equalsIgnoreCase("0")){
                    idspinner.setEnabled(false);
                }else{
                    idspinner.setEnabled(true);
                }

                if(mod.getFieldFlagHide().equalsIgnoreCase("0")){
                    sumSpinner++;
                    idspinner.setVisibility(VISIBLE);
                }else{
                    idspinner.setVisibility(GONE);
                }
            }

        }


        if (listring != null) {
            idspinner.setAdapter(new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,
                    listring) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    v.setPadding(v.getPaddingLeft(),15, 0, 15);
                    ((TextView) v).setGravity(Gravity.LEFT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                        itemChoose = ((TextView) v).getText().toString();
                    }
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {

                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setGravity(Gravity.LEFT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    }

                    return v;

                }

                {
                    setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                }
            });
            idspinner.setSelection(i);
        }

    }

    private void showDetailImage(String url, final String doc_code, final String doc_id){
        String urldetail = url.replace("_thumbnails", "");
        Log.e("HAAAA ","NEW URL " + urldetail);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.detail_image, null);
//        final ScrollView _scrool = (ScrollView) dialogView.findViewById(R.id.scroolImage);
        final ImageView _detailimage = (ImageView) dialogView.findViewById(R.id.detailimage);
        final Button _fotoUlangButton = (Button) dialogView.findViewById(R.id.fotoUlangButton);
        final Button _kembaliButton = (Button) dialogView.findViewById(R.id.kembaliButton);
        Picasso.with(dialogView.getContext()).load(urldetail).placeholder(R.mipmap.ic_add)// Place holder image from drawable folder
                .error(R.mipmap.ic_add)
                .into(_detailimage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError() {
                        dialog.dismiss();
                    }
                });
        dialogBuilder.setView(dialogView);
        dialog.dismiss();
        dialogBuilder.show();

        _fotoUlangButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentuserdata = new Intent(getContext(), CameraActivity.class);
                intentuserdata.putExtra("SECTION_NAME",sectionname);
                intentuserdata.putExtra("REGNO" ,regno);
                intentuserdata.putExtra("TC",tc);
                intentuserdata.putExtra("TYPE",type);
                intentuserdata.putExtra("TABLE_NAME",tablename);
                intentuserdata.putExtra("FORM_NAME",formName);
                intentuserdata.putExtra("UPLOAD_TYPE","surveyDocument");
                intentuserdata.putExtra("NAMA_NASABAH",nama);
                intentuserdata.putExtra("IMAGEID",imageId);
                intentuserdata.putExtra("DOC_CODE", doc_code);
                intentuserdata.putExtra("DOC_ID", doc_id);
                intentuserdata.putExtra("UPLOAD_TYPE","surveyDocument");
                if ((ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

                    ActivityCompat.requestPermissions(context, new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);
                }else {
                    context.startActivity(intentuserdata);
                }

            }
        });
        _kembaliButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.cancel();
            }
        });
    }
    protected void removeUserData(String message){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        formData.deleteAll("FORM_SURVEY");
        formData.deleteAll("FORM_SURVEY_DATA");
        formData.deleteAll("FORM_SURVEY_REFERENCE");
        formData.deleteAll();
        userdata.deleteAll();
        context.finish();
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void setFormMaster(String token) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
            requestForm.setTc(tc);
            requestForm.setRegno(regno);
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
//                    callRetreiveData();
                }
                @Override
                public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                    dialog(R.string.errorBackend);
                    dialog.dismiss();
                }

            });

        }
    }

    private void loadNewForm(){
        Intent intent = new Intent(getContext(), FormActivity.class);
        intent.putExtra(ParameterKey.SECTION_NAME, sectionname);
        intent.putExtra(ParameterKey.REGNO, regno);
        intent.putExtra(ParameterKey.TC, tc);
        intent.putExtra(ParameterKey.TYPE, type);
        intent.putExtra(ParameterKey.STATUS,status);
        intent.putExtra(ParameterKey.TABLE_NAME, tablename);
        intent.putExtra(ParameterKey.FORM_NAME, formName);
        intent.putExtra(ParameterKey.SECTION_TYPE, sectionType);
        intent.putExtra(ParameterKey.NAMA_NASABAH, nama);
        intent.putExtra(ParameterKey.TYPELIST, typeList);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

}