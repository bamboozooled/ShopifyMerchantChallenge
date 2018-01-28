package com.shopifymercholuwatimi.android.shopifymerchantoluwatimi;

/**
 * Created by owotu on 2017-12-21.
 */


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadData {

    String mainData;

    OkHttpClient mainClient = new OkHttpClient();

    public DownloadData(String url){

        mainData = getDatas(url);

    }

    public String getDatas(String url){
        Request mainRequest = new Request.Builder()
                .url(url)
                .build();

        try{

            Response response = mainClient.newCall(mainRequest).execute();

            return response.body().string();
        }

        catch (IOException e){

        }

        return null;
    }

    public String getMainData(){
        return mainData;
    }
}
