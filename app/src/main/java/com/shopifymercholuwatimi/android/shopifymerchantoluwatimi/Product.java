package com.shopifymercholuwatimi.android.shopifymerchantoluwatimi;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by owotu on 2017-12-21.
 */

public class Product implements Parcelable {
    String title;
    String description;
    String imgUrl;
    int ID;
    String type;
    String vendor;
    public Product(String title,String description, String imgUrl, int ID, String type, String vendor){
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.ID = ID;
        this.type = type;
        this.vendor = vendor;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public int getID(){
        return ID;
    }

    public String getType(){
        return type;
    }

    public String getVendor(){
        return vendor;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        // TODO Auto-generated method stub
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imgUrl);
        dest.writeInt(ID);
        dest.writeString(type);
        dest.writeString(vendor);
    }

    public Product(Parcel in) {
        title = in.readString();
        description = in.readString();
        imgUrl = in.readString();
        ID = in.readInt();
        type = in.readString();
        vendor = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
