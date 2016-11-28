package ca.uoit.dmancini.a4870assign1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AskQuestion extends AppCompatActivity {

    public static final String TOAST_MESSAGE = "ca.uoit.dmancini.a4870assign1.TOAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        Intent i = getIntent();
        TextView textView = (TextView)findViewById(R.id.txtQuestion);
        textView.setText(i.getStringExtra(MainMenu.QUESTION_EXTRA));
    }

    public void setQuestion(View view){
        int clickId = -1;
        Intent successIntent = new Intent(this, MainMenu.class);
        String toastText = "You chose ";

        if(view != null){
            clickId = view.getId();
        }
        if(clickId == R.id.btnNo){
            toastText +="\"No\"";
            successIntent.putExtra(MainMenu.NO_COUNT, 1);

        }
        else if(clickId == R.id.btnYes) {
            toastText +="\"Yes\"";
            successIntent.putExtra(MainMenu.YES_COUNT, 1);
        }
        successIntent.putExtra(TOAST_MESSAGE, toastText);
        setResult(MainMenu.RESULT_OK, successIntent);
        finish();
    }
}
