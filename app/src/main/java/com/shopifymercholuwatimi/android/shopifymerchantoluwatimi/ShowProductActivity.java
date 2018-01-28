package com.shopifymercholuwatimi.android.shopifymerchantoluwatimi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowProductActivity extends AppCompatActivity {

    /*
    Easy Activity:

    This activity takes in extras from a bundle extras being a Product Object.

    It then sets all the information in my textviews using the Product information.

    I used a Glide to load images.
     */

    Product theProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Bundle gettingProduct = getIntent().getExtras();

        theProduct = (Product) gettingProduct.getParcelable("Product");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Title: " + theProduct.getTitle());
        TextView description = (TextView) findViewById(R.id.description);
        description.setText("Description: " + theProduct.getDescription());
        TextView ID = (TextView) findViewById(R.id.ID);
        ID.setText("ID" + String.valueOf(theProduct.getID()));
        TextView type = (TextView) findViewById(R.id.type);
        type.setText("Type: " + theProduct.getType());
        TextView vendor = (TextView) findViewById(R.id.vendor);
        vendor.setText("Vendor: " + theProduct.getVendor());
        ImageView image = (ImageView) findViewById(R.id.imageView);

        Glide.with(this)
                .load(theProduct.getImgUrl())
                .override(250,250)
                .into(image);


    }
}
