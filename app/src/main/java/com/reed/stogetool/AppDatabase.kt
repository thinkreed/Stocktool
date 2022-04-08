package com.reed.stogetool

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codelabs.state.todo.StockDao

@Database(entities = [StockItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}