package ops.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Serializable;
import java.util.List;

import base.sqlite.ContentModel;
import base.sqlite.TaskListDetailModel;
import id.collection.mobile.application.R;


public class FullEntryListAdapter extends RecyclerView.Adapter<FullEntryListAdapter.ViewHolder> implements Serializable {

    private List<TaskListDetailModel> list;
    private List<ContentModel> listContent;
    private Context context;
    private int lastPosition = -1;
    private FullEntryCallback callback;


    public FullEntryListAdapter(Context context, List<TaskListDetailModel> list, FullEntryCallback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }


    @Override
    public FullEntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new FullEntryDataListLine(view);
    }

    @Override
    public void onBindViewHolder(final FullEntryListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ((FullEntryDataListLine) holder).namaNasabah.setText("" + list.get(position).getDoc_desc());
            ((FullEntryDataListLine) holder).idNasabah.setVisibility(View.GONE);

        if(list.get(position).getIcon() != null) {
            if (list.get(position).getIcon().equalsIgnoreCase("ic_short_entry_blue")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.mipmap.ic_short_entry_blue);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_surveypekerjaan")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_surveypekerjaan);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_tempattinggal")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_tempattinggal);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_financial")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_financial);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_customer")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_customer);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_contact")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_contact);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_asset")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_asset);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_balance")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_balance);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_asuransi")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_asuransi);
            }
            if (list.get(position).getIcon().equalsIgnoreCase("ic_ask")) {
                ((FullEntryDataListLine) holder)._iconlist.setImageResource(R.drawable.ic_ask);
            }
        }

        setAnimation(holder.itemView, position);

        ((FullEntryDataListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.detailDokumen(position);

//                if(list != null && list.get(position).getMultiple_file().equalsIgnoreCase("0")) {
//                    Intent intent = new Intent(context, FormActivity.class);
//                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
//                    intent.putExtra("REGNO", regno);
//                    intent.putExtra("IMAGEID", list.get(position).getContent().get(0).getDataId());
//                    intent.putExtra("TC", tc);
//                    intent.putExtra("TYPE", type);
//                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
//                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
//                    intent.putExtra("NAMA_NASABAH", nama);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//                else{
//                    listContent = new ArrayList<ContentModel>();
//                    for(TaskListDetailModel.Content contentmodel : list.get(position).getContent()){
//                        ContentModel model = new ContentModel();
//                        model.setKeyFieldName(contentmodel.getKeyFieldName());
//                        model.setDataId(contentmodel.getDataId());
//                        model.setDataDesc(contentmodel.getDataDesc());
//                        model.setIcon(contentmodel.getIcon());
//                        model.setSectionName(list.get(position).getSectionName());
//                        model.setTableName(list.get(position).getTableName());
//                        model.setFormName(list.get(position).getFormName());
//                        listContent.add(model);
//                    }
//                    Log.e("SIZE OF ","CONTENT AWAL : " + listContent.size());
//                    FullEntryListImage fullEntryListImage = new FullEntryListImage();
//                    fullEntryListImage.contentModels = new ArrayList<>();
//                    fullEntryListImage.contentModels = listContent;
//
//                    Intent intent = new Intent(context, FullEntryListImage.class);
//                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
//                    intent.putExtra("REGNO", regno);
//                    intent.putExtra("TC", tc);
//                    intent.putExtra("TYPE", type);
//                    intent.putExtra("NAMA_NASABAH", nama);
//                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
//                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
//                    intent.putExtra("DOC_CODE", list.get(position).getDoc_code());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }

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
