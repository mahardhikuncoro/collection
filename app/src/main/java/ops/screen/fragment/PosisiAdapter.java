package ops.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import base.network.NetworkConnection;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TasklistHistoryModel;
import base.utils.ParameterKey;
import id.collection.mobile.application.R;


public class PosisiAdapter extends RecyclerView.Adapter<PosisiAdapter.ViewHolder> {

    private List<TasklistHistoryModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;


    public PosisiAdapter(List<TasklistHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public PosisiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailhistory_holder, parent, false);
        return new PosisiLine(view);
    }

    @Override
    public void onBindViewHolder(final PosisiAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ((PosisiLine) holder).txtRegno.setText("" + list.get(position).getAppnumber().toUpperCase());
//            ((PosisiLine) holder).txtNamaCust.setText("" + list.get(position).getCurrtrcode());
        ((PosisiLine) holder).txtPosisi.setText(": " + list.get(position).getTr_desc());
        ((PosisiLine) holder).txtTglMulai.setText(": " + list.get(position).getLasttrdate());
        ((PosisiLine) holder).txtProses.setText(": " + list.get(position).getNexttrby());
        ((PosisiLine) holder).txtLamaProses.setText(": " + list.get(position).getAging());
//            ((PosisiLine) holder).txtRegno.setVisibility(View.GONE);
        ((PosisiLine) holder).txtNamaCust.setVisibility(View.GONE);
        ((PosisiLine) holder).layoutPosisi.setBackgroundResource(R.drawable.border_only_bottom_linearlayout);


        setAnimation(holder.itemView, position);

        ((PosisiLine) holder).layoutPosisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appnumber = list.get(position).getAppnumber();
                Intent intent = new Intent(context, HistoryListDetail.class);
                intent.putExtra(ParameterKey.REGNO, appnumber);
                context.startActivity(intent);
            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    protected void dialog(String rString) {
        new MaterialDialog.Builder(context)
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

}
