package ops.screen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import base.network.TaskListJson;
import base.screen.BaseDialogActivity;
import base.sqlite.TaskListDetailModel;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.TaskListInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;

public class TaskListActivity extends BaseDialogActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnback_toolbar)
    ImageView btnback_toolbar;

    @BindView(R.id.taskListRecycleAll)
    RecyclerView taskListRecycleAll;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout _swiperefresh;

    @BindView(R.id.linearTitleSwipe)
    LinearLayout _linearTitleSwipe;

    @BindView(R.id.shortingFloatingButton)
    FloatingActionButton _sortFloatingButton;

    @BindView(R.id.etSearch)
    SearchView _etSearch;

    @BindView(R.id.titleList)
    TextView _titleList;

    @BindView(R.id.btnTambahTasklist)
    Button _btnTambahTasklist;

    private Boolean isNew ;
    private String assignedType ="allassigned", tc = "5.0";
    private ArrayList<TaskListDetailModel> taskListList;
    private TaskListActivityAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_activity);
        ButterKnife.bind(this);
        initiateApiData();
        prepare();
    }

    private void prepare(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycleAll.setLayoutManager(linearLayoutManager);
        taskListRecycleAll.setHasFixedSize(true);
        taskListRecycleAll.smoothScrollToPosition(10);
        _titleList.setText(getIntent().getStringExtra(ParameterKey.MENU_DESC));
        toolbar.inflateMenu(R.menu.menu_actionbar);
        setToolbar();
        if(getIntent().getStringExtra(ParameterKey.IS_ADD).equalsIgnoreCase("1"))
            _btnTambahTasklist.setVisibility(View.VISIBLE);

        if(getIntent() != null) {
            if(getIntent().getStringExtra(ParameterKey.ASSIGNED_TYPE) != null && getIntent().getStringExtra(ParameterKey.ASSIGNED_TYPE).equalsIgnoreCase("1")) {
                assignedType = "assigned";
                tc = getIntent().getStringExtra(ParameterKey.ASSIGNED_TC);
            }
            else if(getIntent().getStringExtra(ParameterKey.ASSIGNED_TYPE) != null &&getIntent().getStringExtra(ParameterKey.ASSIGNED_TYPE).equalsIgnoreCase("0")) {
                assignedType = "unassigned";
                tc = getIntent().getStringExtra(ParameterKey.ASSIGNED_TC);
            }
            else{
                assignedType = "allassigned";
                tc = "5.0";
            }
        }else{
            assignedType = "allassigned";
            tc = "5.0";
        }
        fillList(assignedType);

        _swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNew = null;
                fillList(assignedType);
            }
        });
    }

    protected void fillList(final String typeList){

        _swiperefresh.setRefreshing(true);
        if (!networkConnection.isNetworkConnected()) {
            _swiperefresh.setRefreshing(false);
            dialog(R.string.errorNoInternetConnection);

        } else {
            final TaskListJson.TasklistRequest request = new TaskListJson().new TasklistRequest();
            request.setUserid(userdata.select().getUserid());
            request.setType(typeList);
            request.setTc(tc);
            String token = userdata.select().getAccesstoken();
            Call<TaskListJson.TasklistCallback> call = endPoint.getInbox(token, request);
            call.enqueue(new Callback<TaskListJson.TasklistCallback>() {
                @Override
                public void onResponse(Call<TaskListJson.TasklistCallback> call, Response<TaskListJson.TasklistCallback> response) {
                    try{
                        if(response.isSuccessful()) {
                            _swiperefresh.setRefreshing(false);
                            if (response.body().getMessage().equalsIgnoreCase("Data not available.")) {
//                        dialogmaterial.dismiss();
                                Log.e("Data List", " Empty");
                                taskListList = new ArrayList<TaskListDetailModel>();
                                taskListAdapter = new TaskListActivityAdapter(getApplicationContext(), taskListList, typeList, new TaskListInterface() {
                                    @Override
                                    public void onListSelected(TaskListDetailModel list) {

                                    }
                                });
                                taskListAdapter.notifyDataSetChanged();
                                taskListRecycleAll.setAdapter(taskListAdapter);
//                            _linearTitleSwipe.setBackground(getResources().getDrawable(R.color.grey));
                            } else if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                                Toast.makeText(getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                        .show();
                                userdata.deleteAll();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            } else {
//                        dialogmaterial.dismiss();
                                taskListList = new ArrayList<TaskListDetailModel>();
                                for (TaskListJson.TasklistCallback.Data callbackList : response.body().getData()) {
                                    TaskListDetailModel detailModel = new TaskListDetailModel();
                                    detailModel.setIdNasabah(callbackList.getAp_regno().toUpperCase());
                                    detailModel.setCustomertype_id(callbackList.getCustomertype_id());
                                    detailModel.setCustomertype_desc(callbackList.getCustomertype_desc().toUpperCase());
                                    detailModel.setNamaNasabah(callbackList.getCustomername().toUpperCase());
                                    detailModel.setCustomerdocument_id(callbackList.getCustomerdocument_id().toUpperCase());
                                    detailModel.setCustomerdocument_type(callbackList.getCustomerdocument_type().toUpperCase());
                                    detailModel.setProduct_id(callbackList.getProduct_id().toUpperCase());
                                    detailModel.setProduct_desc(callbackList.getProduct_desc().toUpperCase());
                                    detailModel.setPlafon(callbackList.getPlafon().toUpperCase());
                                    detailModel.setTrack_id(callbackList.getTrack_id());
                                    detailModel.setTrack_name(callbackList.getTrack_name());
                                    detailModel.setFormCode(callbackList.getFormCode());

                                    detailModel.setIcon(callbackList.getIcon());
                                    detailModel.setLast_track_date(callbackList.getLast_track_date());
                                    if(!callbackList.getLast_track_date().equalsIgnoreCase("")) {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                        Date date = null;
                                        try {
                                            date = dateFormat.parse(callbackList.getLast_track_date());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        detailModel.setSort_date(date);
                                    }
                                    taskListList.add(detailModel);
                                }
                                // set up the RecyclerView

//                            Collections.sort(taskListList, Collections.reverseOrder());
//                            sortDate();
                                taskListAdapter = new TaskListActivityAdapter(getApplicationContext(), taskListList, typeList, new TaskListInterface() {
                                    @Override
                                    public void onListSelected(TaskListDetailModel list) {

                                    }
                                });
                                taskListAdapter.notifyDataSetChanged();
                                taskListRecycleAll.setAdapter(taskListAdapter);
                            }

                        }
                    }catch (Exception e){
                        _swiperefresh.setRefreshing(false);
                        dialogMessage(e.toString());
                    }
                }

                @Override
                public void onFailure(Call<TaskListJson.TasklistCallback> call, Throwable t) {
                    _swiperefresh.setRefreshing(false);
//                    dialogmaterial.dismiss();
                }
            });
        }
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
                        new AlertDialog.Builder(TaskListActivity.this).setTitle(R.string.companyName).setView(addView)
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
        Intent i = new Intent(TaskListActivity.this, MainActivityDashboard.class);
        i.putExtra("FLAG_SUBMIT", "1");
        startActivity(i);
    }

    @OnClick(R.id.btntutupTasklist) public void backToMenu(){
        Intent i = new Intent(TaskListActivity.this, MainActivityDashboard.class);
        i.putExtra("FLAG_SUBMIT", "1");
        startActivity(i);
    }

    @OnClick(R.id.btnTambahTasklist)
    public void gotoPengajuanAwal(){
        Intent intent = new Intent(TaskListActivity.this, FullEntry.class);
        //LIST WITH FILL
//        intent.putExtra("REGNO","");
//        intent.putExtra("TC","5.0");
//        intent.putExtra("TYPE","IDJ");

        intent.putExtra(ParameterKey.REGNO,"");
        intent.putExtra(ParameterKey.TC,tc);
        intent.putExtra(ParameterKey.TYPE,"SDE IND");
        intent.putExtra(ParameterKey.STATUS,"0");
        intent.putExtra(ParameterKey.NEW_DATA,"1");
        intent.putExtra("TITLE",getIntent().getStringExtra(ParameterKey.MENU_DESC));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.shortingFloatingButton)
    public void sort(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(TaskListActivity.this).create();
        LayoutInflater inflater = TaskListActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.sorting_popup, null);
        final Button _branchSaveButton = (Button) dialogView.findViewById(R.id.branchSaveButton);
        final Button _branchCancelButton = (Button) dialogView.findViewById(R.id.branchCancelButton);
        final RadioGroup _rbGroupSort = (RadioGroup) dialogView.findViewById(R.id.rbGroupSort);
        final RadioButton _rbTerbaru = (RadioButton) dialogView.findViewById(R.id.rbTerbaru);
        final RadioButton _rbTerlama= (RadioButton) dialogView.findViewById(R.id.rbTerlama);

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        if(isNew != null) {
            _rbTerbaru.setChecked(isNew ? true : false);
            _rbTerlama.setChecked(!isNew ? true : false);
        }
        _branchSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("VALUE "," RADIO BUTTON " + _rbGroupSort.getCheckedRadioButtonId());
                if(_rbTerbaru.isChecked() == true){
                    isNew = true;
                }else{
                    isNew = false;
                }


                ArrayList<TaskListDetailModel> arraylist = new ArrayList<>();
                for(TaskListDetailModel model : taskListList){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        TaskListDetailModel detailModel = new TaskListDetailModel();
                        detailModel.setNamaNasabah(model.getNamaNasabah().toUpperCase());
                        detailModel.setIdNasabah(model.getIdNasabah().toUpperCase());
                        detailModel.setTrack_id(model.getTrack_id());
                        detailModel.setCustomertype_id(model.getCustomertype_id());
                        detailModel.setFormCode(model.getFormCode());
                        detailModel.setIcon(model.getIcon());
                        detailModel.setLast_track_date(model.getLast_track_date());
                        Date date = dateFormat.parse(model.getLast_track_date());
                        detailModel.setSort_date(date);
                        arraylist.add(detailModel);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(!isNew)
                    Collections.sort(taskListList);
                else
                    Collections.sort(taskListList, Collections.reverseOrder());

//                sortDate();
                taskListAdapter = new TaskListActivityAdapter(TaskListActivity.this, taskListList, assignedType, new TaskListInterface() {
                    @Override
                    public void onListSelected(TaskListDetailModel list) {

                    }
                });
                taskListAdapter.notifyDataSetChanged();
                taskListRecycleAll.setAdapter(taskListAdapter);
                dialogBuilder.cancel();
            }
        });

        _branchCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.cancel();
            }
        });
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



