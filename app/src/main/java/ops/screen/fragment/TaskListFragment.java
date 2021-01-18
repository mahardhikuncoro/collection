package ops.screen.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.Slider;
import base.network.TaskListJson;
import base.sqlite.DataMenuModel;
import base.sqlite.FormData;
import base.sqlite.NewsModel;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import base.sqlite.TaskListDetailModel;
import base.sqlite.Userdata;
import base.widget.TextSliderViewCustom;
import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.TaskListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import user.login.LoginActivity;

public class TaskListFragment extends Fragment implements TaskListInterface, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    @BindView(R.id.taskListRecycleAll)
    RecyclerView taskListRecycleAll;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout _swiperefresh;

    @BindView(R.id.linearTitleSwipe)
    LinearLayout _linearTitleSwipe;

    @BindView(R.id.layoutSlider)
    LinearLayout _layoutSlider;

//    SliderLayout sliderLayout = (SliderLayout) rootView.findViewById(R.id.banner_slider);
//    PagerIndicator pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.banner_slider_indicator);
//    DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

    @BindView(R.id.shortingFloatingButton)
    FloatingActionButton _sortFloatingButton;

    @BindView(R.id.etSearch)
    SearchView _etSearch;

    @BindView(R.id.slider)
    SliderLayout sliderLayout;

    @BindView(R.id.banner_slider_indicator)
    PagerIndicator pagerIndicator;

    @BindView(R.id.viewPagerNews)
    ViewPager viewPager ;

    @BindView(R.id.viewPagerArrowIndicator)
    ViewPagerArrowIndicator viewPagerArrowIndicator;


    private SQLiteConfigKu config;
    private ArrayList<TaskListDetailModel> taskListList;
    private TaskListAdapter taskListAdapter;
    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    public Dialog dialogmaterial;
    private Userdata userdata;
    private ArrayList<String> menulist;
    private ArrayList<NewsModel> news;
    private ArrayList<DataMenuModel> dataModels;
    private String assignedType ="allassigned", tc = "5.0";
    private Dialog dialog;
    private Boolean isNew ;
    private FormData formData;
    private SliderSQL slidersql;
    private List<LinearLayout> linearLayouts = new ArrayList<>();

    private FragmentActivity myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.tasklist_fragment, container, false);
        ButterKnife.bind(this, view);
        initialisation();
        prepare();
        getMenuAccess();
//        showMenu();
//        searchList();

        loadSlider();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext =(FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void loadSlider() {

        Integer slidesize = slidersql.count();
        Log.e("SLIDER SIZE " , " + "+ slidesize);
        if (slidesize > 0) {
            sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));

            for (int i = 1; i <= slidesize; i++) {
                Slider temp = slidersql.select(i);

                TextSliderViewCustom textSliderView = new TextSliderViewCustom(getActivity());
                textSliderView
                        .description(temp.getName())
                        .image(temp.getImage())
                        .error(R.drawable.defaultslide)
                        .empty(R.drawable.defaultslide)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(TaskListFragment.this);


                textSliderView.setOnImageLoadListener(new BaseSliderView.ImageLoadListener() {
                    @Override
                    public void onStart(BaseSliderView target) {
                    }

                    @Override
                    public void onEnd(boolean result, BaseSliderView target) {
                    }
                });
                textSliderView.bundle(new Bundle());

                textSliderView.getBundle()
                        .putString("extra", "");

                textSliderView.getBundle()
                        .putString("package", temp.getPackage_name());

                sliderLayout.addSlider(textSliderView);

            }


            sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(6000);
            sliderLayout.setCustomIndicator(pagerIndicator);
            sliderLayout.addOnPageChangeListener(TaskListFragment.this);




        }
    }

    private void prepare(){
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
//            dialogmaterial.dismiss();
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
                @SuppressLint("RestrictedApi")
                @Override
                public void onResponse(Call<TaskListJson.TasklistCallback> call, Response<TaskListJson.TasklistCallback> response) {
//                    try{
                        if(response.isSuccessful()) {
                            _swiperefresh.setRefreshing(false);
                            if (response.body().getMessage().equalsIgnoreCase("Data not available.")) {
//                        dialogmaterial.dismiss();
                                Log.e("Data List", " Empty");
                                taskListList = new ArrayList<TaskListDetailModel>();
                                taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, typeList, new TaskListInterface() {
                                    @Override
                                    public void onListSelected(TaskListDetailModel list) {

                                    }
                                });
                                taskListAdapter.notifyDataSetChanged();
                                taskListRecycleAll.setAdapter(taskListAdapter);
//                            _linearTitleSwipe.setBackground(getResources().getDrawable(R.color.grey));
                            } else if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                                Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                        .show();
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity(), LoginActivity.class));

                            } else {
//                        dialogmaterial.dismiss();
                                taskListList = new ArrayList<TaskListDetailModel>();
                                if (response.body().getData() != null){
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
                                        if (!callbackList.getLast_track_date().equalsIgnoreCase("")) {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                            Date date = null;
                                            try {
                                                date = dateFormat.parse(callbackList.getLast_track_date());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            detailModel.setSort_date(date);
                                        } else {
                                            _sortFloatingButton.setVisibility(View.GONE);
                                        }
                                        taskListList.add(detailModel);
                                    }
                                }
                                // set up the RecyclerView

//                            Collections.sort(taskListList, Collections.reverseOrder());
//                            sortDate();
                                taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, typeList, new TaskListInterface() {
                                    @Override
                                    public void onListSelected(TaskListDetailModel list) {

                                    }
                                });
                                taskListAdapter.notifyDataSetChanged();
                                taskListRecycleAll.setAdapter(taskListAdapter);
                            }

                        }
//                    }catch (Exception e){
//                        _swiperefresh.setRefreshing(false);
//                        dialogMessage(e.toString());
//                    }
                }

                @Override
                public void onFailure(Call<TaskListJson.TasklistCallback> call, Throwable t) {
                    _swiperefresh.setRefreshing(false);
//                    dialogmaterial.dismiss();
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    private void initialisation() {

//        Log.e("HAAA ","HOO " + getArguments().getString("ASSIGNED_TYPE"));
        _sortFloatingButton.setVisibility(View.GONE);

        formData = new FormData(getActivity().getApplicationContext());
        if(getArguments()!= null) {
            if(getArguments().getString("ASSIGNED_TYPE") != null && getArguments().getString("ASSIGNED_TYPE").equalsIgnoreCase("1")) {
                assignedType = "assigned";
                tc = getArguments().getString("ASSIGNED_TC");
            }
            else if(getArguments().getString("ASSIGNED_TYPE") != null && getArguments().getString("ASSIGNED_TYPE").equalsIgnoreCase("0")) {
                assignedType = "unassigned";
                tc = getArguments().getString("ASSIGNED_TC");
            }
            else{
                assignedType = "allassigned";
                tc = "5.0";
            }
        }else{
            assignedType = "allassigned";
            tc = "5.0";
        }
        userdata = new Userdata(getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycleAll.setLayoutManager(linearLayoutManager);
        taskListRecycleAll.setHasFixedSize(true);
        taskListRecycleAll.smoothScrollToPosition(10);

        config = new SQLiteConfigKu(getActivity().getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        menulist = new ArrayList<>();
        endPoint = retrofit.create(EndPoint.class);
        networkConnection = new NetworkConnection(getActivity());
        slidersql = new SliderSQL(getActivity().getApplicationContext());

        ((View)viewPager.getParent()).requestLayout();
    }

    protected void getMenuAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialogmaterial = builder.create();
        dialogmaterial.show();
        if (!networkConnection.isNetworkConnected()) {
            dialogmaterial.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();
            request.setUserid(userdata.select().getUserid());
            Call<LoginJson.getmenuCallback> callback = endPoint.getMenuAccess(userdata.select().getAccesstoken(),request);
            callback.enqueue(new Callback<LoginJson.getmenuCallback>() {
                @Override
                public void onResponse(Call<LoginJson.getmenuCallback> call, Response<LoginJson.getmenuCallback> response) {
                    try{
                        if(response.isSuccessful()) {
                            dataModels = new ArrayList<>();
                            news = new ArrayList<>();
                            if(response.body().getStatus().equalsIgnoreCase("1")){
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
                                        dataModel.setIs_add(callbackdata.getIs_add());
                                        dataModel.setJumlahaplikasi(callbackdata.getJumlahaplikasi());
                                        dataModel.setMenutrack(callbackdata.getMenutrack());
                                        dataModel.setIcon(callbackdata.getIcon());
                                        dataModels.add(dataModel);

                                        String data = callbackdata.getMenudesc();
                                        menulist.add(data);
                                    }
                                }

                                if(response.body().getNews() != null) {
                                    for (NewsModel newsModel : response.body().getNews()) {
                                        NewsModel newsmod = new NewsModel();
                                        newsmod.setNewsId(newsModel.getNewsId());
                                        newsmod.setNewsDesc(newsModel.getNewsDesc());
                                        newsmod.setActive(newsModel.getActive());
                                        news.add(newsmod);
                                    }
                                }
                                showMenu();
                            }else if(response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_LONG)
                                        .show();
                            }else if(response.body().getStatus().equalsIgnoreCase("0")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                            }else if(response.body().getStatus().equalsIgnoreCase("100")){
                                removeUserData(response.body().getMessage().toString());
                            }else{
                                dialogMessage(response.body().getMessage());
                            }
                            dialogmaterial.dismiss();

                        }else{
                            dialogmaterial.dismiss();
//                            dialogMessage(response.body().getMessage());
                        }
                    }catch (Exception e){
                        dialogmaterial.dismiss();
                        dialogMessage(e.toString());
                    }

                }
                @Override
                public void onFailure(Call<LoginJson.getmenuCallback> call, Throwable t) {
                    dialogmaterial.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
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
    private void showMenu() {
        fillList(assignedType);
        showNews(news);
    }


    @OnClick(R.id.shortingFloatingButton)
    public void sort(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
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


//                ArrayList<TaskListDetailModel> arraylist = new ArrayList<>();
//                for(TaskListDetailModel model : taskListList){
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                    try {
//                        TaskListDetailModel detailModel = new TaskListDetailModel();
//                        detailModel.setNamaNasabah(model.getNamaNasabah().toUpperCase());
//                        detailModel.setIdNasabah(model.getIdNasabah().toUpperCase());
//                        detailModel.setTrack_id(model.getTrack_id());
//                        detailModel.setCustomertype_id(model.getCustomertype_id());
//                        detailModel.setFormCode(model.getFormCode());
//                        detailModel.setIcon(model.getIcon());
//                        detailModel.setLast_track_date(model.getLast_track_date());
//                        Date date = dateFormat.parse(model.getLast_track_date());
//                        detailModel.setSort_date(date);
//                        arraylist.add(detailModel);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
                if(!isNew)
                    Collections.sort(taskListList);
                else
                    Collections.sort(taskListList, Collections.reverseOrder());

//                sortDate();
                taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, assignedType, new TaskListInterface() {
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


    private void searchList(){
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if(taskListAdapter == null){
            Log.e("TASKLIST ADAPTER " ,"ISNULL");
        }

        _etSearch.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        _etSearch.setMaxWidth(Integer.MAX_VALUE);
        _etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, assignedType, new TaskListInterface() {
                    @Override
                    public void onListSelected(TaskListDetailModel list) {

                    }
                });
                taskListAdapter.getFilter().filter(query);
                Log.e("TASKLIST ADAPTER " ,"AFTER FILTER " + taskListAdapter.getItemCount());
                taskListAdapter.notifyDataSetChanged();
                taskListRecycleAll.setAdapter(taskListAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                taskListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onListSelected(TaskListDetailModel list) {

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

    @Override
    public void onSliderClick(BaseSliderView slider) {

        String link = slider.getBundle().getString("extra");
        String package_name = slider.getBundle().getString("package");

        if (package_name.length() > 1) {
            if (isPackageExisted(package_name)) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(package_name);
                startActivity(launchIntent);//null pointer check in case package name was not found
            } else {
//                popUpSlider();
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
//                startActivity(browserIntent);
            }
        } else if (!link.equalsIgnoreCase("") && link.length() > 10) {
//            popUpSlider();
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
//            startActivity(browserIntent);
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

    private void showNews(ArrayList<NewsModel> news) {
        Log.e("HAHAHAHHA "," WOII " + news.size());
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapter(fragManager, news));
        viewPagerArrowIndicator.bind(viewPager);
    }

//    private void popUpSlider(){
//        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
//        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View dialogView = layoutInflater.inflate(R.layout.popup_slider, null);
//
//        final WebView mWebView = (WebView)getView().findViewById(R.id.activity_main_webview);
//        final ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progressBar1);
//        dialogBuilder.setView(dialogView);
//        dialogBuilder.show();
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        mWebView.loadUrl("https://www.youtube.com/watch?v=c-74OSumhNk");
//    }
}



