package com.Murok.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Murok.inventoryapp.database.InventoryDao;
import com.Murok.inventoryapp.database.MainDatabase;

import org.jetbrains.annotations.Nullable;

public class EditActivity extends AppCompatActivity
{
    public static final String LOG_TAG = EditActivity.class.getSimpleName();

    private ImageView productImageView;
    private EditText editName;
    private EditText editQuantity;
    private EditText editPrice;
    private EditText editRating;


    public MainDatabase database;
    public InventoryDao inventoryDao;

    public long id;
    public String name;
    public int quantity;
    public String strQuantity;
    public double price;
    public String strPrice;
    public String strRating;

    Bitmap bitmap;

    Intent intent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);

         editName = findViewById(R.id.editTextName);
         editQuantity = findViewById(R.id.editTextQuantity);
         editPrice = findViewById(R.id.editTextPrice);
         productImageView = findViewById(R.id.image);
         editRating = findViewById(R.id.editTextSupplier);

         database = MainDatabase.getStoredDatabase(this);
         inventoryDao = database.productDao();


         try {
             intent = getIntent();

             getProductData( (Item) intent.getSerializableExtra(MainActivity.PRODUCTS_LIST) );

             setEditTexts();
         } catch(NullPointerException e)
         {
            Log.i(LOG_TAG, "onCreate: Intent is null.");
            intent = null;
         }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
                case R.id.action_save:
                     saveProduct();
                     return true;
                case R.id.action_delete:
                     showAlertDialog();
                     return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_message);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProduct();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getProductData( Item product )
    {
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        quantity = product.getQuantity();
        strRating = product.getRating();
    }

    public void setEditTexts()
    {
        editName.setText(name);
        editQuantity.setText(quantity + "");
        editPrice.setText(price + "");
        editRating.setText(strRating);
    }

    private void saveProduct()
    {
        name = editName.getText().toString();
        strQuantity = editQuantity.getText().toString();
        strPrice = editPrice.getText().toString();
        strRating = editRating.getText().toString();

        if (!name.isEmpty() && !strQuantity.isEmpty() && !strPrice.isEmpty() )
        {
            quantity = Integer.parseInt(strQuantity);
            price = Double.parseDouble(strPrice);

            ( new AddInventoryAsyncTask(database.productDao()) ).execute();
        } else {
            Toast.makeText(this, "Please fill in all fields...", Toast.LENGTH_LONG).show();
        }
    }
    private void deleteProduct()
    {
        ( new DeleteInventoryAsyncTask( database.productDao() ) ).execute();
    }
    private class AddInventoryAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private InventoryDao dao;
        AddInventoryAsyncTask(InventoryDao dao)
        {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            if (intent == null)
            {
                Log.i(LOG_TAG, "doInBackground: Adding a new row to the database");
                Item product = new Item(name, quantity,price, strRating );
                dao.add(product);
            } else {
                dao.update(name, quantity, price, strRating, id );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(EditActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private class DeleteInventoryAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private InventoryDao dao;
        DeleteInventoryAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.i(LOG_TAG, "doInBackground: Deleting a row from the database");
            dao.delete(id);
            return null;
        }

        @Override
        protected void onPostExecute( Void aVoid )
        {
            super.onPostExecute(aVoid);
            Toast.makeText(EditActivity.this , "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}
