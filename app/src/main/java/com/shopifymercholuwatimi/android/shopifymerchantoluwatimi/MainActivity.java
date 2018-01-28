package com.shopifymercholuwatimi.android.shopifymerchantoluwatimi;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
    Documentation:

    This is the MainActivity.java file. This is the root of the application.

    Explanation:
    I created a custom list view which contained the title of the product, description and the image of the product.
    I then populated this listview (listofproducts) with the products gotten from the link at https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6
    using an adapter. My adapter called ProductAdapter has paramters ArrayList<Products> and Context. With these I can populate my Custom Listview with its title, description and Image. The Product
    class contains parameters and getters which I can use to provide information about each product.

    Libraries --->>>

    Glide was used to load Images
    OkHTTP was used to get JSON data.

    Also check out DownloadData class for how I downloaded the data with OKHTTP.

    Check -----> ShowProductActivity for explanation on that Activity.


    Search Algorithm - Searching by Title

    My search Algorithm is pretty basic. I looped through my original ArrayList of products and I checked if my user query (entry)
    was seen in each Product object title. In other words, I checked if each product object title contained the user query. If it did,
    I added it to a new arraylist of searched products. Once this was done, I updated my ProductAdapter with this new ArrayList of searched products.
    Giving me a new listview of searched products.

    I believe this takes a runtime of O(n) thereabout because as I loop through the arrayList n times only, I am checking each product title with my query and it does this exactly n times. So O(n).

    This algorithm can ultimately be improved on as there is no realtime search in the sense that as the user types it gives a search result/suggestions just like google. The user will have to type in everything and click enter
    in the current state of the algorithm. Im looking to improve on this in the future. This is a bit similar to the algorithm in my app Snappy Docs.

     Thank you and I hope you understood :D
     */

    private static final String TAG = "MainActivity";
    JSONObject productsObject;
    JSONArray products;

    ArrayList<Product> productList;
    ArrayList<Product> searchedProducts = new ArrayList<>();
    ProductAdapter adapter;
    ArrayList<String> searched = new ArrayList<>();
    ListView listOfProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productList = new ArrayList<>();
        new DownloadFilesTask().execute("https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6");

        adapter = new ProductAdapter(this,productList);
        listOfProducts = (ListView) findViewById(R.id.ListProducts);
        listOfProducts.setAdapter(adapter);


        listOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchedProducts.size() == 0){
                    Intent intent = new Intent(MainActivity.this,ShowProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Product",productList.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else{
                    Intent intent = new Intent(MainActivity.this,ShowProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Product",searchedProducts.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName =
                new ComponentName(this, MainActivity.class);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName)
        );

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class DownloadFilesTask extends AsyncTask<String, Void, String> {

        DownloadData download;

        protected String doInBackground(String... url) {

            download = new DownloadData(url[0]);

            return download.getMainData();
        }

        protected void onProgressUpdate(Integer... progress) {
        }
        protected void onPostExecute(String result) {
            try{
                productsObject = new JSONObject(download.getMainData());
                products = productsObject.getJSONArray("products");

                for (int i = 0; i<products.length(); i++){
                    JSONObject product = products.getJSONObject(i);
                    JSONObject image = product.getJSONObject("image");

                    Product newP = new Product(product.getString("title"), product.getString("body_html"),image.getString("src"), product.getInt("id"),product.getString("product_type"),product.getString("vendor"));
                    productList.add(newP);

                    adapter.notifyDataSetChanged();

                }
                //Log.d(TAG, "Data: " + product.getString("title"));
                //Log.d(TAG, "Data: " + image.getString("src"));

            } catch (Exception e){
                Log.d(TAG,"Catch in onPostExecute");
            }
            //Log.d(TAG, "Data: " + productList.size());
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) { //Handles Search
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }
    public void doMySearch(String query){
        if (searchedProducts.size() == 0){
            for (int i = 0; i<productList.size(); i++){
                if (productList.get(i).getTitle().toLowerCase().contains(query.toLowerCase())){
                    searchedProducts.add(productList.get(i));
                    searched.add(productList.get(i).getTitle());
                }
            }
            newAdapter();
        }
        else{
            if (searchedProducts.size() != 0){
                ArrayList<String> temp = searched;
                ArrayList<Product> temp2 = searchedProducts;
                int y = searchedProducts.size();
                searchedProducts = new ArrayList<Product>();
                searched = new ArrayList<String>();
                for (int i = 0; i<y; i++){
                    if (temp2.get(i).getTitle().toLowerCase().contains(query.toLowerCase())){
                        searchedProducts.add(temp2.get(i));
                        searched.add(temp2.get(i).getTitle());
                    }
                }

                newAdapter();
            }
        }
    }
    public void newAdapter(){
        adapter = new ProductAdapter(this,searchedProducts);
        listOfProducts.setAdapter(adapter);
    }
}
