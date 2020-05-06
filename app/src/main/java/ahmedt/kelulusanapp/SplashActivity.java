package ahmedt.kelulusanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgSmk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgSmk = findViewById(R.id.img_smk);
        Glide.with(this)
                .load(R.drawable.logo_md)
                .apply(new RequestOptions().override(300   , 300))
                .into(imgSmk);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);

                View shareView = imgSmk;
                String transitionName = getString(R.string.foto);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                    finish();
                }else {
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);

    }
}
