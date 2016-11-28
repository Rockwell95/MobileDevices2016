package ca.uoit.dmancini.a100517944_assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {
    public static final String NEW_CONTACT_SERIAL = "ca.uoit.dmancini.a100517944_assignment2.SERIAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void cancelActivity(View view) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void saveActivity(View view) {
        EditText prodName = (EditText)findViewById(R.id.new_prod_name);
        EditText prodDesc = (EditText)findViewById(R.id.new_prod_desc);
        EditText prodPrice = (EditText)findViewById(R.id.new_prod_price);

        String name = prodName.getText().toString();
        String desc = prodDesc.getText().toString();
        float price = Float.parseFloat(prodPrice.getText().toString());

        Product p = new Product(-1, name, desc, price);
        Intent i = new Intent();
        i.putExtra(NEW_CONTACT_SERIAL, p);
        setResult(RESULT_OK, i);
        finish();
    }
}
