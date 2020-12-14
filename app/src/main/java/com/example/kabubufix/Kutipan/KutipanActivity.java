package com.example.kabubufix.Kutipan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.kabubufix.Helper.KutipanDownloadHelper;
import com.example.kabubufix.Holder.KutipanViewHolder;
import com.example.kabubufix.Model.Kutipan;
import com.example.kabubufix.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class KutipanActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private RecyclerView recyclerViewKutipan;
    private RecyclerView.LayoutManager layoutManagerKutipan;

    private DatabaseReference KutipanRef;

    private Dialog popUpDialog;

    private Toolbar toolbarKutipan;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kutipan);

        progressBar = findViewById(R.id.pb_kutipan_admin);

        recyclerViewKutipan = findViewById(R.id.kutipan_recycle_view);
        recyclerViewKutipan.setHasFixedSize(true);

        layoutManagerKutipan = new GridLayoutManager(KutipanActivity.this, 2);
        recyclerViewKutipan.setLayoutManager(layoutManagerKutipan);

        KutipanRef = FirebaseDatabase.getInstance().getReference().child("Kutipan");

        toolbarKutipan = findViewById(R.id.toolbar_kutipan);
        setSupportActionBar(toolbarKutipan);


        //PERMISSION UNTUK DOWNLOAD IMAGE
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//        PackageManager.PERMISSION_GRANTED) requestPermissions(new String[]{
//
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//        },PERMISSION_REQUEST_CODE);

    }

    //Toolbar --------------------------------------------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_kutipan, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_kutipan){

            Intent intent = new Intent(KutipanActivity.this, AddKutipanActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // setting recycle view berdasarkan data yang di ambil di database
        // sebelum ke sini bikin model dulu di kelas lain
        // contohnya disini bikin kutipan.java dulu,
        // isinya variable untuk nyimpen data dari firebase

        FirebaseRecyclerOptions<Kutipan> options =
                new FirebaseRecyclerOptions.Builder<Kutipan>()
                        .setQuery(KutipanRef, Kutipan.class)
                        .build();

        //disini yang diambil holder sama model nya

        final FirebaseRecyclerAdapter<Kutipan, KutipanViewHolder> adapter =
                new FirebaseRecyclerAdapter<Kutipan, KutipanViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final KutipanViewHolder kutipanViewHolder, int i, @NonNull final Kutipan kutipan) {

                        if (progressBar != null){

                            progressBar.setVisibility(View.GONE);

                        }

                        Picasso.get().load(kutipan.getImage()).into(kutipanViewHolder.imageViewKutipan);

                        popUpDialog = new Dialog(KutipanActivity.this);
                        popUpDialog.setContentView(R.layout.popup_kutipan);
                        popUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        kutipanViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final ImageView popupClose = popUpDialog.findViewById(R.id.close_popup_kutipan);
                                final ImageView popupSave = popUpDialog.findViewById(R.id.save_popup_kutipan);
                                final ImageView popupImg = popUpDialog.findViewById(R.id.img_popup_kutipan);
                                Picasso.get().load(kutipan.getImage()).into(popupImg);

                                popupClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popUpDialog.dismiss();
                                    }
                                });

                                popupSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (ActivityCompat.checkSelfPermission(KutipanActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                                PackageManager.PERMISSION_GRANTED){

                                            Toast.makeText(KutipanActivity.this, "Izinkan mengakses galeri!", Toast.LENGTH_SHORT).show();
                                            requestPermissions(new String[]{

                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE

                                            },PERMISSION_REQUEST_CODE);
                                            return;
                                        }
                                        else {

                                            ProgressDialog progressDialog = new ProgressDialog(KutipanActivity.this);
                                            progressDialog.show();


                                            String fileName = UUID.randomUUID().toString()+ ".jpg";
                                            Picasso.get().load(kutipan.getImage()).into( new KutipanDownloadHelper(getBaseContext(),progressDialog,
                                                    getApplicationContext().getContentResolver(),fileName, "Image Description"));
                                        }

                                    }
                                });

                                popUpDialog.show();

                            }
                        });

                        kutipanViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence pilihan[] = new CharSequence[] {

                                        "Hapus"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(KutipanActivity.this);

                                builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0) {

                                            KutipanRef.child("Kutipan");
                                            KutipanRef.child(kutipan.getKid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(KutipanActivity.this, "Kutipan berhasil dihapus!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });


                                        }
                                    }
                                });
                                builder.show();
                                return false;
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public KutipanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_kutipan, parent, false);
                        KutipanViewHolder holder = new KutipanViewHolder(view);

                        return holder;
                    }
                };

        recyclerViewKutipan.setAdapter(adapter);
        adapter.startListening();
    }



}


