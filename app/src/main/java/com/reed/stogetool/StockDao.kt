package com.codelabs.state.todo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.reed.stogetool.StockItem

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks")
    fun getAll(): LiveData<List<StockItem>>

    @Query("SELECT * FROM stocks WHERE id IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<StockItem>

    @Insert
    fun insertAll(vararg items: StockItem)

    @Update
    fun update(item: StockItem)

    @Delete
    fun delete(item: StockItem)
}