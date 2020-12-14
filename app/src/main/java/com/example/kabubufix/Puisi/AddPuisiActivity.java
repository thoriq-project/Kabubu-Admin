package com.example.kabubufix.Puisi;

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

public class AddPuisiActivity extends AppCompatActivity {


    private ImageView imgAddPuisi;
    private EditText editTextNamaPenyair, editTextJudulPuisi, editTextSearchPuisi;
    private Button buttonSavePuisi;

    private String Nama, Judul, Cari;
    private String downloadImageUrl, puisiRandomKey, saveCurrentDate, saveCurrentTime;

    private DatabaseReference puisiRef;
    private StorageReference puisiStore;

    private Uri imageUri;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_puisi);

        imgAddPuisi = findViewById(R.id.image_view_add_puisi);
        editTextNamaPenyair = findViewById(R.id.edit_nama_penyair);
        editTextJudulPuisi = findViewById(R.id.edit_judul_puisi);
        editTextSearchPuisi = findViewById(R.id.edit_search_puisi);
        buttonSavePuisi = findViewById(R.id.btn_save_puisi);

        puisiRef = FirebaseDatabase.getInstance().getReference().child("Puisi");
        puisiStore = FirebaseStorage.getInstance().getReference().child("Puisi Storage");

        buttonSavePuisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanDataPuisi();
            }
        });
        imgAddPuisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        progressDialog = new ProgressDialog(AddPuisiActivity.this);

    }

    private void pilihGambar() {

        CropImage.activity(imageUri)
                .setAspectRatio(200, 380)
                .start(AddPuisiActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
                resultCode == RESULT_OK && data != null) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imgAddPuisi.setImageURI(imageUri);
        }
    }

    private void simpanDataPuisi() {

        Nama = editTextNamaPenyair.getText().toString();
        Judul = editTextJudulPuisi.getText().toString();
        Cari = editTextSearchPuisi.getText().toString();

        if (imageUri == null){

            Toast.makeText(this, "Gambar belum dipilih!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Nama)){

            Toast.makeText(this, "Nama belum diisi!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Judul)){

            Toast.makeText(this, "Judul belum diisi!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Cari)){

            Toast.makeText(this, "Kolom search key belum diisi!", Toast.LENGTH_SHORT).show();
        } else {

            ambilSemuaData();
        }

    }

    private void ambilSemuaData() {

        progressDialog.setMessage("Tunggu, Data sedang diunggah");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        puisiRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = puisiStore.child(imageUri.getLastPathSegment() + puisiRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AddPuisiActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddPuisiActivity.this, "Gambar berhasil diupload!", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){

                            throw  task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddPuisiActivity.this, "Berhasil mengambil Url", Toast.LENGTH_SHORT).show();

                            simpanDataKeDatabase();

                        }

                    }
                });
            }
        });

    }

    private void simpanDataKeDatabase() {

        HashMap<String, Object> puisiMap = new HashMap<>();

        puisiMap.put("nama", Nama);
        puisiMap.put("puid", puisiRandomKey);
        puisiMap.put("judul", Judul);
        puisiMap.put("cari", Cari);
        puisiMap.put("gambar", downloadImageUrl);

        puisiRef.child(puisiRandomKey).updateChildren(puisiMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(AddPuisiActivity.this, PuisiActivity.class);
                            startActivity(intent);

                            progressDialog.dismiss();
                            Toast.makeText(AddPuisiActivity.this, "Puisi berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        } else {

                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddPuisiActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}

