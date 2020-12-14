package com.example.kabubufix.Katalog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kabubufix.Detail.BookDetailActivity;
import com.example.kabubufix.Holder.BookViewHolder;
import com.example.kabubufix.HomeActivity;
import com.example.kabubufix.Model.Book;
import com.example.kabubufix.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class KatalogActivity extends AppCompatActivity {

    private DatabaseReference BookRef;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);

        Toolbar toolbar = findViewById(R.id.katalog_toolbar);
        setSupportActionBar(toolbar);

        //ProgressBar
        progressBar = findViewById(R.id.pb_katalog_admin);


        //Koneksi dan mengambil data dari database
        BookRef = FirebaseDatabase.getInstance().getReference().child("Books");

        //inisialisasi recycleview nya
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycle_view);
        mRecyclerView.setHasFixedSize(true);

        //mengatur jenis layout nya
        layoutManager = new GridLayoutManager(KatalogActivity.this, 3);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(KatalogActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //mengambil atau retrieve gambar dari database
    @Override
    public void onStart() {
        super.onStart();

        //mengambil data yang ada di class Book.java
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(BookRef, Book.class)
                        .build();

        final FirebaseRecyclerAdapter<Book, BookViewHolder> adapter =
                new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, int position, @NonNull final Book book) {

                        if (progressBar != null){

                            progressBar.setVisibility(View.GONE);

                        }

                        //Alhamdulillahirobbil Alamiinnnnn.... :D BISA
                        bookViewHolder.tTitle.setText(book.getTitle());
                        bookViewHolder.tCategory.setText(book.getKategori());
                        Picasso.get().load(book.getImage()).into(bookViewHolder.imgBook);

                        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(KatalogActivity.this, BookDetailActivity.class);
                                intent.putExtra("bid", book.getBid());
                                startActivity(intent);
                            }
                        });

                        bookViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence pilihan[] = new CharSequence[]{

                                        "Ubah",
                                        "Hapus"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(KatalogActivity.this);

                                builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //ubah
                                        if (which == 0) {

                                            //membuka file sesuai yg dipilih di katalog
                                            //dan menirim id agar bbisa di retrieve

                                            Intent intent = new Intent(KatalogActivity.this, EditBookActivity.class);
                                            intent.putExtra("bid", book.getBid());
                                            startActivity(intent);
                                        }


                                        //hapus
                                        if (which == 1) {

                                            BookRef.child("Books");
                                            BookRef.child(book.getBid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(KatalogActivity.this, "Buku berhasil di hapus!", Toast.LENGTH_SHORT).show();
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
                    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_katalog, parent, false);
                        BookViewHolder holder = new BookViewHolder(view);

                        return holder;
                    }
                };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    //Toolbar------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_katalog, menu);


        MenuItem item = menu.findItem(R.id.search_katalog);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView)
                MenuItemCompat.getActionView(item);

        searchView.setQueryHint("Cari buku disini ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!TextUtils.isEmpty(query.trim())) {

                    searchBook(query);


                } else {

                    onStart();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String querys) {

                if (!TextUtils.isEmpty(querys.trim())) {

                    searchBook(querys);


                } else {

                    onStart();

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.add_book) {

            Toast.makeText(KatalogActivity.this, "Menambahkan buku...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(KatalogActivity.this, AddBookActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    //Search--------------------------------------------------------------------------------------
    private void searchBook(String searchInput) {


        Query searchQuery = BookRef.orderByChild("title").startAt(searchInput).endAt(searchInput + "\uf8ff");

        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(searchQuery, Book.class)
                        .build();

        FirebaseRecyclerAdapter<Book, BookViewHolder> adapter =
                new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, int position, @NonNull final Book book) {


                        //Alhamdulillahirobbil Alamiinnnnn.... :D BISA
                        bookViewHolder.tTitle.setText(book.getTitle());
                        bookViewHolder.tCategory.setText(book.getKategori());
                        Picasso.get().load(book.getImage()).into(bookViewHolder.imgBook);

                        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(KatalogActivity.this, BookDetailActivity.class);
                                intent.putExtra("bid", book.getBid());
                                startActivity(intent);
                            }
                        });

                        bookViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                CharSequence pilihan[] = new CharSequence[]{

                                        "Ubah",
                                        "Hapus"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(KatalogActivity.this);

                                builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //ubah
                                        if (which == 0) {

                                            //membuka file sesuai yg dipilih di katalog
                                            //dan menirim id agar bbisa di retrieve

                                            Intent intent = new Intent(KatalogActivity.this, EditBookActivity.class);
                                            intent.putExtra("bid", book.getBid());
                                            startActivity(intent);
                                        }


                                        //hapus
                                        if (which == 1) {

                                            BookRef.child("Books");
                                            BookRef.child(book.getBid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(KatalogActivity.this, "Buku berhasil di hapus!", Toast.LENGTH_SHORT).show();
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
                    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_katalog, parent, false);
                        BookViewHolder holder = new BookViewHolder(view);

                        return holder;
                    }
                };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}

