package com.Murok.inventoryapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.Murok.inventoryapp.Item;

import java.util.List;

@Dao
public interface InventoryDao
{
  //   @Insert(onConflict = OnConflictStrategy.REPLACE)
  //   void insert(Item product);

     @Query("SELECT * FROM Item")
     List<Item> getProducts();

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     long add(Item product);

     @Query("UPDATE Item SET item_name = :name, quantity = :quantity, price = :price, rating = :rating,  id = :id")
     void update(String name, int quantity, double price, String rating, long id);

     @Query("DELETE FROM Item")
     void deleteAll();

     @Query("DELETE FROM Item WHERE id = :id")
     void delete(long id);

    @Query("DELETE FROM Item WHERE item_name = :name AND quantity =:quantity AND price=:price")
    void delete(String name, int quantity, double price);

     @Query("DELETE FROM Item WHERE item_name = :name")
     void delete(String name);

     @Delete
     int delete(Item product);


}
