package com.example.kabubufix.Katalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class EditBookActivity extends AppCompatActivity {

    private ImageView editImage;
    private EditText editJudul, editPenulis, editKategori, editLink, editDesk;

    private Toolbar toolbarEdit_book;

    private String bookID = "";
//    private String downloadImageUrl;

    private DatabaseReference bookRef;

//    private static final int galleryPick = 1;
//    private Uri imageUri;

//    private StorageReference bookImageRef;
//    private StorageTask uploadTask;
//    private String myUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editImage = findViewById(R.id.img_edit_book);
        editJudul = findViewById(R.id.txt_judul_edit);
        editPenulis = findViewById(R.id.txt_penulis_edit);
        editKategori = findViewById(R.id.txt_kategori_edit);
        editLink = findViewById(R.id.txt_link_edit);
        editDesk = findViewById(R.id.txt_deskripsi_edit);

        //mengambil intent yg dikasih index ubah di katalogfragment
        bookID = getIntent().getStringExtra("bid");

//        bookImageRef = FirebaseStorage.getInstance().getReference().child("Book Images");

        bookRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookID);

        toolbarEdit_book = findViewById(R.id.edit_book_toolbar);
        setSupportActionBar(toolbarEdit_book);

        //retrieve data atau info buku
        displayBookInfo();


        //pilih gambar
//        editImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openGallery();
//            }
//        });
    }

//    private void openGallery() {
//
//        Intent galleryIntent = new Intent();
//        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent, galleryPick);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
//
//            imageUri = data.getData();
//            editImage.setImageURI(imageUri);
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditBookActivity.this, KatalogActivity.class);
        startActivity(intent);
    }

    //simpan data yang sudah diinput ke databse
    private void applyChanges() {

        if (TextUtils.isEmpty((editJudul.getText().toString()))) {

            Toast.makeText(this, "Judul harus diisi...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editPenulis.getText().toString())) {

            Toast.makeText(this, "Penulis harus diisi...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editKategori.getText().toString())) {

            Toast.makeText(this, "Kategori harus diisi...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editLink.getText().toString())) {

            Toast.makeText(this, "Link harus diisi...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editDesk.getText().toString())) {

            Toast.makeText(this, "Deskripsi harus diisi...", Toast.LENGTH_SHORT).show();
        } else {

            HashMap<String, Object> bookMap = new HashMap<>();
            bookMap.put("title", editJudul.getText().toString());
            bookMap.put("author", editPenulis.getText().toString());
            bookMap.put("kategori", editKategori.getText().toString());
            bookMap.put("link", editLink.getText().toString());
            bookMap.put("description", editDesk.getText().toString());

            bookRef.updateChildren(bookMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){


                                Intent intent = new Intent(EditBookActivity.this, KatalogActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

        }
    }





        //retrieve or read data
        private void displayBookInfo () {

            bookRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        String judul = dataSnapshot.child("title").getValue().toString();
                        String penulis = dataSnapshot.child("author").getValue().toString();
                        String kategori = dataSnapshot.child("kategori").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();
                        String deskripsi = dataSnapshot.child("description").getValue().toString();
                        String gambar = dataSnapshot.child("image").getValue().toString();

                        editJudul.setText(judul);
                        editPenulis.setText(penulis);
                        editKategori.setText(kategori);
                        editLink.setText(link);
                        editDesk.setText(deskripsi);

                        Picasso.get().load(gambar).into(editImage);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        //Toolbar
        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit_book, menu);

            return super.onCreateOptionsMenu(menu);

        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            if (item.getItemId() == R.id.update_book) {

                applyChanges();

                Toast.makeText(this, "Data berhasil di Update!", Toast.LENGTH_LONG).show();
            }

            return super.onOptionsItemSelected(item);
        }


    }


