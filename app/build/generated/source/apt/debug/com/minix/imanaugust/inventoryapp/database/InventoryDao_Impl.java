package com.minix.imanaugust.inventoryapp.database;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.minix.imanaugust.inventoryapp.Product;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class InventoryDao_Impl implements InventoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfProduct;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDelete_1;

  private final SharedSQLiteStatement __preparedStmtOfDelete_2;

  public InventoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Item`(`id`,`product_name`,`quantity`,`price`,`image_url`,`supplier`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Product value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getQuantity());
        stmt.bindDouble(4, value.getPrice());
        if (value.getImageUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageUrl());
        }
        if (value.getSupplier() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSupplier());
        }
      }
    };
    this.__deletionAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Item` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Product value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Item SET product_name = ?, quantity = ?, price = ?, supplier = ?, image_url = ?,  id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Item";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Item WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_1 = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Item WHERE product_name = ? AND quantity =? AND price=?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_2 = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Item WHERE product_name = ?";
        return _query;
      }
    };
  }

  @Override
  public long add(Product product) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfProduct.insertAndReturnId(product);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(Product product) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(String name, int quantity, double price, String supplier, String imageUri,
      long id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, name);
      }
      _argIndex = 2;
      _stmt.bindLong(_argIndex, quantity);
      _argIndex = 3;
      _stmt.bindDouble(_argIndex, price);
      _argIndex = 4;
      if (supplier == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, supplier);
      }
      _argIndex = 5;
      if (imageUri == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, imageUri);
      }
      _argIndex = 6;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void delete(long id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public void delete(String name, int quantity, double price) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_1.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, name);
      }
      _argIndex = 2;
      _stmt.bindLong(_argIndex, quantity);
      _argIndex = 3;
      _stmt.bindDouble(_argIndex, price);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_1.release(_stmt);
    }
  }

  @Override
  public void delete(String name) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_2.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, name);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_2.release(_stmt);
    }
  }

  @Override
  public List<Product> getProducts() {
    final String _sql = "SELECT * FROM Item";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("product_name");
      final int _cursorIndexOfMQuantity = _cursor.getColumnIndexOrThrow("quantity");
      final int _cursorIndexOfMPrice = _cursor.getColumnIndexOrThrow("price");
      final int _cursorIndexOfMImageUrl = _cursor.getColumnIndexOrThrow("image_url");
      final int _cursorIndexOfMSupplier = _cursor.getColumnIndexOrThrow("supplier");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Product _item;
        final String _tmpMName;
        _tmpMName = _cursor.getString(_cursorIndexOfMName);
        final int _tmpMQuantity;
        _tmpMQuantity = _cursor.getInt(_cursorIndexOfMQuantity);
        final double _tmpMPrice;
        _tmpMPrice = _cursor.getDouble(_cursorIndexOfMPrice);
        final String _tmpMImageUrl;
        _tmpMImageUrl = _cursor.getString(_cursorIndexOfMImageUrl);
        final String _tmpMSupplier;
        _tmpMSupplier = _cursor.getString(_cursorIndexOfMSupplier);
        _item = new Product(_tmpMName,_tmpMQuantity,_tmpMPrice,_tmpMSupplier,_tmpMImageUrl);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
