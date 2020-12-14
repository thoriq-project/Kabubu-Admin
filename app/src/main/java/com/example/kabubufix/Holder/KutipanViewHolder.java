package com.example.kabubufix.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kabubufix.Listener.ItemClickListener;
import com.example.kabubufix.R;

import org.w3c.dom.Text;

public class KutipanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageViewKutipan;
    public ItemClickListener listener;


    public KutipanViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewKutipan = itemView.findViewById(R.id.image_view_kutipan);

    }

    public void setItemClickListener(ItemClickListener listener) {

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(), false);

    }


}
