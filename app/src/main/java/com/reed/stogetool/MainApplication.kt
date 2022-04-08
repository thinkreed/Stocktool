package com.reed.stogetool

import android.app.Application
import com.codelabs.state.todo.DbOperator

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DbOperator.init(this)
    }
}