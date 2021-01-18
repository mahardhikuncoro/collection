package ops.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import base.network.NetworkConnection;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TaskListDetailModel;
import id.collection.mobile.application.R;


public class FullEntryAdapter extends RecyclerView.Adapter<FullEntryAdapter.ViewHolder> {

    private List<TaskListDetailModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
//    private EndPoint endPoint;
//    private MaterialDialog dialog;
//    private String regno , tc, type, status, newdata, nama;
    private FullEntryCallback callback;


    public FullEntryAdapter(List<TaskListDetailModel> list, FullEntryCallback callback, Context context) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }


    @Override
    public FullEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new FullEntryListLine(view);
    }

    @Override
    public void onBindViewHolder(final FullEntryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ((FullEntryListLine) holder).namaNasabah.setText("" + list.get(position).getSectionName());
            ((FullEntryListLine) holder).idNasabah.setVisibility(View.GONE);
            ((FullEntryListLine) holder).namaNasabah.setGravity(Gravity.CENTER_VERTICAL);

            if(list.get(position).getIcon() != null) {
                if (list.get(position).getIcon().equalsIgnoreCase("ic_short_entry_blue")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.mipmap.ic_short_entry_blue);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_surveypekerjaan")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_surveypekerjaan);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_tempattinggal")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_tempattinggal);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_financial")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_financial);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_customer")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_customer);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_contact")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_contact);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_asset")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_asset);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_balance")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_balance);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_asuransi")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_asuransi);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("ic_ask")) {
                    ((FullEntryListLine) holder)._iconlist.setImageResource(R.drawable.ic_ask);
                }
            }

        setAnimation(holder.itemView, position);

        ((FullEntryListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.detailSection(position);
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
