package ca.uoit.dmancini.a100157944_lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AddContact extends AppCompatActivity {
    public static final String NEW_CONTACT = "ca.uoit.dmancini.a100157944_lab6.NEW";
    EditText _id, fName, lName, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void addNewContact(View view) {
        _id = (EditText)findViewById(R.id.txtId);
        fName = (EditText)findViewById(R.id.txtFirstName);
        lName = (EditText)findViewById(R.id.txtLastName);
        phone = (EditText)findViewById(R.id.txtPhone);
        Contact newC = new Contact(
                Integer.parseInt(_id.getText().toString()),
                fName.getText().toString(),
                lName.getText().toString(),
                phone.getText().toString()
        );
        Intent intent = new Intent();
        intent.putExtra(NEW_CONTACT, newC);
        setResult(RESULT_OK, intent);
        finish();
    }
}
