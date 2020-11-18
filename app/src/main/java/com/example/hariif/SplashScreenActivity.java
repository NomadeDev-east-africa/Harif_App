package com.example.hariif;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Animation topanim,bottomanim;
ImageView logo;
TextView brand, copyright,info,Middle;
public static int Splash_screen= 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

    topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

info=findViewById(R.id.Info);
logo=findViewById(R.id.Logo);
brand=findViewById(R.id.Brands);
copyright=findViewById(R.id.Copyright);
Middle=findViewById(R.id.Middle);

logo.setAnimation(topanim);
info.setAnimation(bottomanim);
copyright.setAnimation(bottomanim);
brand.setAnimation(bottomanim);
Middle.setAnimation(bottomanim);



new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(SplashScreenActivity.this , LoginActivity.class);
        Pair[]pairs=new Pair[2];pairs[0]=new Pair<View, String>(logo,"logo_image");pairs[1]=new Pair<View, String>(brand,"logo_text");
        //wrap the call in API level 21 or higher
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
    }
    },Splash_screen);

    }
}
