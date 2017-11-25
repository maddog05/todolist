package com.maddog05.todolist.core.database

import android.content.Context
import com.maddog05.todolist.R
import com.maddog05.todolist.core.database.table.DBTask
import com.maddog05.todolist.entity.Task
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import io.realm.annotations.RealmModule

/**
 * Created by andreetorres on 22/11/17.
 */
class LogicDatabaseRealm : LogicDatabase {
    override fun init(context: Context) {
        Realm.init(context)
        Realm.setDefaultConfiguration(getRealmConfiguration(context))
    }

    override fun getTasks(): List<Task> {
        val tasks = ArrayList<Task>()
        val realm = with()
        val results = realm
                .where(DBTask::class.java)
                .findAll()
        for (_task in results) {
            val task: Task = _task.toTask()
            tasks.add(task)
        }
        realm.close()
        return tasks
    }

    override fun addTask(task: Task) {
        val realm = with()
        realm.executeTransaction {
            val _task = realm.createObject(DBTask::class.java)
            _task.date = task.date
            _task.description = task.description
        }
        realm.close()
    }

    override fun deleteTask(date: Long) {
        val realm = with()
        val toDelete: DBTask? = realm
                .where(DBTask::class.java)
                .equalTo(DBTask.FIELD_DATE, date)
                .findFirst()
        if (toDelete != null) {
            realm.executeTransaction {
                toDelete.deleteFromRealm()
            }
        }
        realm.close()
    }

    //GENERAL METHODS
    private fun with(): Realm {
        return Realm.getDefaultInstance()
    }

    //REALM PRIVATE CONFIGURATIONS
    private fun getRealmConfiguration(context: Context): RealmConfiguration {
        return RealmConfiguration.Builder()
                .modules(ModuleDatabase())
                .schemaVersion(context.resources.getInteger(R.integer.realm_schema_version).toLong())
                .migration(getMigration())
                .build()
    }

    private fun getMigration(): RealmMigration {
        return RealmMigration { realm, oldVersion, newVersion ->
            {
                //FOR FUTURE RELEASES
            }
        }
    }

    @RealmModule(classes = arrayOf(DBTask::class))
    class ModuleDatabase
}