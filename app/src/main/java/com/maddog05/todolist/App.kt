package com.maddog05.todolist

import android.app.Application
import com.maddog05.todolist.core.LogicProvider

/**
 * Created by andreetorres on 24/11/17.
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        val database = LogicProvider.provideDatabase();
        database.init(this)
    }
}