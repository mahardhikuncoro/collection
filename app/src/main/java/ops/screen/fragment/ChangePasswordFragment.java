package ops.screen.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ParameterJson;
import base.network.ResponseStatus;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfig;
import base.sqlite.SQLiteConfigKu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordFragment extends Fragment {

    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.etPassNew)
    EditText etPassNew;

    @BindView(R.id.etRePassNew)
    EditText etRetypePassnew;

    @BindView(R.id.titleSimulasiKredit)
    TextView txtTitle;

    @BindView(R.id.imgPass)
    ImageView imgPass;
    @BindView(R.id.imgPassNew)
    ImageView imgPassNew;
    @BindView(R.id.imgRePassNew)
    ImageView imgDataKtp;

    @BindView(R.id.btnChangePass)
    Button btnChangePassword;

    @BindView(R.id.titlePass)
    TextView txtPass;
    @BindView(R.id.titlePassNew)
    TextView txtPassNew;
    @BindView(R.id.titleRePassNew)
    TextView txtRePassnew;

    @BindView(R.id.HintPass)
    TextInputLayout hintPass;
    @BindView(R.id.HintPassNew)
    TextInputLayout hintPassnew;

    @BindView(R.id.HintRePassNew)
    TextInputLayout hintRePassnew;

    private SQLiteConfigKu sqLiteConfigKu;
    private Userdata datapribadi;
    private String dataNama;
    private String dataNoHp;
    private String dataKTP;
    private String dataGender;
    private String dataEmail;
    private String mailcode;
    private String valid;
    private Picasso picasso;
    private NetworkConnection networkConnection;
    private MaterialDialog dialog;
    private Userdata userdata;
    private EndPoint endPoint;
    private ProfileFragment profileFragment;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkConnection = new NetworkConnection(getActivity());
        userdata = new Userdata(getActivity());
        sqLiteConfigKu = new SQLiteConfigKu(getActivity());
        profileFragment = new ProfileFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofit.create(EndPoint.class);
        getProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.change_password_activity, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAsset();
        //setProfile();
    }

    private void loadAsset() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(getActivity());
        picassoBuilder.downloader(new OkHttp3Downloader(
                NetworkClient.getUnsafeOkHttpClient()
        ));
        picasso = picassoBuilder.build();

        loadIcon();
    }

    private void loadIcon() {
        picasso.load(R.drawable.ic_password).fit().into(imgPass);
    }
    private void getProfile(){

        datapribadi = new Userdata(getActivity());
        dataNama =  datapribadi.select().getUserid();
        dataNoHp = datapribadi.select().getUserid();
        dataKTP = datapribadi.select().getUserid();
        dataGender = datapribadi.select().getUserid();
        dataEmail = datapribadi.select().getUserid();
        Log.e("FULLNAME "," " + dataNama);
    }

    public boolean isValidPassword(String pass) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if (pass.matches(pattern)) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnChangePass)
    public void changePass(){
        if(!isValidPassword(etPassNew.getText().toString())){
            dialog(R.string.errorPassword);
        }
        else if(!etPassNew.getText().toString().equalsIgnoreCase(etRetypePassnew.getText().toString())){
            dialog(R.string.errorPasswordNotMatch);
        }else {
            if (!networkConnection.isNetworkConnected()) {
                dialog(R.string.errorNoInternetConnection);
            } else {

                dialog = new MaterialDialog.Builder(getActivity())
                        .content(R.string.loading)
                        .progress(true, 0)
                        .cancelable(false)
                        .show();

                final ParameterJson.changePassRequest request = new ParameterJson().new changePassRequest();
                request.setVersion(sqLiteConfigKu.getServerVersion());
                request.setCurrent_password(etPass.getText().toString());
                request.setNew_password(etPassNew.getText().toString());
                request.setTelp(datapribadi.select().getUserid());
                Call<ParameterJson.changePassResponse> call = endPoint.changePassEndpoint(request);
                call.enqueue(new Callback<ParameterJson.changePassResponse>() {
                    @Override
                    public void onResponse(Call<ParameterJson.changePassResponse> call, Response<ParameterJson.changePassResponse> response) {
                        if(response != null){
                            dialog.dismiss();
                            if(response.body().getResponseStatus().equalsIgnoreCase(ResponseStatus.CURRENT_PASSWORD_NOT_VALID.name())){
                                dialog(R.string.erroroldpassword);
                            }
                            else if(response.body().getResponseStatus().equalsIgnoreCase(ResponseStatus.PASSWORD_SUCCESSFULLY_CHANGED.name())){
                                dialog(R.string.successchangepass);
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_right, R.animator.animation_back_left);
                                fragmentTransaction.replace(R.id.frame_container, profileFragment).addToBackStack(null).commit();
                            }
                            else if(response.body().getResponseStatus().equalsIgnoreCase(ResponseStatus.INCOMPATIBLE_VERSION.name())){
                                dialogPlaystore(R.string.errorIncompatibleVersion);
                            }
                            else{
                                dialog(R.string.errorBackend);
                            }
                        }else{
                            dialog(R.string.errorBackend);
                        }
                    }

                    @Override
                    public void onFailure(Call<ParameterJson.changePassResponse> call, Throwable t) {

                    }
                });


            }
        }
    }

    protected void dialog(int rString) {
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

    public void dialogPlaystore(int rString) {
        final SQLiteConfig config = new SQLiteConfig(getActivity());
        new MaterialDialog.Builder(getActivity())
                .content(rString)
                .positiveText(R.string.buttonClose)
                .negativeText(R.string.buttonUpdate)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        final String appPackageName = getActivity().getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGoogleMarket() + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGooglePlaystore() + appPackageName)));
                        }
                    }
                })
                .cancelable(true)
                .show();
    }

}
