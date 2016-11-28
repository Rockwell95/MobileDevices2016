package ca.uoit.dmancini.a100157944_lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;

public class DeleteContact extends AppCompatActivity {
    public static final String DELETED_CONTACT = "ca.uoit.dmancini.a100157944_lab6.DEL";
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);
        sp = (Spinner)findViewById(R.id.spinnerContacts);
        ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShowContacts.contacts);
        sp.setAdapter(arrayAdapter);
    }

    public void deleteThisContact(View view) {
        Contact delC = (Contact)sp.getSelectedItem();
        Intent result = new Intent();
        result.putExtra(DELETED_CONTACT, delC);
        setResult(RESULT_OK, result);
        finish();
    }
}
