package com.example.kabubufix.Helper;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class PuisiDownloadHelper implements Target {

    private Context context;
    private WeakReference<AlertDialog> dialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;
    private String desc;

    public PuisiDownloadHelper(Context context, AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context = context;
        this.dialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        ContentResolver r =contentResolverWeakReference.get();

        AlertDialog dialog = dialogWeakReference.get();
        dialog.setMessage("Downloading...");

        if (r != null)
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        context.startActivity(Intent.createChooser(intent,"VIEW PICTURE"));

        Toast.makeText(context, "Download berhasil", Toast.LENGTH_LONG).show();
        dialog.dismiss();

    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
