package com.example.kabubufix.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kabubufix.Katalog.KatalogActivity;
import com.example.kabubufix.Model.Book;
import com.example.kabubufix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    ImageView bukuImg;

    TextView Tjudul, Tpenulis, Tkategori, Tdesk;

    DatabaseReference bookRef;

    private String bukuID = "";

    Button bBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bukuImg = findViewById(R.id.img_book_detail);


        Tjudul = findViewById(R.id.txt_judul_detail);
        Tpenulis = findViewById(R.id.txt_penulis_detail);
        Tdesk = findViewById(R.id.txt_deskripsi_detail);
        Tkategori = findViewById(R.id.txt_kategori_detail);
        bBuy = findViewById(R.id.button_buy);

        bukuID = getIntent().getStringExtra("bid");

        getBookDetails(bukuID);

    }



    private void getBookDetails(final String bukuID) {

        bookRef = FirebaseDatabase.getInstance().getReference().child("Books");
        bookRef.child(bukuID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    final Book buku = dataSnapshot.getValue(Book.class);

                    Tjudul.setText(buku.getTitle());
                    Tpenulis.setText(buku.getAuthor());
                    Tkategori.setText(buku.getKategori());
                    Tdesk.setText(buku.getDescription());

                    Picasso.get().load(buku.getImage()).into(bukuImg);


                    // ALHAMDULILLAAAHHHHHH :))))))
                    bBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(buku.getLink()));
                            startActivity(intent);
                        }
                    });



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
