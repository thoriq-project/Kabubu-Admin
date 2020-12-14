package com.example.kabubufix.Budayawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.kabubufix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editNama, editTanggal, editTempat, editInfo;
    private ImageView editImage;

    private Toolbar toolbarEditProfile;

    private String profileID = "";
    private DatabaseReference profileRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editNama    = findViewById(R.id.edit_nama_profile);
        editTanggal = findViewById(R.id.edit_tanggallahir_profile);
        editTempat  = findViewById(R.id.edit_tempatlahir_profile);
        editInfo    = findViewById(R.id.edit_info_profile);
        editImage   = findViewById(R.id.img_edit_profile);

        toolbarEditProfile = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbarEditProfile);

        profileID = getIntent().getStringExtra("pid");

        profileRef = FirebaseDatabase.getInstance().getReference().child("Profiles").child(profileID);

        //retrieve
        displayProfileInfo();

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditProfileActivity.this, BudayawanActivity.class);
        startActivity(intent);
    }

    //Toolbar---------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.update_profile){

            applyChanges();

            Toast.makeText(this, "Data berhasil diperbarui!", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }


    //Retrieve--------------------------------------------------------------------------------------
    private void displayProfileInfo() {

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    //ambil datanya yg di database
                    String nama = dataSnapshot.child("nama").getValue().toString();
                    String tanggal = dataSnapshot.child("tanggal").getValue().toString();
                    String tempat = dataSnapshot.child("tempat").getValue().toString();
                    String info = dataSnapshot.child("info").getValue().toString();
                    String gambar = dataSnapshot.child("image").getValue().toString();

                    //pasang di masing2 fieldnya
                    editNama.setText(nama);
                    editTanggal.setText(tanggal);
                    editTempat.setText(tempat);
                    editInfo.setText(info);
                    Picasso.get().load(gambar).into(editImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Update data-----------------------------------------------------------------------------------
    private void applyChanges() {

        if (TextUtils.isEmpty(editNama.getText().toString())){

            Toast.makeText(this, "Nama harus diisi!", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(editTanggal.getText().toString())){

            Toast.makeText(this, "Tanggal harus diisi!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(editTempat.getText().toString())){

            Toast.makeText(this, "Tempat tidak boleh kosong", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(editInfo.getText().toString())){

            Toast.makeText(this, "Info harus diisi!", Toast.LENGTH_SHORT).show();

        }
        else {

            HashMap<String, Object> profileMap = new HashMap<>();

            profileMap.put("nama", editNama.getText().toString());
            profileMap.put("tempat", editTempat.getText().toString());
            profileMap.put("tanggal", editTanggal.getText().toString());
            profileMap.put("info", editInfo.getText().toString());

            profileRef.updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Intent intent = new Intent(EditProfileActivity.this, BudayawanActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }
                    });
        }
    }


}
