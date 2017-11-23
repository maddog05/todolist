package com.maddog05.todolist.core

import com.maddog05.todolist.core.database.LogicDatabase
import com.maddog05.todolist.core.database.LogicDatabaseRealm

/**
 * Created by andreetorres on 22/11/17.
 */
class LogicProvider {
    companion object {
        fun provideDatabase(): LogicDatabase {
            return LogicDatabaseRealm()
        }
    }
}