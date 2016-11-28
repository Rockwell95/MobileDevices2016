/*
 * Copyright (c) 2016. Dominick Mancini, University of Ontario Institute of Technology. All Rights Reserved
 */

package ca.uoit.dmancini.a100517944_assignment2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class BrowseProductsActivity extends AppCompatActivity implements ConversionListener{
    public static ArrayList<Product> products = new ArrayList<>();
    public static final int NEW_CONTACT = 0xff12;
    private ProductDBHelper dbHelper = new ProductDBHelper(this);
    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_products_activity);
        products.addAll(dbHelper.getAllProducts());
        index = 0;
        if (!products.isEmpty()) {
            showProduct(products.get(index));
        }
    }

    private void showProduct(Product p) {
        EditText id = (EditText)findViewById(R.id.prod_id);
        EditText name = (EditText)findViewById(R.id.prod_name);
        EditText desc = (EditText)findViewById(R.id.prod_desc);
        EditText price = (EditText)findViewById(R.id.new_prod_price);

        String dollarPrice = "$" + String.valueOf(p.getPrice());
        convertToBitcoin(p.getPrice());

        id.setText(String.valueOf(p.getProductId()));
        name.setText(p.getName());
        desc.setText(p.getDescription());
        price.setText(dollarPrice);


        if(index == 0){
            (findViewById(R.id.prev)).setEnabled(false);
        }
        else{
            (findViewById(R.id.prev)).setEnabled(true);
        }
        if(index == products.size() - 1){
            (findViewById(R.id.next)).setEnabled(false);
        }
        else{
            (findViewById(R.id.next)).setEnabled(true);
        }
    }

    private void convertToBitcoin(float cad) {
        DownloadTask task = new DownloadTask(this);
        String url = "https://blockchain.info/tobtc?currency=CAD&value=" + cad;
        task.execute(url);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_new_product:
                startActivityForResult(new Intent(this, AddProductActivity.class), NEW_CONTACT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void goToPrevious(View view) {
        if (index > 0) {
            index--;
            showProduct(products.get(index));
        }
    }

    public void goToNext(View view) {
        if (index < products.size() -1 ) {
            index++;
            showProduct(products.get(index));
        }
    }

    public void deleteCurrent(View view) {
        int id = Integer.parseInt(((EditText)findViewById(R.id.prod_id)).getText().toString());
        dbHelper.deleteProductById(id);
        products = dbHelper.getAllProducts();
        if(index == products.size()){
            index--;
        }
        if(products.size() == 0){
            showProduct(new Product(0x0, "N/A", "N/A", 0));
        }
        else{
            showProduct(products.get(index));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == NEW_CONTACT){
                Product p = (Product)data.getSerializableExtra(AddProductActivity.NEW_CONTACT_SERIAL);
                dbHelper.addNewProduct(p.getName(), p.getDescription(), p.getPrice());
                products = dbHelper.getAllProducts();
                index = 0;
                showProduct(products.get(index));
            }
        }
    }

    @Override
    public void handleConversion(String btc) {
        Log.i("Assign2", "got the conversion:" + btc);
        // update the UI
        String bitCoinPrice = "Ƀ" + btc;
        EditText bcPrice = (EditText)findViewById(R.id.prod_price_bc);
        bcPrice.setText(bitCoinPrice);
    }

    class DownloadTask extends AsyncTask<String, Void, String> {
        private String bitCoin = "";
        private Exception exception = null;
        private ConversionListener listener = null;

        DownloadTask(ConversionListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // download conversion from the service
                URL url = new URL(params[0]);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;

                while ((line = in.readLine()) != null) {
                    //get lines
                    bitCoin = line;
                }
                in.close();

                // remember the data
                return bitCoin;
            } catch (Exception e) {
                exception = e;
                bitCoin = "";
                return bitCoin;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // handle any error
            if (exception != null) {
                exception.printStackTrace();
                return;
            }

            // show the data
            listener.handleConversion(result);
        }
    }

}
