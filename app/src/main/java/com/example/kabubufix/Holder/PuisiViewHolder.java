package com.example.kabubufix.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kabubufix.Listener.ItemClickListener;
import com.example.kabubufix.R;

public class PuisiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageViewPuisi;
    public ItemClickListener listener;


    public PuisiViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewPuisi = itemView.findViewById(R.id.image_view_puisi);

    }

    public void setItemClickListener (ItemClickListener listener){
        this.listener = listener;

    }


    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(), false);

    }
}
