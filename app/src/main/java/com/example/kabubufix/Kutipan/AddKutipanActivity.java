package com.example.kabubufix.Kutipan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class AddKutipanActivity extends AppCompatActivity {

    private ImageView imageViewAddKutipan;
    private EditText editTextNamaTokoh;
    private Button buttonSimpanKutipan;

    private String Nama;

    private DatabaseReference KutipanRef;
    private StorageReference imageKutipanRef;

    //simpan gambar sementara
    private Uri imageUri;

    private ProgressDialog loadingBar;

    //buat task upload dan simpan data
    private String saveCurrentDate,saveCurrentTime, kutipanRandomKey;
    private String downloadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kutipan);

        imageViewAddKutipan = findViewById(R.id.image_view_add_kutipan);
        editTextNamaTokoh = findViewById(R.id.txt_nama_tokoh);
        buttonSimpanKutipan = findViewById(R.id.button_simpan_kutipan);

        loadingBar = new ProgressDialog(this);

        KutipanRef = FirebaseDatabase.getInstance().getReference().child("Kutipan");
        imageKutipanRef = FirebaseStorage.getInstance().getReference().child("Kutipan Images");

        //ambil gambar
        imageViewAddKutipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGambar();
            }
        });

        buttonSimpanKutipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });


    }

    //-----------------------------------------------------------------------------------------------------------
    private void ambilGambar() {

        CropImage.activity(imageUri)
                .setAspectRatio(170,300)
                .start(AddKutipanActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
            resultCode == RESULT_OK && data != null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageViewAddKutipan.setImageURI(imageUri);
        }
    }

    private void simpanData() {

        Nama = editTextNamaTokoh.getText().toString();

        if (TextUtils.isEmpty(Nama)){

            Toast.makeText(this, "Mohon isi kolom Nama Tokoh!", Toast.LENGTH_SHORT).show();

        } else if (imageUri == null){

            Toast.makeText(this, "Pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show();

        } else {

            ambilSemuaData();
        }

    }

    private void ambilSemuaData() {

        loadingBar.setTitle(("Menambahkan Kutipan Baru"));
        loadingBar.setMessage("Mohon ditunggu...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        kutipanRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath =
                imageKutipanRef.child(imageUri.getLastPathSegment() + kutipanRandomKey + ".jpg");

        //Proses upload data
        final UploadTask uploadTask = filePath.putFile(imageUri);

        //jika upload gagal
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AddKutipanActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

                }})

                //jika upload berhasil
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(AddKutipanActivity.this, "Gambar berhasil di upload...", Toast.LENGTH_SHORT).show();

                        //perintah lanjutan setelah task upload berhasil
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                //perintah selanjutnya
                                if (!task.isSuccessful()){
                                    throw task.getException();
                                }
                                downloadImageUrl = filePath.getDownloadUrl().toString();
                                return filePath.getDownloadUrl();
                            }})

                                //jika task sudah selesai
                                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {

                                        //mengambil link gambar dari firebase
                                        if (task.isSuccessful()){
                                            downloadImageUrl = task.getResult().toString();
                                            Toast.makeText(AddKutipanActivity.this, "Berhasil mengambil URL gambar!", Toast.LENGTH_SHORT).show();

                                            SimpanDataKutipanKeDatabase();
                                        }
                                    }
                                });
                    }
                });


    }

    private void SimpanDataKutipanKeDatabase() {

        HashMap <String, Object> kutipanMap = new HashMap<>();

        kutipanMap.put("kid", kutipanRandomKey);
        kutipanMap.put("name", Nama);
        kutipanMap.put("image", downloadImageUrl);

        KutipanRef.child(kutipanRandomKey).updateChildren(kutipanMap)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AddKutipanActivity.this, KutipanActivity.class);
                            startActivity(intent);
                            Toast.makeText(AddKutipanActivity.this, "Kutipan sukses ditambahkan!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();


                        }else {



                            String message = task.getException().toString();
                            Toast.makeText(AddKutipanActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });

    }
}
