package ca.uoit.csci4100.a100517944_lab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddGrade extends AppCompatActivity {
    public static final String NEW_GRADE = "ca.uoit.dmancini.a100157944_lab6.NEW";
    EditText _id, cContent, mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);
    }

    public void addNewGrade(View view) {
        _id = (EditText)findViewById(R.id.txtId);
        cContent = (EditText)findViewById(R.id.txtCourseContent);
        mark = (EditText)findViewById(R.id.txtMark);
        Grade newC = new Grade(
                Integer.parseInt(_id.getText().toString()),
                cContent.getText().toString(),
                Float.parseFloat(mark.getText().toString())
        );
        Intent intent = new Intent();
        intent.putExtra(NEW_GRADE, newC);
        setResult(RESULT_OK, intent);
        finish();
    }
}
