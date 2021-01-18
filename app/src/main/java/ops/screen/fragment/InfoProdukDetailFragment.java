package ops.screen.fragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;

public class InfoProdukDetailFragment extends Fragment {


    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtDesc)
    TextView txtDesc;

    @BindView(R.id.txtDesc2)
    TextView txtDesc2;

    @BindView(R.id.btnClose)
    TextView btnClose;

    private String screenInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infodetail_fragment, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            screenInfo=getArguments().getString("info");
        }
        loadAsset(screenInfo);
        return view;
    }


    public void loadAsset(String intent) {
        if (intent.equalsIgnoreCase("PANDUAN_PARTNER")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            }
            txtTitle.setText(R.string.infoTitle4);
            txtDesc.setText(R.string.infoDesc4);
            txtDesc2.setText(R.string.infNote4);
        } else if (intent.equalsIgnoreCase("KREDIT_AGUNAN")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtTitle.setText(R.string.infoTitle2);
                txtDesc.setText(R.string.infoDesc2);
                txtDesc2.setText(R.string.infNote);
            } else if (intent.equalsIgnoreCase("TENTANG_PARTNER")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                }
                txtTitle.setText(R.string.infoTitle3);
                txtDesc.setText(R.string.infoDesc3);
                txtDesc2.setText(R.string.infNote4);
            }
        }

    }

}
