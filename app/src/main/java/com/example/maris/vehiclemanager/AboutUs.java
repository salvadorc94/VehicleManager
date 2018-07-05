package com.example.maris.vehiclemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class AboutUs extends AppCompatActivity {

    //ImageSwitcher
    ImageButton prev, next;
    ImageSwitcher imageSwitcher;
    Integer [] images_about = {R.drawable.about_j, R.drawable.about_m, R.drawable.about_s, R.drawable.about_g};
    int i=0; //contador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Inicializando para ImageSwitcher
        imageSwitcher = findViewById(R.id.imgsw);
        prev = findViewById(R.id.btn_prev_about_us);
        next = findViewById(R.id.btn_next_about_us);

        //ImageSwitcher
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        //Animation
        final Animation in = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.in);
        final Animation out = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.out);
        final Animation in2 = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.in2);
        final Animation out2 = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.out2);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setInAnimation(in);
                imageSwitcher.setOutAnimation(out);
                if (i > 0){
                    i--;
                    imageSwitcher.setImageResource(images_about[i]);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setInAnimation(in2);
                imageSwitcher.setOutAnimation(out2);
                if (i < images_about.length - 1){
                    i++;
                    imageSwitcher.setImageResource(images_about[i]);
                }
            }
        });

    }
}
