package com.Murok.inventoryapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.Murok.inventoryapp.Item;

@Database( entities = {Item.class}, version = 1 , exportSchema = false )
    public abstract class MainDatabase extends RoomDatabase {
        private static final String LOG_TAG = MainDatabase.class.getSimpleName();

        public abstract InventoryDao productDao();
        private static MainDatabase instance;

        /**
         * Fetch an existing database
         * Else create a new database
         * @param context of the application
         * @return return new database
         * */
        public static MainDatabase getStoredDatabase(Context context)
        {
            if (instance==null) {
                Log.i(LOG_TAG, "No database found, a new will be created");
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        MainDatabase.class,
                        "productsDatabase")
                        .build();
            } else {
                Log.i(LOG_TAG, "getStoredDatabase: a database already exists");
            }
            return instance;
        }
    }

