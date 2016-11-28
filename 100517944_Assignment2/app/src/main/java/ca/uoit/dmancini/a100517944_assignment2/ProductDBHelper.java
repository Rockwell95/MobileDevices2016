package ca.uoit.dmancini.a100517944_assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

class ProductDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "products.db";

    private static final String CREATE_STATEMENT = "" +
            "CREATE TABLE Products(" +
            "productId INTEGER primary key AUTOINCREMENT, " +
            "name varchar(100) not null, " +
            "description varchar(100)," +
            "price decimal" +
            ")";

    private static final String DROP_STATEMENT = "" +
            "drop table Products";

    ProductDBHelper(Context context) {
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

    public void deleteAllProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Products", "", new String[] {});
    }

    void deleteProductById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Products", "productId = ?", new String[]{Integer.toString(id)});
    }

    Product addNewProduct(String name, String description, float price) {
        // insert the contact data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        long id = db.insert("Products", null, values);

        // create a new contact object
        return new Product((int) id, name, description, price);
    }

    ArrayList<Product> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> results = new ArrayList<>();

        String[] columns = new String[] {"productId," +
                "name," +
                "description," +
                "price"};
        String where = "";  // all products
        String[] whereArgs = new String[] {};
        String groupBy = "";  // no grouping
        String groupArgs = "";
        String orderBy = "productId";

        Cursor cursor = db.query("Products", columns, where, whereArgs,
                groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            float price = cursor.getFloat(3);

            results.add(new Product(id, name, description, price));

            cursor.moveToNext();
        }
        Log.i("Lab 7" , "Got: " + results.toString());
        cursor.close();

        return results;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("productId", product.getProductId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());

        int numRows = db.update("Products",
                values,
                "productId = ?",
                new String[] {Integer.toString(product.getProductId())});
        return (numRows == 1);
    }
}
