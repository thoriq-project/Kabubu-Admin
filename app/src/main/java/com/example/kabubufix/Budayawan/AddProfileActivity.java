package com.example.kabubufix.Budayawan;

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

import com.example.kabubufix.HomeActivity;
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

public class AddProfileActivity extends AppCompatActivity {

    ImageView inputImageProfile;
    EditText inputNama, inputTempalLahir, inputTanggalLahir, inputInfoProfile;
    Toolbar addProfileToolbar;

    //Variabel buat di validasi
    String Nama, Tempat, Tanggal, Info, saveCurrentDate, saveCurrentTime;

    //Variabel buat membuat Primary Key di database
    String  ProfileRandomKey;

    //Variabel untuk download URL gambar dari firebase
    String downloadImageUrl;

    //untuk penyimpanan gambar di storage database
    private StorageReference ProfileImageRef;
    private DatabaseReference ProfileRef;
    private Uri ImageUri;


    //untuk tampilan menunggu atau progres dialog
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        inputImageProfile = findViewById(R.id.img_add_profile);
        inputNama = findViewById(R.id.txt_nama_profile);
        inputTempalLahir = findViewById(R.id.txt_tempatlahir_profile);
        inputTanggalLahir = findViewById(R.id.txt_tanggallahir_profile);
        inputInfoProfile = findViewById(R.id.txt_info_profile);

        //Toolbar
        addProfileToolbar = findViewById(R.id.add_profile_toolbar);
        setSupportActionBar(addProfileToolbar);

        //storage penyimpanan gambar profile di firebase
        ProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        //tabel database untuk semua data
        ProfileRef = FirebaseDatabase.getInstance().getReference().child("Profiles");

        loadingBar = new ProgressDialog(this);

        inputImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BukaGalery();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddProfileActivity.this, BudayawanActivity.class);
        startActivity(intent);
    }


    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //beda cara inflatenya sama yang di fragment dan gaperlu setHasOptionMenu kalau di acticity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_profile){

            ValidateProfileData();
        }

        return super.onOptionsItemSelected(item);
    }



    private void BukaGalery(){

        CropImage.activity(ImageUri)
                .setAspectRatio(1,1)
                .start(AddProfileActivity.this);

    }

    //mengambil gambar dari galeri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
            resultCode == RESULT_OK && data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri = result.getUri();
            inputImageProfile.setImageURI(ImageUri);

        }

    }

    private void ValidateProfileData(){

        Nama = inputNama.getText().toString();
        Tempat = inputTempalLahir.getText().toString();
        Tanggal = inputTanggalLahir.getText().toString();
        Info = inputInfoProfile.getText().toString();

        if (ImageUri == null){

            Toast.makeText(this, "Gambar belum di pilih!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Nama)){
            Toast.makeText(this, "Nama harus diisi!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Tempat)){
            Toast.makeText(this, "Tempat lahir harus diisi!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Tanggal)){
            Toast.makeText(this, "Tanggal lahir harus diisi!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Info)){
            Toast.makeText(this, "Info profil budayawan harus diisi!", Toast.LENGTH_SHORT).show();
        }else{
            StoreProfileInformation();
        }

    }

    //penyiapan tempat
    private void  StoreProfileInformation(){
        loadingBar.setTitle(("Menambahkan Profile Baru"));
        loadingBar.setMessage("Mohon ditunggu...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProfileRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath =
                ProfileImageRef.child(ImageUri.getLastPathSegment() + ProfileRandomKey + ".jpg");

        //Proses upload data
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        //jika upload gagal
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AddProfileActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        })


        //jika upload berhasil
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AddProfileActivity.this, "Gambar berhasil di upload...", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(AddProfileActivity.this, "Berhasil mengambil URL gambar!", Toast.LENGTH_SHORT).show();

                            SimpanDataProfileKeDatabase();
                        }
                    }
                });
            }
        });

    }

    //simpan data
    private void SimpanDataProfileKeDatabase(){
        HashMap<String, Object> profileMap = new HashMap<>();

        //pembuatan tabel database
        profileMap.put("pid", ProfileRandomKey);
        profileMap.put("date", saveCurrentDate);
        profileMap.put("time", saveCurrentTime);
        profileMap.put("image", downloadImageUrl);
        profileMap.put("nama", Nama);
        profileMap.put("tempat", Tempat);
        profileMap.put("tanggal", Tanggal);
        profileMap.put("info", Info);

        ProfileRef.child(ProfileRandomKey).updateChildren(profileMap)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AddProfileActivity.this, BudayawanActivity.class);
                            startActivity(intent);
                            Toast.makeText(AddProfileActivity.this, "Profile sukses ditambahkan!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();


                        }else {



                            String message = task.getException().toString();
                            Toast.makeText(AddProfileActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

}
