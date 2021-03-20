package com.Murok.inventoryapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Item")
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo( name = "item_name")
    private String mName;
    @ColumnInfo( name = "quantity" )
    private int mQuantity;
    @ColumnInfo( name = "price" )
    private double mPrice;

    @ColumnInfo( name = "rating" )
    private String mRating;

    public Item(String name, int quantity , double price, String rating)
    {
        mName = name;
        mQuantity = quantity;
        mPrice = price;

        mRating = rating;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }



    public long getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public double getPrice() {
        return mPrice;
    }



    public void setRating(String mRating) {
        this.mRating = mRating;
    }

    public String getRating() {
        return mRating;
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %d, %f", getName(), getRating(), getQuantity(), getPrice());
    }
}
