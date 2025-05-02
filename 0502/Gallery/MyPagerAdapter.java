package com.example.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {
    Context context;
    int[] images;
    String[] titles;
    LayoutInflater inflater;

    public MyPagerAdapter(Context context, int[] images, String[] titles) {
        this.context = context;
        this.images = images;
        this.titles = titles;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView textView = itemView.findViewById(R.id.titleText);

        imageView.setImageResource(images[position]);
        textView.setText(titles[position]);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
