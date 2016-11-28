package ca.uoit.dmancini.lab5;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShowLicense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_license);
        Intent caller = getIntent();
        String licenseMessage = caller.getStringExtra(MainActivity.LICENSE);
        TextView license = (TextView)findViewById(R.id.license);
        license.setMovementMethod(new ScrollingMovementMethod());
        license.setText(licenseMessage);

    }

    public void closeAgreement(View view) {
        finish();
    }
}
