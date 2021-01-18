package ops.screen.offline;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import base.data.DokumenOfflineModel;
import base.endpoint.UploadImageJson;
import base.screen.BaseDialogActivity;
import base.sqlite.DocumenTypeModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.collection.mobile.application.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ops.screen.CameraActivity;
import ops.screen.MainActivityDashboard;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormOfflineDocument extends BaseDialogActivity {

    @BindView(R.id.surveyFormToolbar)
    Toolbar toolbar;
    @BindView(R.id.btnback_toolbar)
    ImageView btnback_toolbar;
    @BindView(R.id.spinnerDocumentype)
    Spinner _spinnerDocumentype;
    @BindView(R.id.etnamadocument)
    EditText _etnamadocument;
    @BindView(R.id.imageSurveyOffline)
    ImageView _imageSurveyOffline;
    @BindView(R.id.btnSend)
    Button _btnSend;

    @BindView(R.id.linearnamadocument)
    LinearLayout _linearnamadocument;
    @BindView(R.id.linearnama)
    LinearLayout _linearnama;
    @BindView(R.id.inputnamadokumen)
    TextInputLayout _inputnamadokumen;
    @BindView(R.id.inputspinner)
    TextInputLayout _inputspinner;
    @BindView(R.id.inputImage)
    TextInputLayout _inputImage;

    private ArrayList<DocumenTypeModel> dokumentypes;
    private String idTypeDokumen;
    private String typeDokumenDesc;
    private Integer idDokumen;
    private Integer idImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_offline_document);
        ButterKnife.bind(this);
        initiateApiData();
        transparentStatusbar();
        insertDokumenType();
        if(getIntent().getExtras() != null){
            if(getIntent().getStringExtra("REGNO") == null) {
                _btnSend.setText(R.string.buttonSimpan);
                _btnSend.setVisibility(View.GONE);
            }else{
                _btnSend.setText(R.string.buttonSubmit);
                _btnSend.setVisibility(View.VISIBLE);
            }
            idDokumen = (getIntent().getExtras().getInt("ID_DOKUMEN"));
            Log.e("ID DOKUMEN "," HAAAAAAAAa " + idDokumen);
            if(idDokumen != 0) {
                setFieldOffline();
            }
        }
    }

    private DokumenOfflineModel getDokumen(Integer id){
        return  dokumenData.select(id);
    }
    private void setFieldOffline(){
        Integer index = 0 ;
        File imgFile = new  File(getDokumen(idDokumen).getImageUrl());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            _imageSurveyOffline.setImageBitmap(myBitmap);
        }
        for(DocumenTypeModel documenTypeModel : dokumentypes){
            if(documenTypeModel.getDocument_id().equalsIgnoreCase(getDokumen(idDokumen).getIdtypedokumen())){
                index = dokumentypes.indexOf(documenTypeModel);
            }
        }
        _spinnerDocumentype.setSelection(index);
        _spinnerDocumentype.setEnabled(false);
        _etnamadocument.setText(getDokumen(idDokumen).getNamadokumen());
        idImage = getDokumen(idDokumen).getId();
    }

    private void insertDokumenType(){
        dokumentypes = new ArrayList();
        dokumentypes.add(new DocumenTypeModel("0","-- pilih --"));
        dokumentypes.add(new DocumenTypeModel("001","Foto Nasabah"));
        dokumentypes.add(new DocumenTypeModel("002","Foto KTP Nasabah dan KTP pasangan"));
        dokumentypes.add(new DocumenTypeModel("003","Foto Rumah"));
        dokumentypes.add(new DocumenTypeModel("004","Foto Usaha"));
        dokumentypes.add(new DocumenTypeModel("005","Foto Jaminan"));
        dokumentypes.add(new DocumenTypeModel("006","Foto Kartu Keluarga"));
        dokumentypes.add(new DocumenTypeModel("007","Foto Lainnya"));
        dokumentypes.add(new DocumenTypeModel("008","Foto Bukti Kepemilikan Tempat Tinggal"));
        dokumentypes.add(new DocumenTypeModel("009","Foto PBB / Rekening Listrik / Telepon / Air"));
        dokumentypes.add(new DocumenTypeModel("01","KTP"));
        dokumentypes.add(new DocumenTypeModel("010","Foto Pencairan Kredit"));
        dokumentypes.add(new DocumenTypeModel("02","Bukti Kepemilikan Jaminan"));
        dokumentypes.add(new DocumenTypeModel("03","Fotocopy KTP"));
        dokumentypes.add(new DocumenTypeModel("04","Foto Suami / Istri"));
        dokumentypes.add(new DocumenTypeModel("05","Surat Keterangan Usaha"));
        dokumentypes.add(new DocumenTypeModel("06","Fotocopy Kartu keluarga"));
        dokumentypes.add(new DocumenTypeModel("07","Copy ID Card Pegawai"));
        dokumentypes.add(new DocumenTypeModel("08","Copy SK pengangkatan Pegawai Tetap"));
        dokumentypes.add(new DocumenTypeModel("09","Surat Kuasa Pemotong Gaji"));
        dokumentypes.add(new DocumenTypeModel("10","Slip Gaji Bulan Terakhir"));
        dokumentypes.add(new DocumenTypeModel("11","Surat Rekomendasi / Persetujuan dari Atasan Langsung"));
        dokumentypes.add(new DocumenTypeModel("12","Fotocopy KTP/SIM/PASSPORT"));
        dokumentypes.add(new DocumenTypeModel("13","Surat Keterangan Usaha/Surat Keterangan Pengelola Pasar"));
        dokumentypes.add(new DocumenTypeModel("14","Slip Pembayaran PLN"));
        dokumentypes.add(new DocumenTypeModel("15","Buku Tabungan"));
        setSpinner();
    }

    public void setSpinner() {
        ArrayAdapter<DocumenTypeModel> adapterskim = new ArrayAdapter<DocumenTypeModel>(this, android.R.layout.simple_spinner_item, dokumentypes);
        adapterskim.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerDocumentype.setAdapter(adapterskim);
        _spinnerDocumentype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTypeDokumen = dokumentypes.get(position).getDocument_id();
                typeDokumenDesc = dokumentypes.get(position).getDocument_name();
                Log.e("ID DOKUMEN ", " : " + idDokumen);
                Log.e("TYPE DOKUMEN ", " : " + typeDokumenDesc);
                if((idDokumen == null || idDokumen == 0) && position != 0){
                    Log.e("UWUU DOKUMEN ", " : " + dokumenData.countByIdType(idTypeDokumen) );
                    if(dokumenData.countByIdType(idTypeDokumen) > 0)
                        _etnamadocument.setText(typeDokumenDesc +" "+ (dokumenData.countByIdType(idTypeDokumen) + 1));
                    else
                        _etnamadocument.setText(typeDokumenDesc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.imageSurveyOffline)
    public void openImage(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }else {
            if(idDokumen == null || idDokumen == 0) {
                if(idTypeDokumen.equalsIgnoreCase("0")) {
                    _inputspinner.setError("Mohon pilih jenis dokumen");
                }else if(_etnamadocument.getText().toString().equalsIgnoreCase("")) {
                   _inputnamadokumen.setError("Mohon isi nama dokumen");
                }else{
                    Log.e("ID IMAGE ", " : " + idImage);
                    Intent intentprofile = new Intent(this, CameraActivity.class);
                    intentprofile.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                    intentprofile.putExtra("TC", getIntent().getStringExtra("TC"));
                    intentprofile.putExtra("UPLOAD_TYPE", "survey_offline");
                    intentprofile.putExtra("NEWDOCUMENT", true);
                    intentprofile.putExtra("IDDOCTYPE", idTypeDokumen);
                    intentprofile.putExtra("ID_DOKUMEN",  getIntent().getStringExtra("ID_DOKUMEN"));
                    intentprofile.putExtra("FORM_NAME", "");
                    intentprofile.putExtra("DOCTYPEDESC", typeDokumenDesc);
                    intentprofile.putExtra("DOCNAME", String.valueOf(_etnamadocument.getText()));
                    intentprofile.putExtra("DOCID", "NEW");
                    startActivity(intentprofile);
                }
            }else{
                showDetailImage();
            }
        }
    }

    @OnClick(R.id.btnback_toolbar)
    public void backButton(){
        Intent intent = new Intent(FormOfflineDocument.this, DokumenOfflineList.class);
        intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
        intent.putExtra("TC", getIntent().getStringExtra("TC"));
        startActivity(intent);
    }

    private void showDetailImage(/*String url, final String doc_code, final String doc_id*/){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(FormOfflineDocument.this).create();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.detail_image, null);
//        final ScrollView _scrool = (ScrollView) dialogView.findViewById(R.id.scroolImage);
        final ImageView _detailimage = (ImageView) dialogView.findViewById(R.id.detailimage);
        final Button _fotoUlangButton = (Button) dialogView.findViewById(R.id.fotoUlangButton);
        final Button _kembaliButton = (Button) dialogView.findViewById(R.id.kembaliButton);
        File imgFile = new  File(getDokumen(idDokumen).getImageUrl());
        if(imgFile.exists()){

            Bitmap bitmap = decodeFile(imgFile);
            _detailimage.setImageBitmap(bitmap);

        }

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        _fotoUlangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID IMAGE "," : " + idImage );
                Log.e("ID REGNO "," : " + getIntent().getStringExtra("REGNO") );
                Intent intentprofile = new Intent(getApplicationContext(), CameraActivity.class);
                intentprofile.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                intentprofile.putExtra("TC", getIntent().getStringExtra("TC"));
                intentprofile.putExtra("UPLOAD_TYPE","survey_offline");
                intentprofile.putExtra("IDIMAGE",idImage);
                intentprofile.putExtra("IDDOCTYPE",idTypeDokumen);
                intentprofile.putExtra("DOCTYPEDESC",typeDokumenDesc);
                intentprofile.putExtra("DOCNAME",String.valueOf(_etnamadocument.getText()));
                intentprofile.putExtra("DOCID","NEW");
                startActivity(intentprofile);
            }
        });
        _kembaliButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.cancel();
            }
        });
    }

    @OnClick(R.id.btnSend)
    public void sendOfflineImage(){

        if(getIntent().getStringExtra("REGNO") != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if(!networkConnection.isNetworkConnected()){
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{
            try {
                if(idDokumen != 0) {
                    File image = new File(getDokumen(idDokumen).getImageUrl());
                    if (image.exists()) {
                        OutputStream outStream = null;
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth()), (int) ((float) bitmap.getHeight()), false);
                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

                        int height = options.outHeight;
                        int width = options.outWidth;
                        if ((height * width) >= 1000000) {
                            resizePhotonew(image);
                        }
                    }
                }else{
                    _inputspinner.setErrorEnabled(false);
                    _inputnamadokumen.setErrorEnabled(false);
                    _inputImage.setErrorEnabled(false);
                    if(idTypeDokumen.equalsIgnoreCase("0")) {
                        _inputspinner.setErrorEnabled(true);
                        _inputspinner.setError("Mohon pilih jenis dokumen !");
                    }else if(_etnamadocument.getText().toString().equalsIgnoreCase("")) {
                        _inputnamadokumen.setErrorEnabled(true);
                        _inputnamadokumen.setError("Mohon isi nama dokumen !");
                    }else{
                        _inputImage.setErrorEnabled(true);
                        _inputImage.setError("Mohon mengambil gambar !");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(idDokumen != 0) {
                File image = new File(getDokumen(idDokumen).getImageUrl());
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), image);

                Log.e("Masuk ", "Upload Image abab  " + image.getAbsolutePath());
                Log.e("Masuk ", "Upload Image dfdfd " + image.getName());

                MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), reqFile);
                RequestBody regno = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("REGNO") == null ? "M111202002858537" : getIntent().getStringExtra("REGNO"));
                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userdata.select().getUserid());
                RequestBody tc = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("TC"));
                RequestBody uploadtype = RequestBody.create(MediaType.parse("text/plain"), "surveyDocument");
                RequestBody docid = RequestBody.create(MediaType.parse("text/plain"), getDokumen(idDokumen).getDocid());
                RequestBody doccode = RequestBody.create(MediaType.parse("text/plain"), getDokumen(idDokumen).getIdtypedokumen());
                RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getLatitude()));
                RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getLongitude()));
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), getAddress());

                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("regno", regno);
                map.put("userid", userid);
                map.put("tc", tc);
                map.put("uploadtype", uploadtype);
                map.put("docid", docid);
                map.put("doccode", doccode);
                map.put("lat", latitude);
                map.put("lon", longitude);
                map.put("addr", address);
                Log.e("Ahh 1", "HALO LINK" + image.getName());
                Log.e("Ahh 2", "HALO LINK" + getIntent().getStringExtra("REGNO"));
                Log.e("Ahh 3", "HALO LINK" + userdata.select().getUserid());
                Log.e("Ahh 4", "HALO LINK" + getIntent().getStringExtra("TC"));
                Log.e("Ahh 5", "HALO LINK" + getIntent().getStringExtra("UPLOAD_TYPE"));
                Log.e("Ahh 6", "HALO LINK" + doccode);
                Log.e("Ahh 7", "HALO LINK" + address);

                Call<UploadImageJson.Callback> call = endPoint.uploadFile(userdata.select().getAccesstoken(), map, body);
                call.enqueue(new Callback<UploadImageJson.Callback>() {
                    @Override
                    public void onResponse(Call<UploadImageJson.Callback> call, Response<UploadImageJson.Callback> response) {
                        try {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                if (response.body().getStatus().equalsIgnoreCase("1")) {
                                    popUpMessage(response.body().getMessage());
                                } else if(response.body().getStatus().equalsIgnoreCase("100")){
                                    removeUserData(response.body().getMessage());
                                }else {
                                    dialogMessage(response.body().getMessage());
                                }
                            }
                        } catch (Exception e) {
                            dialogMessage("ERROR " + e);
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImageJson.Callback> call, Throwable t) {
                        dialog.dismiss();
                        dialogMessage("" + t);
                    }
                });
            }else{
                dialog.dismiss();
                _inputspinner.setErrorEnabled(false);
                _inputnamadokumen.setErrorEnabled(false);
                _inputImage.setErrorEnabled(false);
                if(idTypeDokumen.equalsIgnoreCase("0")) {
                    _inputspinner.setErrorEnabled(true);
                    _inputspinner.setError("Mohon pilih jenis dokumen !");
                }else if(_etnamadocument.getText().toString().equalsIgnoreCase("")) {
                    _inputnamadokumen.setErrorEnabled(true);
                    _inputnamadokumen.setError("Mohon isi nama dokumen !");
                }else{
                    _inputImage.setErrorEnabled(true);
                    _inputImage.setError("Mohon mengambil gambar !");
                }
            }
        }
        }else{
            if(getDokumen(idDokumen).getImageUrl()== null || getDokumen(idDokumen).getImageUrl().equalsIgnoreCase("")) {
               dialogMessage("Mohon Mengambil Foto");
            }else{
                String idtype = (getIntent().getExtras().getString("IDDOCTYPE"));
                String descType = (getIntent().getExtras().getString("DOCTYPEDESC"));
                String docid = (getIntent().getExtras().getString("DOCID"));
                String docname = (getIntent().getExtras().getString("DOCNAME"));
                dokumenData.save(idDokumen, idtype, descType, docname, docid, getDokumen(idDokumen).getImageUrl());
            }
        }
    }

    protected void popUpMessage(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(rString)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(FormOfflineDocument.this, DokumenOfflineList.class);
                        intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                        intent.putExtra("TC", getIntent().getStringExtra("TC"));
                        startActivity(intent);
                        dokumenData.delete(idDokumen);
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .build();

        dialog.show();
    }

    private static void resizePhotonew(File fileImage) {
//        File imgFile = new File(path);

        if(fileImage.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap = BaseCamera.rotate(bitmap, 0);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(fileImage);
                fos.write(bitmapData);

                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap.recycle();
            System.gc();

        }
    }

    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
}

