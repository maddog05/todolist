package com.maddog05.todolist.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maddog05.todolist.R
import com.maddog05.todolist.entity.Task
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by andreetorres on 22/11/17.
 */
class AdapterTasks(private val context: Context) : RecyclerView.Adapter<AdapterTasks.ATVH>() {

    private val tasks: MutableList<Task>
    private var clickCallback: ClickCallbackTask? = null

    init {
        tasks = mutableListOf<Task>()
    }

    fun addClickCallback(callback: ClickCallbackTask) {
        this.clickCallback = callback
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks.clear()
        if (tasks.size > 0) {
            this.tasks.addAll(tasks)
        }
        notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        var isFounded = false
        for (_task in tasks) {
            if (_task.date == task.date) {
                isFounded = true
                break
            }
        }
        if (!isFounded) {
            tasks.add(task)
            notifyItemInserted(tasks.size)
        }
    }

    fun deleteTask(date: Long) {
        var position: Int = -1
        for (index in 0 until tasks.size) {
            if (tasks.get(index).date == date) {
                position = index
                tasks.removeAt(position)
                break
            }
        }
        if (position > -1)
            notifyItemRemoved(position)
    }

    //RECYCLER VIEW ADAPTER FUNCTIONS
    override fun onBindViewHolder(holder: ATVH, position: Int) {
        val task = tasks.get(holder.adapterPosition)
        holder.bind(task, clickCallback!!)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ATVH {
        return ATVH(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))
    }

    //CLICK CALLBACK
    interface ClickCallbackTask {
        fun onClick(task: Task)
    }

    //VIEW HOLDER MODEL
    class ATVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, clickCallback: ClickCallbackTask) {
            with(task) {
                itemView.tv_title.text = task.description
                itemView.setOnClickListener { clickCallback.onClick(task) }
            }
        }
    }
}