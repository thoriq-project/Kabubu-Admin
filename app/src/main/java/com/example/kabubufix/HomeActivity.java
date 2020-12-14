package com.example.kabubufix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kabubufix.Budayawan.BudayawanActivity;
import com.example.kabubufix.Katalog.KatalogActivity;
import com.example.kabubufix.Kuis.SplashKuisActivity;
import com.example.kabubufix.Kutipan.KutipanActivity;
import com.example.kabubufix.Musik.MusikActivity;
import com.example.kabubufix.Puisi.PuisiActivity;
import com.example.kabubufix.Report.ReportActivity;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbarHome;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    private SliderLayout sliderLayout;

    private CardView cardViewKatalog, cardViewBudayawan, cardViewKutipan, cardViewQuiz,
                    cardViewMusik, cardViewPuisi;

    private Dialog popUpLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar-------------------------------------------------------------------------------------------
        toolbarHome = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbarHome);

        //PopUp Exit-------------------------------------------------------------------------------------------
        popUpLogout = new Dialog(HomeActivity.this);
        popUpLogout.setContentView(R.layout.popup_exit);
        popUpLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //Drawer--------------------------------------------------------------------------------------------
        drawerLayout = findViewById(R.id.home_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navView_home);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarHome,
                R.string.nav_draw_open, R.string.nav_draw_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //ImageSlider---------------------------------------------------------------------------------------
        sliderLayout = findViewById(R.id.home_slider_view);
        sliderLayout.setScrollTimeInSec(2);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        setSliderView();

        //CardView---------------------------------------------------------------------------------------
        cardViewBudayawan = findViewById(R.id.card_home_budayawan);
        cardViewKatalog = findViewById(R.id.card_home_katalog);
        cardViewKutipan = findViewById(R.id.card_home_kutipan);
        cardViewQuiz = findViewById(R.id.card_home_quiz);
        cardViewMusik = findViewById(R.id.card_home_musik);
        cardViewPuisi = findViewById(R.id.card_home_puisi);


        cardViewKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, KatalogActivity.class);
                startActivity(intent);

            }
        });

        cardViewBudayawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BudayawanActivity.class);
                startActivity(intent);
            }
        });

        cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SplashKuisActivity.class);
                startActivity(intent);
            }
        });

        cardViewKutipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, KutipanActivity.class);
                startActivity(intent);
            }
        });

        cardViewMusik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MusikActivity.class);
                startActivity(intent);
            }
        });

        cardViewPuisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, PuisiActivity.class);
                startActivity(intent);

            }
        });



    }

    private void setSliderView() {

        for (int i=0; i<=4; i++){

            DefaultSliderView sliderView = new DefaultSliderView(HomeActivity.this);

            switch (i){

                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/kabubufix.appspot.com/o/new%20slider%2Fslider1.png?alt=media&token=e907bb47-1e8b-4f33-aaa4-b4b6072bf82c");
                    sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case   1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/kabubufix.appspot.com/o/new%20slider%2Fslider2.png?alt=media&token=7695d4a2-ba73-454b-8ad5-1f60b1007719");
                    sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case   2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/kabubufix.appspot.com/o/new%20slider%2Fslider3.png?alt=media&token=6e76ab43-fc4b-456e-aeca-f9042398ed59");
                    sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case   3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/kabubufix.appspot.com/o/slide4.jpg?alt=media&token=3c9dccd8-113d-4162-a7c9-862641ac0ba5");
                    sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case   4:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/kabubufix.appspot.com/o/slide5.jpg?alt=media&token=c27c6c1f-4fda-44c4-8fc3-c250f7a95bb2");
                    sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {

           final Button yes = popUpLogout.findViewById(R.id.btn_keluar_ya);
           final Button no = popUpLogout.findViewById(R.id.btn_keluar_tidak);

           yes.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   finish();
                   System.exit(0);
               }
           });

           no.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   popUpLogout.dismiss();
               }
           });

           popUpLogout.show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.nav_logout) {

            final Button yes = popUpLogout.findViewById(R.id.btn_keluar_ya);
            final Button no = popUpLogout.findViewById(R.id.btn_keluar_tidak);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpLogout.dismiss();
                }
            });

            popUpLogout.show();


        } else if (item.getItemId() == R.id.nav_about){

            Intent intent = new Intent(HomeActivity.this, TentangActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.nav_report){

            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            startActivity(intent);

        }

        return false;
    }
}
