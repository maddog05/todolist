package com.maddog05.todolist.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.maddog05.todolist.R
import com.maddog05.todolist.core.LogicProvider
import com.maddog05.todolist.core.database.LogicDatabase
import com.maddog05.todolist.entity.Task
import com.maddog05.todolist.ui.adapter.AdapterTasks

class MainActivity : AppCompatActivity() {

    //CONSTANTS
    private val TAG: String = "#Main"

    //LOGIC
    private var database: LogicDatabase? = null

    //VIEWS
    private var taskEt: AppCompatEditText? = null
    private var tasksRv: RecyclerView? = null
    private var addFab: FloatingActionButton? = null

    //CUSTOM
    private var adapter: AdapterTasks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLogics()
        setupViews()
        setupActions()
        setupData()
    }

    private fun setupLogics() {
        database = LogicProvider.provideDatabase()
    }

    private fun setupViews() {
        taskEt = findViewById(R.id.et_task)
        tasksRv = findViewById(R.id.rv_tasks)
        addFab = findViewById(R.id.fab_add)

        adapter = AdapterTasks(this@MainActivity)
        adapter!!.addClickCallback(object : AdapterTasks.ClickCallbackTask {
            override fun onClick(task: Task) = processClickCallback(task)
        })
        tasksRv!!.adapter = adapter
    }

    private fun setupActions() {
        taskEt!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "${count} in onTextChanged")
            }

        })
        tasksRv?.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        tasksRv?.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        addFab?.setOnClickListener { addTask() }
    }

    private fun setupData() {
        adapter!!.setTasks(database!!.getTasks())
    }

    private fun processClickCallback(task: Task) {
        //FOR NOW
        AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.dialog_delete_task)
                .setNegativeButton(R.string.action_cancel) { dialogInterface, p1 -> dialogInterface.dismiss() }
                .setPositiveButton(R.string.action_delete) { dialogInterface, p1 ->
                    dialogInterface.dismiss()
                    removeTask(task.date)
                }
                .show()
    }

    private fun addTask() {
        val descriptionTask = taskEt!!.text.toString()
        if (descriptionTask.trim().isEmpty()) {
            showError(getString(R.string.error_empty_fields))
        } else {
            _addTask(descriptionTask)
        }
    }

    private fun _addTask(descriptionTask: String) {
        val date = System.currentTimeMillis()
        val task = Task(date)
        task.description = descriptionTask
        database?.addTask(task)
        adapter!!.addTask(task)
        taskEt!!.text.clear()

    }

    private fun removeTask(date: Long) {
        database?.deleteTask(date)
        adapter!!.deleteTask(date)
    }

    private fun showError(text: String) {
        addFab?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }
    }
}
