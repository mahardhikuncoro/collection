package ops.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TaskListDetailModel;
import base.utils.ParameterKey;
import id.collection.mobile.application.R;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.TaskListInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskListActivityAdapter extends RecyclerView.Adapter<TaskListActivityAdapter.ViewHolder> implements Filterable {

    private List<TaskListDetailModel> list;
    private static List<TaskListDetailModel> listFiltered;
    private Context context;
    private int lastPosition = -1;
    private String typelist;
    private static TaskListInterface listener;


    public TaskListActivityAdapter(Context context, List<TaskListDetailModel> list, String typeList, TaskListInterface listener) {

        notifyDataSetChanged();
        this.list = list;
        this.context = context;
        this.typelist = typeList;
        this.listFiltered = list;
        this.listener = listener;

    }


    @Override
    public TaskListActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new TaskListActivityLine(view);
    }

    @Override
    public void onBindViewHolder(final TaskListActivityAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ((TaskListActivityLine) holder).namaNasabah.setText("" + list.get(position).getNamaNasabah());
            ((TaskListActivityLine) holder).idNasabah.setText("" + list.get(position).getIdNasabah());
            ((TaskListActivityLine) holder).date.setVisibility(View.VISIBLE);
            ((TaskListActivityLine) holder).date.setText("" + list.get(position).getLast_track_date());

            if(list.get(position).getIcon() != null) {
                if (list.get(position).getIcon().equalsIgnoreCase("pengajuanawal")
                        || list.get(position).getIcon().equalsIgnoreCase("dokumen")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.mipmap.ic_short_entry_blue);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("surveypekerjaan")
                        || list.get(position).getIcon().equalsIgnoreCase("bisnis")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_surveypekerjaan);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("surveytempattinggal")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_tempattinggal);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("inputfinancial")
                        || list.get(position).getIcon().equalsIgnoreCase("income")
                        || list.get(position).getIcon().equalsIgnoreCase("biaya")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_financial);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("pasangan")
                        || list.get(position).getIcon().equalsIgnoreCase("customer")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_customer);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("emergencycontact")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_contact);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("aset")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_asset);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("creditfac")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_balance);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("asuransi")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_asuransi);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("decision")) {
                    ((TaskListActivityLine) holder)._iconlist.setImageResource(R.drawable.ic_ask);
                }
            }

        setAnimation(holder.itemView, position);

        ((TaskListActivityLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullEntry.class);
                intent.putExtra(ParameterKey.NAMA_NASABAH,list.get(position).getNamaNasabah());
                intent.putExtra(ParameterKey.REGNO,list.get(position).getIdNasabah());
                intent.putExtra(ParameterKey.TC,list.get(position).getTrack_id());
                intent.putExtra(ParameterKey.TYPE,list.get(position).getFormCode());
                intent.putExtra(ParameterKey.TYPELIST,typelist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(View view) {
            super(view);
//            name = view.findViewById(R.id.namaNasabah);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListSelected(listFiltered.get(getAdapterPosition()));
                }
            });
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

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    listFiltered = list;
                }else{
                    List<TaskListDetailModel> filteredList = new ArrayList<>();
                    for(TaskListDetailModel model : list){
                        if(model.getNamaNasabah().toUpperCase().contains(charString.toUpperCase())){
                            filteredList.add(model);
                        }
                    }
                    listFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                try {
                    Log.e("SIZE ","listFiltered : " + listFiltered.size());
                    listFiltered = (ArrayList<TaskListDetailModel>) results.values;
                    notifyDataSetChanged();
//                }catch (Exception e){}

            }
        };
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
