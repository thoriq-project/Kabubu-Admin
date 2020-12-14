package com.example.kabubufix.Holder;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kabubufix.Listener.ItemClickListener;
import com.example.kabubufix.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Filterable {

    public TextView tTitle, tCategory;
    public ImageView imgBook;
    public ItemClickListener listener;


    public BookViewHolder(@NonNull View itemView) {
        super(itemView);

        imgBook = itemView.findViewById(R.id.book_img_id);
        tTitle = itemView.findViewById(R.id.txt_title);
        tCategory = itemView.findViewById(R.id.txt_cat);
    }

    public void setItemClickListener(ItemClickListener listener) {

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(),false);

    }


    @Override
    public Filter getFilter() {
        return null;
    }
}
