package com.maddog05.todolist.core.database.table

import com.maddog05.todolist.entity.Task
import io.realm.RealmObject

/**
 * Created by andreetorres on 24/11/17.
 */
open class DBTask : RealmObject() {

    //CONSTANTS
    companion object {
        @JvmField
        val FIELD_DATE = "date"
    }

    //DATA
    var date: Long = 0
    var description: String = ""

    //FUNCTION TO CREATE TASK
    fun toTask(): Task {
        val task = Task(date)
        task.description = description
        return task
    }
}