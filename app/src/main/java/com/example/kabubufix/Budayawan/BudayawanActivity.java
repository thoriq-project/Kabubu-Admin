package com.example.kabubufix.Budayawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kabubufix.Detail.ProfileDetailActivity;
import com.example.kabubufix.Holder.ProfileViewHolder;
import com.example.kabubufix.HomeActivity;
import com.example.kabubufix.Model.Profile;
import com.example.kabubufix.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class BudayawanActivity extends AppCompatActivity {

    private DatabaseReference ProfileRef;

    private RecyclerView mRecycleView;
    RecyclerView.LayoutManager layoutManager;


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budayawan);


        //this crazy code is untuk inisialisasi app compat toolbar ke fragment alhamdulillahhhhhh..... :)
        //and dont forget the import for teh adroidx widget above
        Toolbar toolbar = findViewById(R.id.budayawan_toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi recycleview nya
        mRecycleView = findViewById(R.id.rv_budayawan);
        mRecycleView.setHasFixedSize(true);

        //mengatur jenis layout nya
        layoutManager = new LinearLayoutManager(BudayawanActivity.this);
        mRecycleView.setLayoutManager(layoutManager);

        //Inisialisai Database
        ProfileRef = FirebaseDatabase.getInstance().getReference().child("Profiles");

        //ProgressBar
        progressBar = findViewById(R.id.pb_budayawan_admin);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(BudayawanActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    //Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar Toolbar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_budayawan, menu);

        MenuItem item = menu.findItem(R.id.search_budayawan);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setQueryHint("Cari budayawan...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!TextUtils.isEmpty(query.trim())){

                    searchProfileQuery(query);

                } else {

                    onStart();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!TextUtils.isEmpty(newText.trim())){

                    searchProfileQuery(newText);

                } else {

                    onStart();
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    // Query Cari Profile
    private void searchProfileQuery(String searchInput) {

        Query queryProfile = ProfileRef.orderByChild("nama").startAt(searchInput).endAt(searchInput + "\uf8ff");

        //Mengambil data dari Profile.class
        FirebaseRecyclerOptions<Profile> options =
                new FirebaseRecyclerOptions.Builder<Profile>()
                        .setQuery(queryProfile, Profile.class)
                        .build();

        //membuat apater untuk memasukan data dari firebase ke recycleview
        // PS : Membutuhkan 2 parameter atau Class
        final FirebaseRecyclerAdapter<Profile, ProfileViewHolder> adapter =
                new FirebaseRecyclerAdapter<Profile, ProfileViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i, @NonNull final Profile profile) {

                        if (progressBar != null){

                            progressBar.setVisibility(View.GONE);

                        }

                        profileViewHolder.textNama.setText(profile.getNama());
                        profileViewHolder.textTempat.setText(profile.getTempat());
                        profileViewHolder.textTanggal.setText(profile.getTanggal());

                        Picasso.get().load(profile.getImage()).into(profileViewHolder.profileImage);


                        profileViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BudayawanActivity.this, ProfileDetailActivity.class);
                                intent.putExtra("pid", profile.getPid());
                                startActivity(intent);
                            }
                        });

                        profileViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence opsi [] = new CharSequence[]{

                                        "Ubah",
                                        "Hapus"

                                } ;

                                AlertDialog.Builder builder = new AlertDialog.Builder(BudayawanActivity.this);

                                builder.setItems(opsi, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0){

                                            Intent intent = new Intent(BudayawanActivity.this, EditProfileActivity.class);
                                            intent.putExtra("pid", profile.getPid());
                                            startActivity(intent);

                                        }

                                        if (which == 1){

                                            ProfileRef.child("Profiles");
                                            ProfileRef.child(profile.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(BudayawanActivity.this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
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
                    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_budayawan, parent, false);
                        ProfileViewHolder holder = new ProfileViewHolder(v);

                        return holder;
                    }
                };
        mRecycleView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_profile) {

            Intent i = new Intent(BudayawanActivity.this, AddProfileActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    //RecycleView RecycleView RecycleView RecycleView RecycleView RecycleView RecycleView
    //mulai me retrieve

    @Override
    public void onStart() {
        super.onStart();

        //Mengambil data dari Profile.class
        FirebaseRecyclerOptions<Profile> options =
                new FirebaseRecyclerOptions.Builder<Profile>()
                        .setQuery(ProfileRef, Profile.class)
                        .build();

        //membuat apater untuk memasukan data dari firebase ke recycleview
        // PS : Membutuhkan 2 parameter atau Class
        final FirebaseRecyclerAdapter<Profile, ProfileViewHolder> adapter =
                new FirebaseRecyclerAdapter<Profile, ProfileViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i, @NonNull final Profile profile) {

                        if (progressBar != null){

                            progressBar.setVisibility(View.GONE);

                        }

                        profileViewHolder.textNama.setText(profile.getNama());
                        profileViewHolder.textTempat.setText(profile.getTempat());
                        profileViewHolder.textTanggal.setText(profile.getTanggal());

                        Picasso.get().load(profile.getImage()).into(profileViewHolder.profileImage);


                        profileViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BudayawanActivity.this, ProfileDetailActivity.class);
                                intent.putExtra("pid", profile.getPid());
                                startActivity(intent);
                            }
                        });

                        profileViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence opsi [] = new CharSequence[]{

                                        "Ubah",
                                        "Hapus"

                                } ;

                                AlertDialog.Builder builder = new AlertDialog.Builder(BudayawanActivity.this);

                                builder.setItems(opsi, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0){

                                            Intent intent = new Intent(BudayawanActivity.this, EditProfileActivity.class);
                                            intent.putExtra("pid", profile.getPid());
                                            startActivity(intent);

                                        }

                                        if (which == 1){

                                            ProfileRef.child("Profiles");
                                            ProfileRef.child(profile.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(BudayawanActivity.this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
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
                    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_budayawan, parent, false);
                        ProfileViewHolder holder = new ProfileViewHolder(v);

                        return holder;
                    }
                };
        mRecycleView.setAdapter(adapter);
        adapter.startListening();
    }
}
