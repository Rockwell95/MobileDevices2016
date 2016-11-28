package ca.uoit.dmancini.a4870assign1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    public static final int QUESTION_REQUEST = 1;
    public static final String QUESTION_EXTRA = "ca.uoit.dmancini.a4870assign1.Main";
    public static final String YES_COUNT = "ca.uoit.dmancini.a4870assign1.YES";
    public static final String NO_COUNT = "ca.uoit.dmancini.a4870assign1.NO";


    private static int questionNo;
    private static int noCount;
    private static int yesCount;
    private static String[] qArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        qArray = getResources().getStringArray(R.array.main_questions);
        noCount = 0;
        yesCount = 0;
        questionNo = 0;
    }

    public void goToQuestions(View view){
        Intent i = new Intent(this, AskQuestion.class);
        i.putExtra(QUESTION_EXTRA, qArray[questionNo]);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == QUESTION_REQUEST){
            if(resultCode == RESULT_OK){
                yesCount += data.getIntExtra(YES_COUNT, 0);
                noCount += data.getIntExtra(NO_COUNT, 0);
                questionNo++;
                String toastText = data.getStringExtra(AskQuestion.TOAST_MESSAGE);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
                if(questionNo < qArray.length){
                    Intent i = new Intent(this, AskQuestion.class);
                    i.putExtra(QUESTION_EXTRA, qArray[questionNo]);
                    startActivityForResult(i, QUESTION_REQUEST);
                }
                else {
                    Intent intent = new Intent(this, Summary.class);
                    intent.putExtra(YES_COUNT, yesCount);
                    intent.putExtra(NO_COUNT, noCount);
                    startActivity(intent);
                    yesCount = 0;
                    noCount = 0;
                    questionNo = 0;
                }
            }
        }
    }
}
