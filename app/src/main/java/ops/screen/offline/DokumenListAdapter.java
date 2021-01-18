package ops.screen.offline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import base.data.DokumenOfflineModel;
import id.collection.mobile.application.R;


public class DokumenListAdapter extends RecyclerView.Adapter<DokumenListAdapter.ViewHolder> {
    private List<DokumenOfflineModel> list;
    private DokumenListCallback callback;
    private String idTypeDoc;

    public DokumenListAdapter(DokumenListCallback callback, List<DokumenOfflineModel> list, String idTypeDoc){
        this.callback = callback;
        this.list = list;
        this.idTypeDoc = idTypeDoc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tasklist_holder, viewGroup, false);
        return new DokumenListLine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(idTypeDoc == null)
            ((DokumenListLine) viewHolder).namaNasabah.setText("" + list.get(i).getTypedokumen());
        else
            ((DokumenListLine) viewHolder).namaNasabah.setText("" + list.get(i).getNamadokumen());
        ((DokumenListLine) viewHolder).idNasabah.setText("" + list.get(i).getIdtypedokumen());
//        ((DokumenListLine) viewHolder).idNasabah.setVisibility(View.GONE);

        final Integer id= list.get(i).getId();
        final String idType= list.get(i).getIdtypedokumen();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OOOOOOOOOO ",": " + idType);
                callback.detail(id, idType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }


}
