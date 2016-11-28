package ca.uoit.csci4100.a100517944_lab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DeleteGrade extends AppCompatActivity {
    public static final String DELETED_GRADE = "ca.uoit.dmancini.a100157944_lab6.DEL";
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_grade);
        sp = (Spinner)findViewById(R.id.spinnerGrades);
        ArrayAdapter<Grade> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShowGrades.grades);
        sp.setAdapter(arrayAdapter);
    }

    public void deleteThisGrade(View view) {
        Grade delC = (Grade)sp.getSelectedItem();
        Intent result = new Intent();
        result.putExtra(DELETED_GRADE, delC);
        setResult(RESULT_OK, result);
        finish();
    }
}
