package com.example.kabubufix.Musik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.example.kabubufix.HomeActivity;
import com.example.kabubufix.Model.Song;
import com.example.kabubufix.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MusikActivity extends AppCompatActivity {

    private Toolbar musikToolbar;

    private ListView musikListView;
    ArrayList<String> arrayListSongName = new ArrayList<>();
    ArrayList<String> arrayListSongUrl = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    private boolean checkPermission = false;
    private Uri uri;

    String songName, songUrl;

    String downloadSongUrl, saveCurrentDate, saveCurrentTime, songRandomKey;

    DatabaseReference songRef;
    StorageReference songStoreRef;

    ProgressDialog progressDialog;

    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musik);

        musikToolbar = findViewById(R.id.musik_toolbar);
        setSupportActionBar(musikToolbar);

        songRef = FirebaseDatabase.getInstance().getReference().child("Musik");
        songStoreRef = FirebaseStorage.getInstance().getReference().child("Musik Storage");

        progressDialog = new ProgressDialog(this);

        progressBar = findViewById(R.id.pb_musik_admin);

        musikListView = findViewById(R.id.musik_list_view);

        jcPlayerView = findViewById(R.id.jcplayer);

        retrieveSongs();

        musikListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                jcPlayerView.playAudio(jcAudios.get(position));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();

            }
        });


    }

    private void retrieveSongs() {

        songRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (progressBar != null){

                    progressBar.setVisibility(View.GONE);
                }

                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    Song songObj = ds.getValue(Song.class);
                    arrayListSongName.add(songObj.getName());
                    arrayListSongUrl.add(songObj.getLagu());

                    jcAudios.add(JcAudio.createFromURL(songObj.getName(),songObj.getLagu()));

                }

                arrayAdapter = new ArrayAdapter(MusikActivity.this, android.R.layout.simple_list_item_1, arrayListSongName){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView)view.findViewById(android.R.id.text1);

                        textView.setSingleLine(true);
                        textView.setMaxLines(1);

                        return view;
                    }
                };

                jcPlayerView.initPlaylist(jcAudios, null);
                musikListView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_musik, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.upload_musik){

            if (validatePermission()){

                pickSong();
            }

            Toast.makeText(this, "Bisa!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void pickSong() {

        Intent intentUpload = new Intent();
        intentUpload.setType("audio/*");
        intentUpload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentUpload, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == RESULT_OK){

                uri = data.getData();

                Cursor cursor = getApplicationContext().getContentResolver()
                        .query(uri, null, null, null, null);

                int indexedname = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                songName = cursor.getString(indexedname);
                cursor.close();

                uploadSongToFirebase();

            }
        }
    }

    private void uploadSongToFirebase() {

        progressDialog.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        songRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = songStoreRef.child(uri.getLastPathSegment() + songRandomKey + ".mp3");
        final UploadTask uploadTask = filePath.putFile(uri);



        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(MusikActivity.this, "Error." + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MusikActivity.this, "Musik berhasil di Upload!", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                        downloadSongUrl = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){

                            downloadSongUrl = task.getResult().toString();

                            Toast.makeText(MusikActivity.this, "Berhasil mengambil url lagu", Toast.LENGTH_SHORT).show();

                            simpanLaguKeDatabase();
                        }
                    }
                });
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                int currentProgress = (int)progress;
                progressDialog.setMessage("Upload loading : " +currentProgress+ "%");

            }
        });


    }

    private void simpanLaguKeDatabase() {

        HashMap<String, Object> songMap = new HashMap<>();

        songMap.put("sid", songRandomKey);
        songMap.put("name", songName);
        songMap.put("lagu", downloadSongUrl);

        songRef.child(songRandomKey).updateChildren(songMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(MusikActivity.this, HomeActivity.class);
                            startActivity(intent);

                            progressDialog.dismiss();
                            Toast.makeText(MusikActivity.this, "Musik Sukses Ditambahkan!", Toast.LENGTH_SHORT).show();

                        } else {

                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(MusikActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private boolean validatePermission () {

        Dexter.withActivity(MusikActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        checkPermission = true ;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        checkPermission = false;

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();

                    }
                }).check();
        return  checkPermission;
    }
}
