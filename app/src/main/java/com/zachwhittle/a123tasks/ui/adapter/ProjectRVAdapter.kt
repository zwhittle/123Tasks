package com.zachwhittle.a123tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.ListItemSimpleBinding
import com.zachwhittle.a123tasks.ui.model.Project
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel

class ProjectRVAdapter (private val viewModel: MainViewModel) : RecyclerView.Adapter<ProjectRVAdapter.ProjectViewHolder>() {

    var projects = emptyList<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val withDataBinding: ListItemSimpleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), ProjectViewHolder.LAYOUT, parent, false)

        return ProjectViewHolder(withDataBinding)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]

        holder.apply {
            val label = binding.label

            label.text = project.name
        }
    }

    class ProjectViewHolder(val binding: ListItemSimpleBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item_simple
        }
    }
}