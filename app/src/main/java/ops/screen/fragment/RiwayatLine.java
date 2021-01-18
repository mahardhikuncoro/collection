package ops.screen.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.R;


public class RiwayatLine extends RiwayatAdapter.ViewHolder {

    @BindView(R.id.txtRegno)
    TextView txtRegno;
    @BindView(R.id.txtNamaCust)
    TextView txtNamaCust;
    @BindView(R.id.txtPosisi)
    TextView txtPosisi;
    @BindView(R.id.txtTglMulai)
    TextView txtTglMulai;
    @BindView(R.id.txtProses)
    TextView txtProses;
    @BindView(R.id.txtLamaProses)
    TextView txtLamaProses;
    @BindView(R.id.layoutPosisi)
    LinearLayout rentalLinear;


    public RiwayatLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
