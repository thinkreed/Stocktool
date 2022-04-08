package com.reed.stogetool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "stocks")
data class StockItem(
    @ColumnInfo
    val origin : String = "0",
    @ColumnInfo
    val first: String = "0",
    @ColumnInfo
    val second: String = "0",
    @ColumnInfo
    val third: String = "0",
    @ColumnInfo
    val fourth: String = "0",
    @ColumnInfo
    val fifth: String = "0",
    @ColumnInfo
    val name : String = "",
    // since the user may generate identical tasks, give them each a unique ID
    @PrimaryKey
    val id: UUID = UUID.randomUUID()
)
