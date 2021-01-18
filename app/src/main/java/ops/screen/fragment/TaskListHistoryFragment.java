package ops.screen.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;

import base.data.DokumenOfflineData;
import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ResponseCallback;
import base.network.RetreiveHistoryListJson;

import base.sqlite.DataMenuModel;
import base.sqlite.FormData;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TasklistHistoryModel;
import base.sqlite.Userdata;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import user.login.LoginActivity;

public class TaskListHistoryFragment extends Fragment{


    @BindView(R.id.recycleMenuEntry)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout _swiperefresh;

    private SQLiteConfigKu config;
    private ArrayList<TasklistHistoryModel> taskListList;
    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    private Userdata userdata;
    private ArrayList<String> menulist;
    private ArrayList<DataMenuModel> dataModels;
    private String assignedType ="allassigned", tc = "5.0";
    private Dialog dialog;
    private Boolean isNew ;
    private FormData formData;
    protected Bundle bundle;
    protected TelephonyManager telephonyManager;
    private TaskListHistoryAdapter taskListHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.tasklist_history_fragment, container, false);
        ButterKnife.bind(this, view);

        initiateApiData();
//        getLastLocation();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        prepare();
        retreiveDatanewList();
        return view;
    }

    protected void initiateApiData(){
        config = new SQLiteConfigKu(getActivity().getApplicationContext());
        networkConnection = new NetworkConnection(getActivity().getApplicationContext());
        userdata = new Userdata(getActivity().getApplicationContext());
        formData = new FormData(getActivity().getApplicationContext());
        bundle = new Bundle();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        httpclient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                return chain.proceed(request);
            }
        });

        endPoint = retrofit.create(EndPoint.class);

        telephonyManager = (TelephonyManager) getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    }

    protected void transparentStatusbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            w.setStatusBarColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.greytransparent));
        }
    }

    private void prepare(){
        Log.e("MASUK ","MASUK PREPARE ");
        _swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNew = null;
                Log.e("MASUK ","MASUK REFRESH ");
                retreiveDatanewList();
            }
        });
    }

    protected void dialog(int rString) {
        new MaterialDialog.Builder(getActivity())
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
                .show();
    }
    protected void dialogMessage(String rString) {
        new MaterialDialog.Builder(getActivity())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }
    private void removeUserData(String message){
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        formData.deleteAll("FORM_SURVEY");
        formData.deleteAll("FORM_SURVEY_DATA");
        formData.deleteAll("FORM_SURVEY_REFERENCE");
        formData.deleteAll();
        userdata.deleteAll();
        getActivity().finish();
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public void retreiveDatanewList(){

        _swiperefresh.setRefreshing(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            _swiperefresh.setRefreshing(false);
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final RetreiveHistoryListJson.RetreiveRequest request = new RetreiveHistoryListJson().new RetreiveRequest();
            request.setUserid(userdata.select().getUserid());
            request.setTc("ALL");

            Call<RetreiveHistoryListJson.RetreiveCallback> callBack = endPoint.getDataHistory(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<RetreiveHistoryListJson.RetreiveCallback>() {
                @Override
                public void onResponse(Call<RetreiveHistoryListJson.RetreiveCallback> call, Response<RetreiveHistoryListJson.RetreiveCallback> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        _swiperefresh.setRefreshing(false);
                        if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.OK)) {
                            if(response.body().getData() != null) {
                                taskListList = new ArrayList<>();
                                for (TasklistHistoryModel datamodel : response.body().getData()) {
                                    TasklistHistoryModel model = new TasklistHistoryModel();
                                    model.setUserid(datamodel.getUserid());
                                    model.setCustname(datamodel.getCustname());
                                    model.setAppnumber(datamodel.getAppnumber());
                                    model.setTrack_code(datamodel.getTrack_code());
                                    model.setTrack_desc(datamodel.getTrack_desc());
                                    model.setFinish_track(datamodel.getFinish_track());
                                    taskListList.add(model);
                                }
                                setAdapter();
                            }
                        }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.INVALID_LOGIN)){
                            removeUserData(response.body().getMessage());
                        }else{
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
                    _swiperefresh.setRefreshing(false);
                    dialogMessage("Error " + t);
                }
            });
        }

    }

    private  void setAdapter(){
        dialog.dismiss();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
        taskListHistoryAdapter = new TaskListHistoryAdapter(taskListList, getActivity().getApplicationContext());
        taskListHistoryAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(taskListHistoryAdapter);

    }


}
