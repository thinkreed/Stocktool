package com.codelabs.state.todo

import android.content.Context
import androidx.room.Room
import com.reed.stogetool.AppDatabase
import kotlin.properties.Delegates

internal object DbOperator {

    var db: AppDatabase by Delegates.notNull()
        private set

    fun init(applicationContext: Context) {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "stocks"
        ).build()
    }
}