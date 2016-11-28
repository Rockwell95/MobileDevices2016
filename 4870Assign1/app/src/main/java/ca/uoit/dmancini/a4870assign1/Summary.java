package ca.uoit.dmancini.a4870assign1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        String yesMsg;
        String noMsg;
        Intent intent = getIntent();
        int yesCount = intent.getIntExtra(MainMenu.YES_COUNT, -1);
        int noCount = intent.getIntExtra(MainMenu.NO_COUNT, -1);
        if(yesCount >= 0 && noCount >= 0){
            yesMsg = "You answered \"Yes\" to " + yesCount + (yesCount == 1 ? " question": " questions");
            noMsg = "You answered \"No\" to " + noCount + (noCount == 1 ? " question": " questions");
        }
        else{
            yesMsg = "ERROR: NO COUNT FOUND";
            noMsg = "ERROR: NO COUNTER FOUND";
        }

        TextView y = (TextView)findViewById(R.id.txtYesTotal);
        TextView n = (TextView)findViewById(R.id.txtNoTotal);
        y.setText(yesMsg);
        n.setText(noMsg);
    }


    public void finishActivity(View view) {
        finish();
    }
}
