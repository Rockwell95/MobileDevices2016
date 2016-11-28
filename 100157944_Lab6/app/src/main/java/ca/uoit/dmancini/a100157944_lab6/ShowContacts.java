package ca.uoit.dmancini.a100157944_lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ShowContacts extends AppCompatActivity {

    protected static ArrayList<Contact> contacts = new ArrayList<>();
    public static final int CONTACT_NEW = 0xff12;
    public static final int CONTACT_DELETE = 0xff13;
    private ArrayAdapter<Contact> arrayAdapter;
    private Contact newC;
    private Contact delC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView contLv = (ListView) findViewById(R.id.contacts);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contLv.setAdapter(arrayAdapter);
    }


    public void deleteContact(View view) {
        Intent intent = new Intent(this, DeleteContact.class);
        startActivityForResult(intent, CONTACT_DELETE);
    }

    public void addContact(View view) {
        Intent intent = new Intent(this, AddContact.class);
        startActivityForResult(intent, CONTACT_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CONTACT_NEW) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                arrayAdapter.notifyDataSetChanged();
                newC = (Contact)data.getSerializableExtra(AddContact.NEW_CONTACT);
            }
        }
        else if (requestCode == CONTACT_DELETE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                arrayAdapter.notifyDataSetChanged();
                delC = (Contact)data.getSerializableExtra(DeleteContact.DELETED_CONTACT);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Lab6", "Stopping App");
        try{
            String filePath = this.getFilesDir().getPath() + "/contactData.txt";
            Log.i("Lab 6", "Writing to" + filePath);
            File f = new File(filePath);
            PrintWriter writer = new PrintWriter(f);
            for(Contact c: contacts) {
                writer.println(c.toString());
            }
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        contacts.clear();
        Log.i("Lab6", "Starting...");
        try{
            String filePath = this.getFilesDir().getPath() + "/contactData.txt";
            File file = new File(filePath);
            if (file.exists()) {
                Log.i("Lab6", "Found File! " + filePath);
                BufferedReader vIn = new BufferedReader(new FileReader(file));
                String contactLine;
                int _id;
                String firstName;
                String lastName;
                String phoneNumber;
                while ((contactLine = vIn.readLine()) != null) {
                    if (!contactLine.equals("")) {
                        String[] contactParsed = contactLine.split(", ");
                        _id = Integer.parseInt(contactParsed[0]);
                        lastName = contactParsed[1];
                        firstName = contactParsed[2];
                        phoneNumber = contactParsed[3];
                        contacts.add(new Contact(_id, firstName, lastName, phoneNumber));
                    }
                }
            }
            if(newC != null){
                contacts.add(newC);
                newC = null;
            }
            if(delC != null){
                removeContact();
                delC = null;
            }
            Log.i("Lab 6", "Contacts is now" + contacts.toString());
            arrayAdapter.notifyDataSetChanged();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void removeContact(){
        for(Contact c : contacts){
            if(c.get_id() == delC.get_id()){
                contacts.remove(c);
                break;
            }
        }
    }
}
