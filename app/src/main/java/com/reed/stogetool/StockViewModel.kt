package com.reed.stogetool

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.state.todo.DbOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class StockViewModel : ViewModel() {
    var allItems : LiveData<List<StockItem>> by Delegates.notNull()

    fun addItem(item: StockItem) {
        viewModelScope.launch(Dispatchers.IO) {
            DbOperator.db.stockDao().insertAll(item)
        }
    }

    fun removeItem(item: StockItem) {
        viewModelScope.launch(Dispatchers.IO) {
            DbOperator.db.stockDao().delete(item)
        }
    }

    suspend fun init() {
        allItems = DbOperator.db.stockDao().getAll()
    }
}