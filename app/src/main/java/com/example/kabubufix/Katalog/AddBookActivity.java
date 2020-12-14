package com.example.kabubufix.Katalog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.kabubufix.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddBookActivity extends AppCompatActivity {

    String  Title, Kategori, Description, Author, Link, saveCurrentDate,
            saveCurrentTime;

    ImageView addBook;
    EditText tJudul,tPenulis,tDeskripsi,tLink,tKategori;
    Toolbar toolbar;

    String BookRandomKey, downloadImageUrl;
    private StorageReference BookImageRef;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private DatabaseReference BookRef;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //Membuat Table Storage Di FireBase untuk menyimpan foto dan link buku

        BookImageRef = FirebaseStorage.getInstance().getReference().child("Book Images");


        //Membuat Database Books di FireBase yg isinya data data buku

        BookRef = FirebaseDatabase.getInstance().getReference().child("Books");


        //inisialisasi

        addBook = findViewById(R.id.img_add_book);
        tJudul = findViewById(R.id.txt_judul);
        tPenulis = findViewById(R.id.txt_penulis);
        tDeskripsi = findViewById(R.id.txt_deskripsi);
        tLink = findViewById(R.id.txt_link);
        tKategori = findViewById(R.id.txt_kategori);


        //inisialisasi Toolbar

        toolbar = findViewById(R.id.add_book_toolbar);
        setSupportActionBar(toolbar);

        //buat loading bar
        loadingBar = new ProgressDialog(this);




//        updateBookData(bookID);


        //pilih gambar buku
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenGallery();

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddBookActivity.this, KatalogActivity.class);
        startActivity(intent);
    }

    //Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_book){

            ValidateProductData();

        }

        return super.onOptionsItemSelected(item);
    }


    //membuka galeri
    private void OpenGallery() {

//        Intent galleryIntent = new Intent();
//        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent, GalleryPick);

        CropImage.activity(ImageUri).setAspectRatio(120,180)
                .start(AddBookActivity.this);

    }

    //mengambil gambar dari galeri

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == GalleryPick && resultCode == RESULT_OK && data!=null){
//            ImageUri = data.getData();
//            addBook.setImageURI(ImageUri);
//    }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
            && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri = result.getUri();
            addBook.setImageURI(ImageUri);
        }
        else {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddBookActivity.this, AddBookActivity.class));
            finish();
        }

    }


    // mengecek apakah field telah diisi semua
    private void ValidateProductData(){

        Title = tJudul.getText().toString();
        Author = tPenulis.getText().toString();
        Kategori = tKategori.getText().toString();
        Description = tDeskripsi.getText().toString();
        Link = tLink.getText().toString();

        if (ImageUri == null){
            Toast.makeText(this, "Pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Deskripsikan buku!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Author)){
            Toast.makeText(this, "Nama Penulis Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Title)){
            Toast.makeText(this, "Judul Harus Diisi!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Link)){
            Toast.makeText(this, "Masukan Link Penjual Buku!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Kategori)){
            Toast.makeText(this, "Masukan jenis kategori buku!", Toast.LENGTH_SHORT).show();
        }
        else {

            //jika data sudah diisi semua maka lakukan perintah ini
            StoreBookInformation();
        }
    }

//pembuatan tabel data buku ke firebase
    private void StoreBookInformation(){

        loadingBar.setTitle(("Menambahkan Buku Baru"));
        loadingBar.setMessage("Tunggu, buku sedang ditambahkan");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        //buat Primary key di database
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        BookRandomKey = saveCurrentDate + saveCurrentTime;


        // menaruh gambar di storage firebase
        final StorageReference filePath = BookImageRef.child(ImageUri.getLastPathSegment() + BookRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);


        // Pesan Kalau Salah Upload.....
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AddBookActivity.this, "Error:" + message , Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }


            //Pesan Kalau Upload Berhasil....
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddBookActivity.this, "Gambar Berhaasil Di Upload....", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();

                        }


                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddBookActivity.this, "Berhasil Mengambil URL Gambar Buku...", Toast.LENGTH_SHORT).show();

                            SaveBookInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    //proses penyimpanan info buku ke database
    private void SaveBookInfoToDatabase() {
        HashMap<String, Object> bookMap = new HashMap<>();
        bookMap.put("bid", BookRandomKey);
        bookMap.put("date", saveCurrentDate);
        bookMap.put("time", saveCurrentTime);
        bookMap.put("image", downloadImageUrl);
        bookMap.put("title", Title);
        bookMap.put("kategori", Kategori);
        bookMap.put("author", Author);
        bookMap.put("link", Link);
        bookMap.put("description", Description);

        BookRef.child(BookRandomKey).updateChildren(bookMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(AddBookActivity.this, KatalogActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddBookActivity.this, "Buku Sukses Ditambahkan!", Toast.LENGTH_SHORT).show();

                        }else {

                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddBookActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}