package com.Murok.inventoryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Murok.inventoryapp.database.InventoryDao;
import com.Murok.inventoryapp.database.MainDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemListener
{
    private FloatingActionButton fab;
    private RecyclerView inventoryList;
    private ItemAdapter productAdapter;
    private List<Item> itemList;
    private MainDatabase database;
    private InventoryDao inventoryDao;
    private Context context;

    public final static String PRODUCTS_LIST = "products_list";

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        inventoryList = findViewById(R.id.recycler_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        ProductsDatabaseAyncTask task = new ProductsDatabaseAyncTask();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_delete:

                showAlertDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertDialog()
    {
        // Set up the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_message);

        //add OK button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                (new DeleteAllInventoryAsyncTask()).execute();
            }
        });
        // set NO button
        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Item not deleted", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class ProductsDatabaseAyncTask extends AsyncTask<Void, Void, List<Item> >
    {
        @Override
        protected List<Item> doInBackground(Void... voids)
        {
            database = MainDatabase.getStoredDatabase(getApplicationContext());

            inventoryDao = database.productDao();
            itemList = inventoryDao.getProducts();

            return itemList;
        }

        @Override
        protected void onPostExecute(List<Item> products) {
            super.onPostExecute(products);
            updateList(products);
        }
    }

    private class DeleteAllInventoryAsyncTask extends AsyncTask<Void, Void, List<Item> > {
        @Override
        protected List<Item> doInBackground(Void... voids) {
            Log.i(LOG_TAG, "doInBackground: Deleting all of the inventory in the database");
            inventoryDao.deleteAll();
            itemList.clear();
            return itemList;
        }

        @Override
        protected void onPostExecute(List<Item> products) {
            super.onPostExecute(products);
            updateList(products);
            Toast.makeText(context, "All Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int i)
    {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra( PRODUCTS_LIST ,  itemList.get(i) );
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProductsDatabaseAyncTask task = new ProductsDatabaseAyncTask();
        task.execute();
    }

    public void updateList(List<Item> products)
    {
        productAdapter = new ItemAdapter( products , this , database , this );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        inventoryList.setLayoutManager(layoutManager);
        inventoryList.setItemAnimator(new DefaultItemAnimator());
        inventoryList.setAdapter( productAdapter );
    }
}













