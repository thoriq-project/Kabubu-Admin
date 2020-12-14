package com.example.kabubufix.Kuis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kabubufix.HomeActivity;
import com.example.kabubufix.R;


public class KuisActivity extends AppCompatActivity {

    private ImageButton buttonMulai;
    private ImageView imageViewBack, imageViewHelp;

    private Dialog popUpHelp, popUpExit;

    private static final int REQUEST_CODE_QUIZ = 1;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewSkorTertinggi;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuis);



        textViewSkorTertinggi = findViewById(R.id.txt_score_tertinggi);
        loadHighScore();

        imageViewBack = findViewById(R.id.keluar_kuis);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        buttonMulai = findViewById(R.id.button_mulai_kuis);
        buttonMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mulaiQuiz();

            }
        });

        //popup keluar
        popUpExit = new Dialog(KuisActivity.this);
        popUpExit.setContentView(R.layout.popup_exit);
        popUpExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popUpHelp = new Dialog(KuisActivity.this);
        popUpHelp.setContentView(R.layout.popup_quiz_help);
        popUpHelp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imageViewHelp = findViewById(R.id.img_kuis_help);
        imageViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ImageView closeButton = popUpHelp.findViewById(R.id.close_popup_quiz);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUpHelp.dismiss();
                    }
                });
                popUpHelp.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        final Button ya = popUpExit.findViewById(R.id.btn_keluar_ya);
        final Button tidak = popUpExit.findViewById(R.id.btn_keluar_tidak);

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KuisActivity.super.onBackPressed();
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpExit.dismiss();
            }
        });

        popUpExit.show();
    }

    private void mulaiQuiz() {


        Intent intent = new Intent(KuisActivity.this, MulaiKuisActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(MulaiKuisActivity.EXTRA_SCORE, 0);
                if (score   > highscore){

                    updateHighScore(score);
                }
            }
        }
    }

    private void loadHighScore(){

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE,0);
        textViewSkorTertinggi.setText(highscore + "00");
    }

    private void updateHighScore(int highscoreNew) {

        highscore = highscoreNew;
        textViewSkorTertinggi.setText(highscore + "00");

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();

    }
}
