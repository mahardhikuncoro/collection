package ops.screen.offline;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.collection.mobile.application.R;
import ops.screen.fragment.FullEntryListAdapter;


public class DokumenListLine extends DokumenListAdapter.ViewHolder {

    @BindView(R.id.namaNasabah)
    TextView namaNasabah;
    @BindView(R.id.idNasabah)
    TextView idNasabah;
    @BindView(R.id.rentalLinear)
    LinearLayout rentalLinear;
    @BindView(R.id.iconlist)
    ImageView _iconlist;

    public DokumenListLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
