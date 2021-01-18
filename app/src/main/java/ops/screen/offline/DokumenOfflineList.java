package ops.screen.offline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import base.data.DokumenOfflineModel;
import base.screen.BaseDialogActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import user.login.LoginActivity;

public class DokumenOfflineList  extends BaseDialogActivity implements DokumenListCallback {

    @BindView(R.id.recyclerlistdokumenoffline)
    RecyclerView recyclerView;
    @BindView(R.id.buttontambahoffline)
    Button floatingActionButton;
    @BindView(R.id.btnback_toolbar)
    ImageView _btnback_toolbar;
    private DokumenListAdapter dokumenListAdapter;
    private String regno,tc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengajuan_doc_list);
        ButterKnife.bind(this);
        initiateApiData();
        transparentStatusbar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        dokumenListAdapter = new DokumenListAdapter(this, list(), getIntent().getStringExtra("ID_TYPE_DOKUMEN"));
        recyclerView.setAdapter(dokumenListAdapter);
        recyclerView.setHasFixedSize(true);

        if(getIntent().getExtras() != null){
            regno = getIntent().getStringExtra("REGNO");
            tc = getIntent().getStringExtra("TC");
        }
    }

    public List<DokumenOfflineModel> list() {
        if(getIntent().getStringExtra("ID_TYPE_DOKUMEN") != null){
            if (dokumenData.selectListbyIdtype(getIntent().getStringExtra("ID_TYPE_DOKUMEN")) != null) {
                return dokumenData.selectListbyIdtype(getIntent().getStringExtra("ID_TYPE_DOKUMEN"));
            } else {
                return new ArrayList<>();
            }
        }else {
            if (dokumenData.selectList(null) != null) {
                return dokumenData.selectList(null);
            } else {
                return new ArrayList<>();
            }
        }
    }

    @Override
    public void detail(Integer id, String idType) {
        int countIdType = dokumenData.countByIdType(idType);
        Log.e("ID TYPE ","COUNT " + idType +" COUNT " + countIdType);
        if(countIdType <= 1 ) {
            Intent intentFormOfflineList = new Intent(DokumenOfflineList.this, FormOfflineDocument.class);
            intentFormOfflineList.putExtra("ID_DOKUMEN", id);
            intentFormOfflineList.putExtra("REGNO", regno);
            intentFormOfflineList.putExtra("TC", tc);
            startActivity(intentFormOfflineList);
        }else{// IMAGE LEBIH DARI 1
            if(getIntent().getStringExtra("ID_TYPE_DOKUMEN") == null) {
                Intent intentFormOfflineList = new Intent(DokumenOfflineList.this, DokumenOfflineList.class);
                intentFormOfflineList.putExtra("ID_TYPE_DOKUMEN", idType);
                intentFormOfflineList.putExtra("REGNO", regno);
                intentFormOfflineList.putExtra("TC", tc);
                startActivity(intentFormOfflineList);
            }else{
                Intent intentFormOfflineList = new Intent(DokumenOfflineList.this, FormOfflineDocument.class);
                intentFormOfflineList.putExtra("ID_DOKUMEN", id);
                intentFormOfflineList.putExtra("ID_TYPE_DOKUMEN", idType);
                intentFormOfflineList.putExtra("REGNO", regno);
                intentFormOfflineList.putExtra("TC", tc);
                startActivity(intentFormOfflineList);
            }
        }
    }

    @OnClick(R.id.buttontambahoffline)
    public void tambahDokumen(){
        Intent intentFormOffline = new Intent(getApplicationContext(), FormOfflineDocument.class);
        intentFormOffline.putExtra("REGNO", regno);
        intentFormOffline.putExtra("TC", tc);
        startActivity(intentFormOffline);
    }

    @OnClick(R.id.btnback_toolbar)
    public void backToolbar(){
        if(getIntent().getExtras() == null){
            Intent intentFormOffline = new Intent(DokumenOfflineList.this, LoginActivity.class);
            startActivity(intentFormOffline);
        }else{
            if(getIntent().getStringExtra("ID_TYPE_DOKUMEN") != null) {
                Intent intentFormOfflineList = new Intent(DokumenOfflineList.this, DokumenOfflineList.class);
                startActivity(intentFormOfflineList);
            }else {
                if(getIntent().getStringExtra("REGNO") != null) {
                    Intent intentFormOffline = new Intent(DokumenOfflineList.this, MainActivityDashboard.class);
                    startActivity(intentFormOffline);
                }else{
                    Intent intentFormOffline = new Intent(DokumenOfflineList.this, LoginActivity.class);
                    startActivity(intentFormOffline);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getExtras() == null){
            Intent intentFormOffline = new Intent(DokumenOfflineList.this, LoginActivity.class);
            startActivity(intentFormOffline);
        }else{
            if(getIntent().getStringExtra("ID_TYPE_DOKUMEN") != null) {
                Intent intentFormOfflineList = new Intent(DokumenOfflineList.this, DokumenOfflineList.class);
                startActivity(intentFormOfflineList);
            }else {
                if(getIntent().getStringExtra("REGNO") != null) {
                    Intent intentFormOffline = new Intent(DokumenOfflineList.this, MainActivityDashboard.class);
                    startActivity(intentFormOffline);
                }else{
                    Intent intentFormOffline = new Intent(DokumenOfflineList.this, LoginActivity.class);
                    startActivity(intentFormOffline);
                }
            }
        }
    }
}
