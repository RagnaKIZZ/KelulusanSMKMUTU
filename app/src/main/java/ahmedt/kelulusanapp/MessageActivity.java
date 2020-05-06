package ahmedt.kelulusanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    private static final int REQUEST_PERMISSION_CODE = 1000;
    private TextView txtSelamat, txtLulus, txtProdi;
    private Button btnDownload;
    private DownloadManager manager;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        txtLulus = findViewById(R.id.txt_status);
        txtSelamat = findViewById(R.id.txt_selamat);
        txtProdi = findViewById(R.id.txt_prodi);
        btnDownload = findViewById(R.id.btn_download);
        final Intent i = getIntent();

        String nama = i.getStringExtra("nama");
        String status = i.getStringExtra("status");
        String jurusan = i.getStringExtra("jurusan");
        txtProdi.setText(getString(R.string.prodi)+" "+jurusan);
        txtLulus.setText(status);
        if (status.toLowerCase().matches("lulus")){
            txtSelamat.setText("Selamat "+nama+"!");
            txtLulus.setTextColor(Color.GREEN);
        }else {
            txtLulus.setTextColor(Color.RED);
            txtSelamat.setText("Maaf "+nama);
        }

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i.getIntExtra("isFileNull", 0)==0){
                    filename = i.getStringExtra("file");
                    downloadData(filename);
                }else {
                    Toast.makeText(MessageActivity.this, "SKL saat ini belum tersedia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadData(String lamp){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //diatas marshmellow kudu ada ijin
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                //kalo ga di ijinin
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show pop up dialog
                requestPermissions(permission, REQUEST_PERMISSION_CODE);
            }else{
                startDownloading(lamp);
            }
        }else{
            //dibawah marshmellow langsung download
            startDownloading(lamp);
        }
    }

    private void startDownloading(String lamp) {
        String url = LinkClass.URL_GET_FILE+lamp;

        filename = url.substring(url.lastIndexOf("/")+1);
        Log.d(TAG, "startDownloading: "+url);

        //download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //allow type of network to download
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(filename);
        request.setDescription("Mengunduh File...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+filename);
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startDownloading(filename);
                }else{
                    Toast.makeText(MessageActivity.this, "Permission Denied..!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
