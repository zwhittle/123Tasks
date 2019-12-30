package com.zachwhittle.a123tasks.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.databinding.ListItemTaskBinding
import com.zachwhittle.a123tasks.ui.model.Task
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel
import com.zachwhittle.a123tasks.util.withCommas
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import com.zachwhittle.a123tasks.R


class TaskRVAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<TaskRVAdapter.TaskViewHolder>() {

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

            val nameTv = binding.liTaskName
            val tagsTv = binding.liTaskTags
            val projectsTv = binding.liTaskProjects
            val dueTv = binding.liTaskDue

            nameTv.text = task.name
            tagsTv.text = task.tags.withCommas()
            projectsTv.text = task.projects.withCommas()
            dueTv.text = task.due

            if (task.isComplete) {
                nameTv.paintFlags = nameTv.paintFlags or STRIKE_THRU_TEXT_FLAG
                tagsTv.paintFlags = tagsTv.paintFlags or STRIKE_THRU_TEXT_FLAG
                projectsTv.paintFlags = projectsTv.paintFlags or STRIKE_THRU_TEXT_FLAG
                dueTv.paintFlags = dueTv.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                nameTv.paintFlags = 0
                tagsTv.paintFlags = 0
                projectsTv.paintFlags = 0
                dueTv.paintFlags = 0
            }

            binding.listener = View.OnClickListener {
                viewModel.toggleComplete(task)
            }
        }
    }


    class TaskViewHolder(val binding: ListItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item_task
        }
    }
}