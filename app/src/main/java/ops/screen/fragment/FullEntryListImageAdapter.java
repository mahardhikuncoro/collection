package ops.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import base.sqlite.ContentModel;
import id.collection.mobile.application.R;


public class FullEntryListImageAdapter extends RecyclerView.Adapter<FullEntryListImageAdapter.ViewHolder> {

    private List<ContentModel> list;
    private Context context;
    private int lastPosition = -1;
    private FullEntryCallback callback;


    public FullEntryListImageAdapter(Context context, FullEntryCallback callback ,List<ContentModel> list) {
        this.list = list;
        this.context = context;
        this.callback=callback;
    }


    @Override
    public FullEntryListImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new FullEntryDataListImageLine(view);
    }

    @Override
    public void onBindViewHolder(final FullEntryListImageAdapter.ViewHolder holder, final int position) {
            ((FullEntryDataListImageLine) holder).namaNasabah.setText("" + list.get(position).getDataDesc());
            ((FullEntryDataListImageLine) holder).idNasabah.setVisibility(View.GONE);


        if(list.get(position).getIcon() != null) {
            if (list.get(position).getIcon().equalsIgnoreCase("ic_short_entry_blue")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.mipmap.ic_short_entry_blue);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_surveypekerjaan")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_surveypekerjaan);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_tempattinggal")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_tempattinggal);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_financial")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_financial);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_customer")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_customer);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_contact")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_contact);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_asset")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_asset);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_balance")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_balance);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_asuransi")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_asuransi);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_ask")) {
                ((FullEntryDataListImageLine) holder)._iconlist.setImageResource(R.drawable.ic_ask);
            }
        }

        setAnimation(holder.itemView, position);

        ((FullEntryDataListImageLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    callback.detailListDokumen(position);
//                    Intent intent = new Intent(context, FormActivity.class);
//                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
//                    intent.putExtra("REGNO", regno);
//                    intent.putExtra("IMAGEID", list.get(position).getDataId());
//                    intent.putExtra("TC", tc);
//                    intent.putExtra("TYPE", type);
//                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
//                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
//                    intent.putExtra("NAMA_NASABAH", nama);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
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
