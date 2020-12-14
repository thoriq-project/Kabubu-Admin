package com.example.kabubufix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.kabubufix.Model.Slider;
import com.example.kabubufix.R;

import org.w3c.dom.Text;

import java.util.List;

public class OnboardSliderAdapter extends PagerAdapter {

    Context mContext;
    List<Slider> mListSlider;

    public OnboardSliderAdapter(Context mContext, List<Slider> mListSlider) {
        this.mContext = mContext;
        this.mListSlider = mListSlider;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layoutScreen = inflater.inflate(R.layout.slide_layout,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.img_slide_ob);
        TextView textHeader = layoutScreen.findViewById(R.id.txt_ob_header);
        TextView textKeterangan = layoutScreen.findViewById(R.id.txt_ob_keterangan);

        imgSlide.setImageResource(mListSlider.get(position).getImage());
        textHeader.setText(mListSlider.get(position).getHeader());
        textKeterangan.setText(mListSlider.get(position).getKeterangan());

        container.addView(layoutScreen);

        return layoutScreen;
    }


    @Override
    public int getCount() {
        return mListSlider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
