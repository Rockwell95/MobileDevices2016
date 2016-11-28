package ca.uoit.csci4100.a100517944_lab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowGrades extends AppCompatActivity {

    protected static ArrayList<Grade> grades = new ArrayList<>();
    public static final int GRADE_NEW = 0xff12;
    public static final int GRADE_DELETE = 0xff13;
    private ArrayAdapter<Grade> arrayAdapter;
    private Grade newC;
    private Grade delC;

    GradesDBHelper dbHelper = new GradesDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grades = dbHelper.getAllGrades();
        ListView contLv = (ListView) findViewById(R.id.lstGrades);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, grades);
        contLv.setAdapter(arrayAdapter);
    }


    public void deleteGrade(View view) {
        Intent intent = new Intent(this, DeleteGrade.class);
        startActivityForResult(intent, GRADE_DELETE);
    }

    public void addGrade(View view) {
        Intent intent = new Intent(this, AddGrade.class);
        startActivityForResult(intent, GRADE_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GRADE_NEW) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                newC = (Grade) data.getSerializableExtra(AddGrade.NEW_GRADE);
                dbHelper.addNewGrade(newC.getStudentId(), newC.getCourseComponent(), newC.getMark());
            }
        } else if (requestCode == GRADE_DELETE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                delC = (Grade) data.getSerializableExtra(DeleteGrade.DELETED_GRADE);
                dbHelper.deleteGradeById(delC.getStudentId());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Lab6", "Stopping App");
    }

    @Override
    protected void onStart() {
        super.onStart();
        grades.clear();
        grades.addAll(dbHelper.getAllGrades());
        Log.i("Start Lab 7", "Got: " + grades.toString());
        arrayAdapter.notifyDataSetChanged();
    }
}
