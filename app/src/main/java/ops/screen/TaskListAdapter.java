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
import java.util.logging.LogRecord;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.TaskListJson;
import base.sqlite.SQLiteConfigKu;
import base.utils.ParameterKey;
import id.collection.mobile.application.R;
import ops.screen.fragment.FullEntry;
import base.sqlite.TaskListDetailModel;
import ops.screen.fragment.TaskListInterface;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> implements Filterable {

    private List<TaskListDetailModel> list;
    private static List<TaskListDetailModel> listFiltered;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    private String typelist;
    private static TaskListInterface listener;


    public TaskListAdapter(Context context, List<TaskListDetailModel> list, String typeList, TaskListInterface listener) {

        notifyDataSetChanged();
        this.list = list;
        this.context = context;
        this.typelist = typeList;
        this.listFiltered = list;
        this.listener = listener;

        sqLiteConfigKu = new SQLiteConfigKu(context);
        networkConnection = new NetworkConnection(context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
    }


    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new TaskListLine(view);
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ((TaskListLine) holder).namaNasabah.setText("" + list.get(position).getNamaNasabah());
            ((TaskListLine) holder).idNasabah.setText("" + list.get(position).getIdNasabah());
            ((TaskListLine) holder).date.setVisibility(View.VISIBLE);
            ((TaskListLine) holder).date.setText("" + list.get(position).getLast_track_date());

            if(list.get(position).getIcon() != null) {
                if (list.get(position).getIcon().equalsIgnoreCase("pengajuanawal")
                        || list.get(position).getIcon().equalsIgnoreCase("dokumen")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.mipmap.ic_short_entry_blue);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("surveypekerjaan")
                        || list.get(position).getIcon().equalsIgnoreCase("bisnis")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_surveypekerjaan);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("surveytempattinggal")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_tempattinggal);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("inputfinancial")
                        || list.get(position).getIcon().equalsIgnoreCase("income")
                        || list.get(position).getIcon().equalsIgnoreCase("biaya")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_financial);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("pasangan")
                        || list.get(position).getIcon().equalsIgnoreCase("customer")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_customer);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("emergencycontact")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_contact);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("aset")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_asset);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("creditfac")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_balance);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("asuransi")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_asuransi);
                }
                if (list.get(position).getIcon().equalsIgnoreCase("decision")) {
                    ((TaskListLine) holder)._iconlist.setImageResource(R.drawable.ic_ask);
                }
            }

        setAnimation(holder.itemView, position);

        ((TaskListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
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
