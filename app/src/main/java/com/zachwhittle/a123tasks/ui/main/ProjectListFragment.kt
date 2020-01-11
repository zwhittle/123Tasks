package com.zachwhittle.a123tasks.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.FragmentListBinding
import com.zachwhittle.a123tasks.ui.adapter.ProjectRVAdapter
import com.zachwhittle.a123tasks.ui.adapter.TaskRVAdapter
import com.zachwhittle.a123tasks.ui.model.Project
import com.zachwhittle.a123tasks.ui.model.Task
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel

class ProjectListFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectListFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentListBinding>(inflater, R.layout.fragment_list, container, false)

        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        adapter = ProjectRVAdapter(viewModel)
        recyclerView.adapter = adapter

        viewModel.allProjects.observe(viewLifecycleOwner, projectsObserver)
    }

    private val projectsObserver = Observer<List<Project>> { projects ->
        adapter.projects = projects
        adapter.notifyDataSetChanged()
    }
}