package com.zachwhittle.a123tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.ListItemTaskBinding
import com.zachwhittle.a123tasks.ui.model.Task
import com.zachwhittle.a123tasks.util.withCommas

class TaskRVAdapter() : RecyclerView.Adapter<TaskRVAdapter.TaskViewHolder>() {

    var tasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val withDataBinding: ListItemTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), TaskViewHolder.LAYOUT, parent, false)

        return TaskViewHolder(withDataBinding)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.apply {
//            binding.task = task
            binding.liTaskName.text = task.name
            binding.liTaskTags.text = task.tags.withCommas()
            binding.liTaskProjects.text = task.projects.withCommas()
            binding.liTaskDue.text = task.due
        }
    }


    class TaskViewHolder(val binding: ListItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item_task
        }
    }
}