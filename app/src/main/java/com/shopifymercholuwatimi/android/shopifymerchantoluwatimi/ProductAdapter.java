package com.shopifymercholuwatimi.android.shopifymerchantoluwatimi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by owotu on 2017-12-23.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;

    public ProductAdapter(Context context, ArrayList<Product> list){
        super(context,0,list);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.product_base_layout,parent,false);
        }


        Product adding = getItem(position);

        TextView title = (TextView) listItem.findViewById(R.id.ProductTitle);
        title.setText("Title: " + adding.getTitle());

        TextView description = (TextView) listItem.findViewById(R.id.ProductDescription);
        description.setText("Description: " + adding.getDescription());

        ImageView imageDone = (ImageView) listItem.findViewById(R.id.ProductImage);

        Glide.with(getContext())
                .load(adding.getImgUrl())
                .override(250,250)
                .into(imageDone);


        return  listItem;
    }
}
