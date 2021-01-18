package ops.screen.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ResponseCallback;
import base.network.Slider;
import base.network.TaskListJson;
import base.sqlite.DataMenuModel;
import base.sqlite.FormData;
import base.sqlite.TaskListDetailModel;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import base.utils.ParameterKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import ops.screen.TaskListActivity;
import ops.screen.TaskListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import user.login.LoginActivity;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.taskListRecycle)
    RecyclerView recyclerView;
    @BindView(R.id.txtSeeAll)
    TextView txtSeeAll;
//    @BindView(R.id.linearmenu1)
//    LinearLayout linearmenu1;
//    @BindView(R.id.linearmenu2)
//    LinearLayout linearmenu2;
//    @BindView(R.id.linearmenu3)
//    LinearLayout linearmenu3;
//    @BindView(R.id.linearmenu4)
//    LinearLayout linearmenu4;
    @BindView(R.id.dash1)
    LinearLayout _dashboard;
    @BindView(R.id.layoutLatestTask)
    LinearLayout _layoutLatestTask;
    @BindView(R.id.linearRecycle)
    LinearLayout _linearRecycle;

    @BindView(R.id.successLayout)
    RelativeLayout successLayout;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout _swiperefresh;

    TaskListAdapter taskListAdapter;

    private TaskListFragment taskListFragment;
    private SQLiteConfigKu sqLiteConfigKu;
    private SliderSQL slidersql;
    private Userdata userdata;
    private ArrayList<TaskListDetailModel> taskListList;
    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    private Dialog dialog;
    private ArrayList<String> menulist;
    private ArrayList<DataMenuModel> dataModels;
    public String titleSection = "DATA PENGAJUAN";

    private List<LinearLayout> linearLayouts = new ArrayList<>();
    private FormData formData;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard_activity, container, false);
        ButterKnife.bind(this, view);
//        linearmenu1.setVisibility(View.GONE);
//        linearmenu2.setVisibility(View.GONE);
//        linearmenu3.setVisibility(View.GONE);
//        linearmenu4.setVisibility(View.GONE);
        sliderLayout.setVisibility(View.GONE);
        initialisation();
        prepare();
        getMenuAccess();

//        fillList();

        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {
            dialogPermission();
        }else {
            Integer slidesize = slidersql.count();
            Log.e("SLIDER SIZE " , " + "+ slidesize);
            if (slidesize > 0) {
                sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));

                for (int i = 1; i <= slidesize; i++) {
                    Slider temp = slidersql.select(i);

                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView
                            .description(temp.getName())
                            .image(temp.getImage())
                            .error(R.drawable.defaultslide)
                            .empty(R.drawable.defaultslide)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(HomeFragment.this);

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra", temp.getLink());

                    textSliderView.getBundle()
                            .putString("package", temp.getPackage_name());

                    sliderLayout.addSlider(textSliderView);

                }

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(6000);
                sliderLayout.addOnPageChangeListener(HomeFragment.this);
            }
        }


        return view;
    }


    private void prepare(){
        _swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               callTopAssign();
            }
        });
    }


    private void callTopAssign() {
        _swiperefresh.setRefreshing(true);
        if (!networkConnection.isNetworkConnected()) {
            _swiperefresh.setRefreshing(false);
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final TaskListJson.TasklistRequest request = new TaskListJson().new TasklistRequest();
            request.setUserid(userdata.select().getUserid());
            request.setType("topassigned");
            String token = userdata.select().getAccesstoken();
            Call<TaskListJson.TasklistCallback> call = endPoint.getInbox(token, request);
            call.enqueue(new Callback<TaskListJson.TasklistCallback>() {
                @Override
                public void onResponse(Call<TaskListJson.TasklistCallback> call, Response<TaskListJson.TasklistCallback> response) {
                    try {
                        dialog.dismiss();
                        _swiperefresh.setRefreshing(false);
                        if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                    .show();
                            userdata.deleteAll();
                            dialogMessage(response.body().getMessage());
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } else if (response.body().getMessage().equalsIgnoreCase("Data not available.")) {
                            Log.e("Data List", " Empty");
                            _layoutLatestTask.setVisibility(View.GONE);
                            _linearRecycle.setVisibility(View.GONE);
//                            dialogMessage(response.body().getMessage());
                        } else {
                            taskListList = new ArrayList<TaskListDetailModel>();
                            if(response.body().getData() != null ) {
                                for (TaskListJson.TasklistCallback.Data datamodel : response.body().getData()) {
                                    TaskListDetailModel detailModel = new TaskListDetailModel();
                                    detailModel.setIdNasabah(datamodel.getAp_regno().toUpperCase());
                                    detailModel.setCustomertype_id(datamodel.getCustomertype_id());
                                    detailModel.setNamaNasabah(datamodel.getCustomername().toUpperCase());
                                    detailModel.setCustomerdocument_id(datamodel.getCustomerdocument_id());
                                    detailModel.setCustomerdocument_type(datamodel.getCustomerdocument_type());
                                    detailModel.setProduct_id(datamodel.getProduct_id());
                                    detailModel.setProduct_desc(datamodel.getProduct_desc());
                                    detailModel.setPlafon(datamodel.getPlafon());
                                    detailModel.setTrack_id(datamodel.getTrack_id());
                                    detailModel.setTrack_name(datamodel.getTrack_name());
                                    detailModel.setFormCode(datamodel.getFormCode());
                                    detailModel.setIcon(datamodel.getIcon());
                                    detailModel.setLast_track_date(datamodel.getLast_track_date());
                                    titleSection = String.valueOf(datamodel.getTrack_name());
                                    taskListList.add(detailModel);
                                }
                                // set up the RecyclerView
                                taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, "allassigned", new TaskListInterface() {
                                    @Override
                                    public void onListSelected(TaskListDetailModel list) {

                                    }
                                });
                                recyclerView.setAdapter(taskListAdapter);
                            }
                        }
                    }catch (Exception e){
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                }

                @Override
                public void onFailure(Call<TaskListJson.TasklistCallback> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    protected void getMenuAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();
            request.setUserid(userdata.select().getUserid());
            Call<LoginJson.getmenuCallback> callback = endPoint.getMenuAccess(userdata.select().getAccesstoken(),request);
            callback.enqueue(new Callback<LoginJson.getmenuCallback>() {
                @Override
                public void onResponse(Call<LoginJson.getmenuCallback> call, Response<LoginJson.getmenuCallback> response) {
//                    try {
                        if (response.isSuccessful()) {
                            dataModels = new ArrayList<>();
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                if(response.body().getData() != null) {
                                    for (DataMenuModel callbackdata : response.body().getData()) {
                                        DataMenuModel dataModel = new DataMenuModel();
                                        dataModel.setUserid(callbackdata.getUserid());
                                        dataModel.setSu_fullname(callbackdata.getSu_fullname());
                                        dataModel.setGroupid(callbackdata.getGroupid());
                                        dataModel.setSg_grpname(callbackdata.getSg_grpname());
                                        dataModel.setBranchid(callbackdata.getBranchid());
                                        dataModel.setBranchname(callbackdata.getBranchname());
                                        dataModel.setTypeid(callbackdata.getTypeid());
                                        dataModel.setMenuid(callbackdata.getMenuid());
                                        dataModel.setMenudesc(callbackdata.getMenudesc());
                                        dataModel.setAssigned(callbackdata.getAssigned());
                                        dataModel.setTrack(callbackdata.getTrack());
                                        dataModel.setJumlahaplikasi(callbackdata.getJumlahaplikasi());
                                        dataModel.setMenutrack(callbackdata.getMenutrack());
                                        dataModel.setIcon(callbackdata.getIcon());
                                        dataModel.setIs_add(callbackdata.getIs_add());
                                        dataModels.add(dataModel);

                                        String data = callbackdata.getMenudesc();
                                        menulist.add(data);
                                    }
                                }
                                showMenu();
                            }else if (response.body().getStatus().equalsIgnoreCase(ResponseCallback.FAILED)) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                            }else if(response.body().getStatus().equalsIgnoreCase(ResponseCallback.INVALID_LOGIN)){
                                dialog.dismiss();
                                removeUserData(response.body().getMessage().toString());
                            }else{
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        } else {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
//                    }catch (Exception e){
//                        dialog.dismiss();
//                        dialogMessage(e.toString());
//                    }
                }
                @Override
                public void onFailure(Call<LoginJson.getmenuCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
    }

    private void showMenu() {

        Log.e("DATA MODEL COUNT ", "" + dataModels.size());
       /* for(DataMenuModel dataMenuModel : dataModels) {

            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setTag(dataMenuModel.getMenuid());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams linearparams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            linearparams.setMargins(2, 0, 0, 0);
            linearparams.weight= 1f;
            linearLayout.setLayoutParams(linearparams);

            TextView textBadge = new TextView(getActivity());
            textBadge.setBackground(getResources().getDrawable(R.drawable.badge));
            textBadge.setGravity(Gravity.CENTER);
            textBadge.setPadding(2,2,2,2);
            textBadge.setText(dataMenuModel.getJumlahaplikasi());
            textBadge.setTextSize(10);
            textBadge.setTextColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    40,
                    40
            );
            params.gravity = Gravity.CENTER;
            params.setMargins(0, 3, -20, -20);
            textBadge.setLayoutParams(params);
            Log.e("DATA MODEL COUNT ", "JUMLAH APP " + Integer.valueOf(dataMenuModel.getJumlahaplikasi()));
            int jumlahApp = Integer.valueOf(dataMenuModel.getJumlahaplikasi());
            Log.e("DATA MODEL COUNT ", "JUMLAH APP AFTER " + jumlahApp);
            Log.e("DATA MODEL COUNT ", "JUMLAH APP IS ADD " + dataMenuModel.getIs_add());
            if(dataMenuModel.getIs_add().equalsIgnoreCase("1") || jumlahApp <= 0){
                textBadge.setVisibility(View.INVISIBLE);
            }
//            textBadge.setLayoutParams(new LinearLayout.LayoutParams(25,20, Gravity.RIGHT|Gravity.END|Gravity.TOP));


            ImageView imageview = new ImageView(getActivity());
            if(dataMenuModel.getIcon().equalsIgnoreCase("ic_suitcase")){
                imageview.setImageResource(R.drawable.ic_suitcase);
            }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_building")){
                imageview.setImageResource(R.drawable.ic_building);
            }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_document")){
                imageview.setImageResource(R.drawable.ic_document);
            }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_newdoc")){
                imageview.setImageResource(R.drawable.ic_newdoc);
            }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_information")){
                imageview.setImageResource(R.drawable.ic_information);
            }else{
                imageview.setImageResource(R.drawable.ic_information);
            }
            imageview.setLayoutParams(new LinearLayout.LayoutParams(100 , 100,Gravity.CENTER));


            TextView textview = new TextView(getActivity());
            textview.setText(dataMenuModel.getMenudesc());
            textview.setGravity(Gravity.CENTER);
            textview.setTextSize(14);

            textview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

            linearLayout.addView(textBadge);
            linearLayout.addView(imageview);
            linearLayout.addView(textview);
            linearLayouts.add(linearLayout);

            // Add Button to LinearLayout
            if (_dashboard != null) {
                _dashboard.addView(linearLayout);
            }
        }
        if(dataModels.size()==0){
            successLayout.setVisibility(View.GONE);
        }

        for(final LinearLayout linearLayout : linearLayouts){
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("LINEAR ID ", " " + linearLayout.getTag());
                    for(DataMenuModel datamenu : dataModels){
                        if(linearLayout.getTag().toString().equalsIgnoreCase(datamenu.getMenuid())){
                            Log.e("LINEAR ID ", " DALAM " + linearLayout.getTag());
                            if(datamenu.getIs_add().equalsIgnoreCase("0")) {
                                gototask(datamenu.getMenutrack(), datamenu.getAssigned());
                            }else{
                                gotolist(datamenu.getMenutrack());
                            }
                        }
                    }
                }
            });
        }
*/
        int k = 0;

//        if(dataModels.size() >= 3) {
        Log.e("DATA MODEL COUNT ", "HAA SATU : "+ dataModels.size() % 3);
        int mod = dataModels.size() % 3;
        for (int index = 0 ; index <= dataModels.size(); index++) {
            if ((index - mod) % 3 == 0) {

                Log.e("DATA MODEL COUNT ", " HAA DUA : " + index );
                LinearLayout linearLayout = new LinearLayout(getActivity());
//                linearLayout.setTag(dataMenuModel.getMenuid());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(5, 5, 5, 5);
                linearLayout.setGravity(Gravity.CENTER);
//                linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                LinearLayout.LayoutParams linearparams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                linearparams.setMargins(0, 0, 0, 0);
                linearparams.weight = 3f;
                linearLayout.setLayoutParams(linearparams);

                for (int j = k; j < index; j++) {
                    LinearLayout linearLayoutmenu = new LinearLayout(getActivity());
                    linearLayoutmenu.setTag(dataModels.get(j).getMenuid());
                    linearLayoutmenu.setOrientation(LinearLayout.VERTICAL);
                    linearLayoutmenu.setPadding(5, 5, 5, 5);
                    linearLayoutmenu.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayoutmenu.setBackground(getResources().getDrawable(R.drawable.green_outline));
                    }
                    LinearLayout.LayoutParams linearmenuparams = new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    linearmenuparams.setMargins(5, 5, 5, 5);
                    linearmenuparams.weight = 1f;
                    linearLayoutmenu.setLayoutParams(linearmenuparams);


                    TextView textBadge = new TextView(getActivity());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        textBadge.setBackground(getResources().getDrawable(R.drawable.badge));
                    }
                    textBadge.setGravity(Gravity.CENTER);
                    textBadge.setPadding(2, 2, 2, 2);
                    textBadge.setText(dataModels.get(j).getJumlahaplikasi());
                    textBadge.setTextSize(10);
                    textBadge.setTextColor(getResources().getColor(R.color.white));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            40,
                            40
                    );
                    params.gravity = Gravity.CENTER;
                    params.setMargins(0, 3, -20, -20);
                    textBadge.setLayoutParams(params);
                    Log.e("DATA MODEL COUNT ", "JUMLAH APP " + Integer.valueOf(dataModels.get(j).getJumlahaplikasi()));
                    int jumlahApp = Integer.valueOf(dataModels.get(j).getJumlahaplikasi());
                    Log.e("DATA MODEL COUNT ", "JUMLAH APP AFTER " + jumlahApp);
                    Log.e("DATA MODEL COUNT ", "JUMLAH APP IS ADD " + dataModels.get(j).getIs_add());
                    if (dataModels.get(j).getIs_add().equalsIgnoreCase("1") || jumlahApp <= 0) {
                        textBadge.setVisibility(View.INVISIBLE);
                    }
                    //            textBadge.setLayoutParams(new LinearLayout.LayoutParams(25,20, Gravity.RIGHT|Gravity.END|Gravity.TOP));


                    ImageView imageview = new ImageView(getActivity());
                    if (dataModels.get(j).getIcon().equalsIgnoreCase("ic_suitcase")) {
                        imageview.setImageResource(R.drawable.ic_suitcase);
                    } else if (dataModels.get(j).getIcon().equalsIgnoreCase("ic_building")) {
                        imageview.setImageResource(R.drawable.ic_building);
                    } else if (dataModels.get(j).getIcon().equalsIgnoreCase("ic_document")) {
                        imageview.setImageResource(R.drawable.ic_document);
                    } else if (dataModels.get(j).getIcon().equalsIgnoreCase("ic_newdoc")) {
                        imageview.setImageResource(R.drawable.ic_newdoc);
                    } else if (dataModels.get(j).getIcon().equalsIgnoreCase("ic_information")) {
                        imageview.setImageResource(R.drawable.ic_information);
                    } else {
                        imageview.setImageResource(R.drawable.ic_information);
                    }
                    imageview.setLayoutParams(new LinearLayout.LayoutParams(100, 100, Gravity.CENTER));


                    TextView textview = new TextView(getActivity());
                    textview.setText(dataModels.get(j).getMenudesc());
                    textview.setGravity(Gravity.CENTER);
                    textview.setTextSize(14);

                    textview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));


                    linearLayoutmenu.addView(textBadge);
                    linearLayoutmenu.addView(imageview);
                    linearLayoutmenu.addView(textview);
                    linearLayout.addView(linearLayoutmenu);
                    linearLayouts.add(linearLayoutmenu);
                    k = index;
                }
                _dashboard.addView(linearLayout);
            }else{
                Log.e("DATA MODEL COUNT ", "HAA LALA : "+ index);
            }
        }
//        }else{
//            for(DataMenuModel dataMenuModel : dataModels) {
//                LinearLayout linearLayout = new LinearLayout(getActivity());
//                linearLayout.setTag(dataMenuModel.getMenuid());
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                linearLayout.setPadding(5,5,5,5);
//                linearLayout.setGravity(Gravity.CENTER);
//                linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
//                LinearLayout.LayoutParams linearparams = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                );
//                linearparams.setMargins(2, 0, 0, 0);
//                linearparams.weight= 1f;
//                linearLayout.setLayoutParams(linearparams);
//
//                TextView textBadge = new TextView(getActivity());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    textBadge.setBackground(getResources().getDrawable(R.drawable.badge));
//                }
//                textBadge.setGravity(Gravity.CENTER);
//                textBadge.setPadding(2,2,2,2);
//                textBadge.setText(dataMenuModel.getJumlahaplikasi());
//                textBadge.setTextSize(10);
//                textBadge.setTextColor(getResources().getColor(R.color.white));
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        40,
//                        40
//                );
//                params.gravity = Gravity.CENTER;
//                params.setMargins(0, 3, -20, -20);
//                textBadge.setLayoutParams(params);
//                Log.e("DATA MODEL COUNT ", "JUMLAH APP " + Integer.valueOf(dataMenuModel.getJumlahaplikasi()));
//                int jumlahApp = Integer.valueOf(dataMenuModel.getJumlahaplikasi());
//                Log.e("DATA MODEL COUNT ", "JUMLAH APP AFTER " + jumlahApp);
//                Log.e("DATA MODEL COUNT ", "JUMLAH APP IS ADD " + dataMenuModel.getIs_add());
//                if(dataMenuModel.getIs_add().equalsIgnoreCase("1") || jumlahApp <= 0){
//                    textBadge.setVisibility(View.INVISIBLE);
//                }
//    //            textBadge.setLayoutParams(new LinearLayout.LayoutParams(25,20, Gravity.RIGHT|Gravity.END|Gravity.TOP));
//
//
//                ImageView imageview = new ImageView(getActivity());
//                if(dataMenuModel.getIcon().equalsIgnoreCase("ic_suitcase")){
//                    imageview.setImageResource(R.drawable.ic_suitcase);
//                }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_building")){
//                    imageview.setImageResource(R.drawable.ic_building);
//                }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_document")){
//                    imageview.setImageResource(R.drawable.ic_document);
//                }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_newdoc")){
//                    imageview.setImageResource(R.drawable.ic_newdoc);
//                }else if(dataMenuModel.getIcon().equalsIgnoreCase("ic_information")){
//                    imageview.setImageResource(R.drawable.ic_information);
//                }else{
//                    imageview.setImageResource(R.drawable.ic_information);
//                }
//                imageview.setLayoutParams(new LinearLayout.LayoutParams(100 , 100,Gravity.CENTER));
//
//
//                TextView textview = new TextView(getActivity());
//                textview.setText(dataMenuModel.getMenudesc());
//                textview.setGravity(Gravity.CENTER);
//                textview.setTextSize(14);
//
//                textview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//
//                linearLayout.addView(textBadge);
//                linearLayout.addView(imageview);
//                linearLayout.addView(textview);
//                linearLayouts.add(linearLayout);
//
//                // Add Button to LinearLayout
//                if (_dashboard != null) {
//                    _dashboard.addView(linearLayout);
//                }
//            }
//            if(dataModels.size()==0){
//                successLayout.setVisibility(View.GONE);
//            }
//        }



        for(final LinearLayout linearLayout : linearLayouts){
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("LINEAR ID ", " " + linearLayout.getTag());
                    for(DataMenuModel datamenu : dataModels){
                        if(linearLayout.getTag().toString().equalsIgnoreCase(datamenu.getMenuid())){
                            Log.e("LINEAR ID ", " DALAM " + linearLayout.getTag());
//                            if(datamenu.getIs_add().equalsIgnoreCase("0")) {
                                gototask(datamenu.getMenutrack(), datamenu.getAssigned(),datamenu.getMenudesc(), datamenu.getIs_add());
//                            }else{
//                                gotolist(datamenu.getMenutrack());
//                            }
                        }
                    }
                }
            });
        }
        callTopAssign();
    }

    private void initialisation() {
        userdata = new Userdata(getActivity().getApplicationContext());
        slidersql = new SliderSQL(getActivity().getApplicationContext());
        formData = new FormData(getActivity().getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        sqLiteConfigKu = new SQLiteConfigKu(getActivity().getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofit.create(EndPoint.class);

        networkConnection = new NetworkConnection(getActivity());
        menulist = new ArrayList<>();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        String link = slider.getBundle().getString("extra");
        String package_name = slider.getBundle().getString("package");

        if (package_name.length() > 1) {
            if (isPackageExisted(package_name)) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(package_name);
                startActivity(launchIntent);//null pointer check in case package name was not found
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
                startActivity(browserIntent);
            }
        } else if (!link.equalsIgnoreCase("") && link.length() > 10) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.txtSeeAll) public void seeAll(){
        taskListFragment = new TaskListFragment();
        loadFragment(taskListFragment);
    }


    private void loadFragment(Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean isPackageExisted(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getActivity().getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    protected void dialogPermission() {
        new MaterialDialog.Builder(getActivity())
                .content("Please allow all permission on your app setting, thank you")
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .cancelable(false)
                .show();
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
                        getMenuAccess();
                    }
                })
                .cancelable(true)
                .show();
    }
    protected void dialogMessage(String rString) {
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
  /*  protected void fillList(){

        taskListList = new ArrayList<TaskListDetailModel>();

        for(int i =0;i<=2;i++) {
            TaskListDetailModel detailModel = new TaskListDetailModel();
            detailModel.setNamaNasabah("Agus_" + i);
            Log.e("Agus ","Id Agus_" + i );
            detailModel.setIdNasabah(String.valueOf(i));
            taskListList.add(detailModel);
        }

        // set up the RecyclerView
        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(),taskListList);
        recyclerView.setAdapter(taskListAdapter);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_application:
//                dialog(R.string.addPinNumber);
                return true;
//                break;
            // action with ID action_settings was selected
            case R.id.action_logout:
                Toast.makeText(getActivity(), "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                return true;
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @OnClick(R.id.linearmenu1)public void linearclick1(){
//        gotolist("1.0");
//    }

    public void gotolist(String tc){

        Intent intent = new Intent(getActivity(), FullEntry.class);
        //LIST WITH FILL
//        intent.putExtra("REGNO","");
//        intent.putExtra("TC","5.0");
//        intent.putExtra("TYPE","IDJ");

        intent.putExtra(ParameterKey.REGNO,"");
        intent.putExtra(ParameterKey.TC,tc);
        intent.putExtra(ParameterKey.TYPE,"SDE IND");
        intent.putExtra(ParameterKey.STATUS,"0");
        intent.putExtra(ParameterKey.NEW_DATA,"1");
        intent.putExtra("TITLE",titleSection);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gototask(String tc,String assignedType,String desc, String isAdd){

        Intent i = new Intent(getActivity(), TaskListActivity.class);
        i.putExtra("FLAG_SUBMIT","1");
        i.putExtra(ParameterKey.ASSIGNED_TYPE,assignedType);
        i.putExtra(ParameterKey.ASSIGNED_TC,tc);
        i.putExtra(ParameterKey.MENU_DESC,desc);
        i.putExtra(ParameterKey.IS_ADD, isAdd );
        i.putExtra(ParameterKey.SCREEN_TASK, "1");
        startActivity(i);
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

}
