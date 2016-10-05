package ca.uoit.dmancini.uidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        Log.i("UIDemo", "The button was clicked");
        setContentView(R.layout.activity_main);
    }

    public void linearLayout(View view) {
        setContentView(R.layout.linear_layout);
        Spinner list = (Spinner)findViewById(R.id.listSpinner);
        populateSpinner(list, R.array.provinces);
    }

    public void relativeLayout(View view) {
        setContentView(R.layout.relative_layout);
        Spinner list = (Spinner)findViewById(R.id.listSpinnerR);
        populateSpinner(list, R.array.provinces);
    }

    public void populateSpinner(Spinner list, int arrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);
    }
}
