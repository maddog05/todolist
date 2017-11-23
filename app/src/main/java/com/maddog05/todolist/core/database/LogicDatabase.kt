package com.maddog05.todolist.core.database

import com.maddog05.todolist.entity.Task

/**
 * Created by andreetorres on 22/11/17.
 */
interface LogicDatabase {
    fun getTasks(): List<Task>

    fun addTask(task: Task)

    fun deleteTask(date: Long)
}
