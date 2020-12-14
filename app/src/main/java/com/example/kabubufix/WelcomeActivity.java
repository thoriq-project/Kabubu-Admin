package com.example.kabubufix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kabubufix.Model.Slider;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    TabLayout tabLayoutIndicator;

    private Button nextButton;
    private Button masukButton;
    private Animation btnAnim;

    private int position  = 0;

    OnboardSliderAdapter onBoardSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //saat aplikasi sudah terinstall sebelumnya atau belum

        if (restorePrefData()) {
            Intent userhomeactivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(userhomeactivity);
            finish();

        }


        tabLayoutIndicator = findViewById(R.id.tabLayout);
        nextButton = findViewById(R.id.btn_next_ob);
        masukButton = findViewById(R.id.buttonMasuk);


        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_masuk_animation);

        masukButton.setVisibility(View.INVISIBLE);

        final List<Slider> mList = new ArrayList<>();
        mList.add(new Slider("Selamat Datang \n di Kabubu", "Aplikasi katalog buku \\n budayawan Indonesia",R.drawable.ob1));
        mList.add(new Slider("Katalog Buku", "Katalog buku lengkap karya para budayawan di Indonesia, dan bisa dibeli melalui link yang tersedia.",R.drawable.ob2));
        mList.add(new Slider("Budayawan Indonesia", "Terdapat informasi mengenai budayawan-budayawan Indonesia,biografi singkat, karya puisi, musik dan kutipan" +
                "yang berisi kata-kata bijak yang bisa diunduh secara gratis",R.drawable.ob3));

        mList.add(new Slider("Quiz", "Permainan kuis sederhana seputar budayawan Indonesia dan karya-karyanya.",R.drawable.ob4));

        viewPager = findViewById(R.id.viewPager_Ob);
        onBoardSliderAdapter = new OnboardSliderAdapter(this,mList);
        viewPager.setAdapter(onBoardSliderAdapter);

        //set tablayout dengan viewpager
        tabLayoutIndicator.setupWithViewPager(viewPager);


        //button dan text inisialize
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = viewPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    viewPager.setCurrentItem(position);


                }

                if  (position == mList.size()-1){ //slide akhir

                    loadLastScreen();



                }

            }
        });

        masukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                savePfresData();
                finish();

            }
        });



        tabLayoutIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1){

                    loadLastScreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActOpenedBefore;

    }

    private void savePfresData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();

    }

    private void loadLastScreen() {

        nextButton.setVisibility(View.INVISIBLE);
        tabLayoutIndicator.setVisibility(View.INVISIBLE);
        masukButton.setVisibility(View.VISIBLE);
        masukButton.setAnimation(btnAnim);

    }
}
