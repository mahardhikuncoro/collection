package ops.screen.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.R;


public class FullEntryDataListImageLine extends FullEntryListImageAdapter.ViewHolder {

    @BindView(R.id.namaNasabah)
    TextView namaNasabah;
    @BindView(R.id.idNasabah)
    TextView idNasabah;
    @BindView(R.id.rentalLinear)
    LinearLayout rentalLinear;
    @BindView(R.id.iconlist)
    ImageView _iconlist;

    public FullEntryDataListImageLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
