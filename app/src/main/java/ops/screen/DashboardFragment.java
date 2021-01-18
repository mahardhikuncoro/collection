package ops.screen;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import base.sqlite.Userdata;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import user.login.LoginActivity;

public class DashboardFragment extends Fragment {


    @BindView(R.id.pengajuanKreditLinear)
    LinearLayout pengajuanKreditLinear;

    @BindView(R.id.success1)
    TextView success1;

    @BindView(R.id.rentalLinear)
    LinearLayout rentalLinear;

    @BindView(R.id.panduanTransferLinear)
    LinearLayout panduanTransferLinear;


    View view;
//    PanduanTransferFragment panduanTransferFragment;

    TapTargetSequence sequence;

    Userdata userdata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        ButterKnife.bind(this, view);
        userdata = new Userdata(getActivity());
//        panduanTransferFragment = new PanduanTransferFragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString("sk").equalsIgnoreCase("sk")) {
                success1.setVisibility(View.VISIBLE);
            }
        }

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        success1.setVisibility(View.GONE);
                    }
                });

        return view;
    }

//    @OnClick(R.id.pengajuanKreditLinear)
//    public void pengajuan() {
//        if(userdata.count() == 0) {
//            dialognedlogin(R.string.asklogin);
//        }
//        else{
//            Intent intent = new Intent(getActivity(), SimulasiHome.class);
//            startActivity(intent);
//        }
//    }

    @OnClick(R.id.rentalLinear)
    public void rental() {
        if(userdata.count() == 0) {
            dialognedlogin(R.string.asklogin);
        }else {
            MainActivityDashboard mainActivityDashboard = (MainActivityDashboard) getActivity();
            mainActivityDashboard.selectedItemId = 0;
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        }
    }


    @OnClick(R.id.panduanTransferLinear)
    public void panduanTransfer() {
        if(userdata.count() == 0) {
            dialognedlogin(R.string.asklogin);
        }else {
            MainActivityDashboard mainActivityDashboard = (MainActivityDashboard) getActivity();
            mainActivityDashboard.selectedItemId = 0;
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
//            fragmentTransaction.replace(R.id.frameLayout, panduanTransferFragment).addToBackStack(null).commit();
        }
    }

    protected void dialogDone(String rString) {
        new MaterialDialog.Builder(getActivity())
                .content(rString)
                .positiveText("Regis Ulang")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .show();
    }

    protected void dialognedlogin(int rString) {
        new MaterialDialog.Builder(getActivity())
                .content(rString)
                .positiveText("Login")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .cancelable(false)
                .show();
    }
}
