package ahmedt.kelulusanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextInputEditText edtNisn;
    private TextInputLayout layNisn;
    private Button btnSubmit;
    private ImageView imgMutu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView(){
        edtNisn = findViewById(R.id.edt_nisn);
        layNisn = findViewById(R.id.layout_nisn);
        btnSubmit = findViewById(R.id.btn_submit);
        imgMutu = findViewById(R.id.img_mutu);

        Glide.with(MainActivity.this)
                .load(R.drawable.logo_md)
                .apply(new RequestOptions().override(300, 300))
                .into(imgMutu);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNisn.getText().toString().trim().isEmpty()){
                    layNisn.setErrorEnabled(false);
                    submitNisn(edtNisn.getText().toString().trim());
                }else {
                    Toast.makeText(MainActivity.this, "NISN harus diisi!", Toast.LENGTH_SHORT).show();
                    layNisn.setError("NISN harus diisi!");
                } }
        });
    }

    private void submitNisn(String nisn){
        final KProgressHUD hud = new KProgressHUD(this);
        Utils.getLoading(hud, null, null, false);
        AndroidNetworking.post(LinkClass.URL_GET_NISN)
                .addBodyParameter("nisn", nisn)
                .build()
                .getAsOkHttpResponseAndObject(KelulusanModel.class, new OkHttpResponseAndParsedRequestListener<KelulusanModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, KelulusanModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Intent i = new Intent(MainActivity.this, MessageActivity.class);
                                String status = response.getData().getKeterangan();
                                String nama = response.getData().getNama();
                                String jurusan = response.getData().getJurusan();
                                if (response.getData().getFile() != null){
                                    String file = response.getData().getFile();
                                    i.putExtra("isFileNull", 0);
                                    i.putExtra("file", file);
                                }else {
                                    i.putExtra("isFileNull", 1);
                                }
                                i.putExtra("nama", nama);
                                i.putExtra("status", status);
                                i.putExtra("jurusan", jurusan);
                                startActivity(i);
                            }else {
                                Toast.makeText(MainActivity.this, "NISN tidak dikenal!", Toast.LENGTH_SHORT).show();
                                layNisn.setError("NISN tidak dikenal!");
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                        Toast.makeText(MainActivity.this, "Koneksi bermasalah!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
