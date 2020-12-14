package com.example.kabubufix.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kabubufix.Listener.ItemClickListener;
import com.example.kabubufix.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


   public CircleImageView profileImage;
   public TextView textNama, textTempat, textTanggal;

   //buat nanti itemclicknya
   public ItemClickListener listener;

   public ProfileViewHolder(@NonNull View itemView) {
       super(itemView);

       profileImage = itemView.findViewById(R.id.img_profile_cardview);
       textNama = itemView.findViewById(R.id.txt_nama_profile_cardview);
       textTempat = itemView.findViewById(R.id.txt_tempatlahir_profile_cardview);
       textTanggal = itemView.findViewById(R.id.txt_tanggallahir_profile_cardview);

   }

   public void setItemClickListener(ItemClickListener listener){

       this.listener = listener;
   }

   @Override
   public void onClick(View v) {

       listener.onClick(v, getAdapterPosition(), false);

   }
}
