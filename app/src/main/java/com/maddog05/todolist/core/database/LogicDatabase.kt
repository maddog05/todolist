package com.maddog05.todolist.core.database

import android.content.Context
import com.maddog05.todolist.entity.Task

/**
 * Created by andreetorres on 22/11/17.
 */
interface LogicDatabase {
    fun init(context: Context)

    fun getTasks(): List<Task>

    fun addTask(task: Task)

    fun deleteTask(date: Long)
}
