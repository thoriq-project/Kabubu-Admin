package com.example.kabubufix.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kabubufix.Budayawan.BudayawanActivity;
import com.example.kabubufix.Model.Profile;
import com.example.kabubufix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetailActivity extends AppCompatActivity {

    CircleImageView imgProfileDetail;
    TextView namaPenulisDetail, infoPenulisDetail;

    private String profileID = "";

    DatabaseReference profileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        imgProfileDetail = findViewById(R.id.cardView_profile_detail);
        namaPenulisDetail = findViewById(R.id.txt_nama_profile_detail);
        infoPenulisDetail = findViewById(R.id.txt_info_profile_deetail);

        profileID = getIntent().getStringExtra("pid");

        getProfilesData(profileID);


    }


    private void getProfilesData(final String profileID) {
        profileRef = FirebaseDatabase.getInstance().getReference().child("Profiles");
        profileRef.child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    final Profile profile = dataSnapshot.getValue(Profile.class);

                    namaPenulisDetail.setText(profile.getNama());
                    infoPenulisDetail.setText(profile.getInfo());
                    Picasso.get().load(profile.getImage()).into(imgProfileDetail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
