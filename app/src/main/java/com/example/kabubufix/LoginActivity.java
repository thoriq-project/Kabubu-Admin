package com.example.kabubufix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText t_user,t_pass;
    private TextView textViewBantuan;

    private Dialog popUpBantuan, popUpExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.button_login);
        t_user = (EditText)findViewById(R.id.txt_username);
        t_pass = (EditText)findViewById(R.id.txt_password);

        textViewBantuan = findViewById(R.id.txt_bantuan_login);

        popUpExit = new Dialog(LoginActivity.this);
        popUpExit.setContentView(R.layout.popup_exit);
        popUpExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popUpBantuan = new Dialog(LoginActivity.this);
        popUpBantuan.setContentView(R.layout.popup_login_help);
        popUpBantuan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textViewBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView close = popUpBantuan.findViewById(R.id.close_popup_login);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUpBantuan.dismiss();
                    }
                });

                popUpBantuan.show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t_user.getText().toString().equals("admin") && t_pass.getText().toString().equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Berhasil !", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Username atau Password Salah !", Toast.LENGTH_LONG).show();
                }}
            });

            }

    @Override
    public void onBackPressed() {
        final Button yes = popUpExit.findViewById(R.id.btn_keluar_ya);
        final Button no = popUpExit.findViewById(R.id.btn_keluar_tidak);

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
                popUpExit.dismiss();
            }
        });

        popUpExit.show();
    }

}