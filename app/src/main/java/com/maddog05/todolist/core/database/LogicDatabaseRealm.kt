package com.maddog05.todolist.core.database

import com.maddog05.todolist.entity.Task

/**
 * Created by andreetorres on 22/11/17.
 */
class LogicDatabaseRealm : LogicDatabase {
    override fun getTasks(): List<Task> {
        return ArrayList<Task>()
    }

    override fun addTask(task: Task) {

    }

    override fun deleteTask(date: Long) {

    }
}