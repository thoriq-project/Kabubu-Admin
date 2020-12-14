package com.example.kabubufix.Puisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
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

import com.example.kabubufix.Helper.PuisiDownloadHelper;
import com.example.kabubufix.Holder.PuisiViewHolder;
import com.example.kabubufix.Model.Puisi;
import com.example.kabubufix.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class PuisiActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private Dialog popUpDialog;

    private Toolbar puisiToolbar;

    private DatabaseReference puisiRef;

    private RecyclerView recyclerViewPuisi;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puisi);

        progressBar = findViewById(R.id.pb_puisi_admin);

        puisiToolbar = findViewById(R.id.toolbar_puisi);
        setSupportActionBar(puisiToolbar);

        puisiRef = FirebaseDatabase.getInstance().getReference().child("Puisi");

        recyclerViewPuisi = findViewById(R.id.puisi_recycle_view);
        recyclerViewPuisi.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(PuisiActivity.this, 2);
        recyclerViewPuisi.setLayoutManager(layoutManager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_puisi, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_puisi){

            Toast.makeText(this, "Bisa !", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AddPuisiActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Puisi> options = new FirebaseRecyclerOptions.Builder<Puisi>()
                .setQuery(puisiRef, Puisi.class)
                .build();

        final FirebaseRecyclerAdapter<Puisi, PuisiViewHolder> adapter =
                new FirebaseRecyclerAdapter<Puisi, PuisiViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PuisiViewHolder puisiViewHolder, int i, @NonNull final Puisi puisi) {

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        Picasso.get().load(puisi.getGambar()).into(puisiViewHolder.imageViewPuisi);

                        popUpDialog = new Dialog(PuisiActivity.this);
                        popUpDialog.setContentView(R.layout.popup_puisi);
                        popUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



                        puisiViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ImageView popupClose = popUpDialog.findViewById(R.id.close_popup_puisi);
                                final ImageView popupSave = popUpDialog.findViewById(R.id.save_popup_puisi);
                                final ImageView popupImg = popUpDialog.findViewById(R.id.img_popup_puisi);
                                Picasso.get().load(puisi.getGambar()).into(popupImg);

                                popupClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popUpDialog.dismiss();
                                    }
                                });

                                popupSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (ActivityCompat.checkSelfPermission(PuisiActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                                PackageManager.PERMISSION_GRANTED){

                                            Toast.makeText(PuisiActivity.this, "Izinkan mengakses galeri!", Toast.LENGTH_SHORT).show();
                                            requestPermissions(new String[]{

                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE

                                            },PERMISSION_REQUEST_CODE);
                                            return;
                                        }
                                        else {

                                            ProgressDialog progressDialog = new ProgressDialog(PuisiActivity.this);
                                            progressDialog.show();


                                            String fileName = UUID.randomUUID().toString()+ ".jpg";
                                            Picasso.get().load(puisi.getGambar()).into( new PuisiDownloadHelper(getBaseContext(),progressDialog,
                                                    getApplicationContext().getContentResolver(),fileName, "Image Description"));
                                        }

                                    }
                                });

                                popUpDialog.show();
                            }
                        });

                        puisiViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence hapus[] = new CharSequence[] {
                                        "Hapus"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(PuisiActivity.this);
                                builder.setItems(hapus, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0){

                                            puisiRef.child("Puisi");
                                            puisiRef.child(puisi.getPuid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(PuisiActivity.this, "Puisi berhasil dihapus!", Toast.LENGTH_SHORT).show();
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
                    public PuisiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_puisi, parent,false);
                        PuisiViewHolder holder = new PuisiViewHolder(view);
                        return holder;
                    }
                };

        recyclerViewPuisi.setAdapter(adapter);
        adapter.startListening();
    }
}

