package com.coloring.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {
     Context mContext;
     View myView;

    public ImageAdapter(Context context) {
        System.err.println("image adapte");
        this.mContext = context;
    }

    public int getCount() {
        return com.coloring.page.MyConstant.selected_bitmapIds.length;
    }

    public Object getItem(int i) {
        return com.coloring.page.MyConstant.selected_bitmapIds[i];
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = com.coloring.page.MyConstant.widthInPixels;
        int i3 = (i2 / 2) - (i2 / 40);
        int i4 = (i3 * 3) / 2;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.grid_layout_view, viewGroup, false);
        }
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.imageView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewInside);
        frameLayout.setLayoutParams(new AbsListView.LayoutParams(i3, i4));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Picasso.get().load(com.coloring.page.MyConstant.selected_bitmapIds[i].intValue()).into(imageView);
        return frameLayout;
    }
}
