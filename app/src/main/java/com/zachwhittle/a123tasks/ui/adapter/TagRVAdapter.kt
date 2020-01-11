package com.zachwhittle.a123tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.ListItemSimpleBinding
import com.zachwhittle.a123tasks.ui.model.Tag
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel

class TagRVAdapter (private val viewModel: MainViewModel) : RecyclerView.Adapter<TagRVAdapter.TagViewHolder>() {

    var tags = emptyList<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val withDataBinding: ListItemSimpleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), TagViewHolder.LAYOUT, parent, false)

        return TagViewHolder(withDataBinding)
    }

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]

        holder.apply {
            val label = binding.label

            label.text = tag.name
        }
    }

    class TagViewHolder(val binding: ListItemSimpleBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item_simple
        }
    }
}