package ops.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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


public class TaskListHistoryAdapter extends RecyclerView.Adapter<TaskListHistoryAdapter.ViewHolder> {

    private List<TasklistHistoryModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;


    public TaskListHistoryAdapter(List<TasklistHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
//        this.callback = callback;
    }


    @Override
    public TaskListHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new TaskListHistoryLine(view);
    }

    @Override
    public void onBindViewHolder(final TaskListHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ((TaskListHistoryLine) holder).namaNasabah.setText("" + list.get(position).getCustname());
            ((TaskListHistoryLine) holder).idNasabah.setText("" + list.get(position).getAppnumber());
            ((TaskListHistoryLine) holder).namaNasabah.setGravity(Gravity.CENTER_VERTICAL);

        setAnimation(holder.itemView, position);

        ((TaskListHistoryLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appnumber = list.get(position).getAppnumber();
                Intent intent = new Intent(context, HistoryListDetail.class);
                intent.putExtra(ParameterKey.REGNO, appnumber);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
