package com.example.kabubufix.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kabubufix.R;

public class ReportActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3, imageView4;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

//        toolbar = findViewById(R.id.toolbar_report);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView1 = findViewById(R.id.img_rep1);
        imageView2 = findViewById(R.id.img_rep2);
        imageView3 = findViewById(R.id.img_rep3);
        imageView4 = findViewById(R.id.img_rep4);

        textClicked();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void textClicked(){

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReportActivity.this, Report1Activity.class);
                startActivity(intent);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReportActivity.this, Report3Activity.class);
                startActivity(intent);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReportActivity.this, Report2Activity.class);
                startActivity(intent);

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReportActivity.this, Report4Activity.class);
                startActivity(intent);

            }
        });

    }

}
