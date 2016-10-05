package ca.uoit.dmancini.lab3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // loginSuccess("Welcome");
    }

    public void showAbout(View view){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
    }

    private void loginSuccess(CharSequence toastText){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    public void showLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("LAB3", "ACTIVITY RESULT RECEIVED: " + resultCode + " --- RESULT_OK: " + MainActivity.RESULT_OK);
        Log.i("Request Code", "" + requestCode);
        switch (requestCode){
            case (LOGIN_REQUEST):
                if(resultCode == MainActivity.RESULT_OK){
                    Log.i("Lab3", "Going to display Toast");
                    String tText = intent.getStringExtra(LoginActivity.ALT_MESSAGE);
                    loginSuccess(tText);
                }
                break;
        }
    }
}
