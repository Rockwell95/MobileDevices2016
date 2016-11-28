package ca.uoit.csci4100.a100517944_lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GradesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "grades.db";

    private static final String CREATE_STATEMENT = "" +
            "CREATE TABLE Grades(" +
            "studentId int primary key, " +
            "courseComponent varchar(100) not null, " +
            "mark decimal not null)";

    private static final String DROP_STATEMENT = "" +
            "drop table contacts";

    public GradesDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_STATEMENT);
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    public void deleteAllGrades(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Grades", "", new String[] {});
    }

    public void deleteGradeById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Grades", "studentId = ?", new String[]{Integer.toString(id)});
    }

    public Grade addNewGrade(int studentId,
                             String courseComponent,
                             float mark) {
        // insert the contact data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentId", studentId);
        values.put("courseComponent", courseComponent);
        values.put("mark", mark);
        long id = db.insert("Grades", null, values);

        // create a new contact object
        return new Grade(studentId, courseComponent, mark);
    }

    public ArrayList<Grade> getAllGrades() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Grade> results = new ArrayList<>();

        String[] columns = new String[] {"studentId," +
                "courseComponent," +
                "mark"};
        String where = "";  // all contacts
        String[] whereArgs = new String[] {};
        String groupBy = "";  // no grouping
        String groupArgs = "";
        String orderBy = "studentId";

        Cursor cursor = db.query("Grades", columns, where, whereArgs,
                groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String courseComponent = cursor.getString(1);
            float mark = cursor.getFloat(2);

            results.add(new Grade(id, courseComponent, mark));

            cursor.moveToNext();
        }
        Log.i("Lab 7" , "Got: " + results.toString());

        return results;
    }

    public boolean updateGrade(Grade grade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("studentId", grade.getStudentId());
        values.put("courseComponent", grade.getCourseComponent());
        values.put("mark", grade.getMark());

        int numRows = db.update("Grades",
                values,
                "studentId = ?",
                new String[] {Integer.toString(grade.getStudentId())});
        return (numRows == 1);
    }
}
