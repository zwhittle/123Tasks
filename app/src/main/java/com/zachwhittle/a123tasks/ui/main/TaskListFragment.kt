package com.zachwhittle.a123tasks.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.FragmentTaskListBinding
import com.zachwhittle.a123tasks.ui.adapter.TaskRVAdapter
import com.zachwhittle.a123tasks.ui.model.Task
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel
import com.zachwhittle.a123tasks.util.*

class TaskListFragment : Fragment() {

    companion object {
        fun newInstance() = TaskListFragment()
    }

    private lateinit var inbox: EditText
    private lateinit var outbox: TextView

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskRVAdapter

    private lateinit var task: Task

    private var showingTasks: Int = Constants.SHOWING_ALL_TASKS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentTaskListBinding>(inflater, R.layout.fragment_task_list, container, false)

        inbox = binding.inbox
//        outbox = binding.outbox

        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        binding.dealWithItButton.setOnClickListener {
            dealWithString()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        adapter = TaskRVAdapter(viewModel)
        recyclerView.adapter = adapter

        when (showingTasks) {
            Constants.SHOWING_ALL_TASKS -> viewModel.allTasks.observe(viewLifecycleOwner, tasksObserver)
            Constants.SHOWING_ACTIVE_TASKS -> viewModel.activeTasks.observe(viewLifecycleOwner, tasksObserver)
            Constants.SHOWING_COMPLETED_TASKS -> viewModel.completedTasks.observe(viewLifecycleOwner, tasksObserver)
        }
    }

    private val tasksObserver = Observer<List<Task>> { tasks ->
        adapter.tasks = tasks
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_db -> {actionClearDb()}
            R.id.action_toggle_complete -> {actionToggleComplete()}
            else -> false
        }
    }

    private fun actionClearDb(): Boolean {
        viewModel.clearTasks()
        return true
    }

    private fun actionToggleComplete(): Boolean {
        when (showingTasks) {
            Constants.SHOWING_ALL_TASKS -> subscribeToActiveTasks()
            Constants.SHOWING_ACTIVE_TASKS -> subscribeToAllTasks()
        }
        return true
    }

    private fun subscribeToActiveTasks() {
        clearObservers()
        viewModel.activeTasks.observe(viewLifecycleOwner, tasksObserver)
        showingTasks = Constants.SHOWING_ACTIVE_TASKS
    }

    private fun subscribeToAllTasks() {
        clearObservers()
        viewModel.allTasks.observe(viewLifecycleOwner, tasksObserver)
        showingTasks = Constants.SHOWING_ALL_TASKS
    }

    private fun subscribeToCompletedTasks() {
        clearObservers()
        viewModel.completedTasks.observe(viewLifecycleOwner, tasksObserver)
        showingTasks = Constants.SHOWING_COMPLETED_TASKS
    }

    private fun clearObservers() {
        if (viewModel.allTasks.hasActiveObservers()) {
            viewModel.allTasks.removeObserver(tasksObserver)
        }

        if (viewModel.completedTasks.hasActiveObservers()) {
            viewModel.completedTasks.removeObserver(tasksObserver)
        }

        if (viewModel.activeTasks.hasActiveObservers()) {
            viewModel.activeTasks.removeObserver(tasksObserver)
        }
    }

    private fun setOutboxText(value: String) {
//        outbox.text = value
    }

    private fun dealWithString() {
        val text = inbox.text.toString()

        setOutboxText(extractTask(text).toString())
//        extractTask(text)
//        loopString(text)
    }

    private fun extractTask(text: String) : String {
        val delimiter = " "
        val words = text.split(delimiter)

        val tagsBuilder = StringBuilder()
        val projectsBuilder = StringBuilder()
        val nameBuilder = StringBuilder()
        val notesBuilder = StringBuilder()

        val tags = ArrayList<String>()
        val projects = ArrayList<String>()
        var due = ""

        val containsBang = text.contains("!")

        for (w in words) {
            val isKey = (w.startsWithAt() || w.startsWithHash() || w.startsWithTilde())

            if (!isKey) {
                nameBuilder.append(w).append(" ")
            }

            if (w.startsWithAt()) {
                val x = w.substring(1)
                tagsBuilder.append(x).append("\n")
                tags.add(x)
            }

            if (w.startsWithHash()) {
                val x = w.substring(1)
                projectsBuilder.append(x).append("\n")
                projects.add(x)
            }

            if (w.startsWithTilde()) {
                val x = w.substring(1)
                due = x
            }
        }

        val task = Task(
            name = nameBuilder.toString().trim(),
            tags = tags,
            projects = projects,
            due = due)

        Log.d(this::class.java.simpleName, "Task: $task")

        viewModel.insertTask(task)

        return task.toString()
    }

    private fun loopString(input: String) {
        val builder = StringBuilder()

        for (c in input) {
            builder.append(c).append("\n")
        }

        setOutboxText(builder.toString())
    }

    private fun printVowels(input: String) {
        val vowels = input.filter { c -> c.isVowel() }

        loopString(vowels)
    }

}

// if (containsBang) {
//            val regexMMDDYYYY = Regex(Constants.REGEX_DATE_MMDDYYYY)
//            val regexMMDDYY = Regex(Constants.REGEX_DATE_MMDDYY)
//            val regexMMDD = Regex(Constants.REGEX_DATE_MMDD)
//            val regexYYYYMMDD = Regex(Constants.REGEX_DATE_YYYYMMDD)
//
//            val mmddyyyy = regexMMDDYYYY.containsMatchIn(text)
//            val mmddyy = regexMMDDYY.containsMatchIn(text)
//            val mmdd = regexMMDD.containsMatchIn(text)
//            val yyyymmdd = regexYYYYMMDD.containsMatchIn(text)
//
//            return when {
//                mmddyyyy -> {
//                    val parts = regexMMDDYYYY.split(text)
//
//                    val builder = StringBuilder()
//
//                    for (p in parts) {
//                        builder.append(p).append("\n")
//                    }
//
//                    builder.toString()
//                }
//                mmddyy -> {
//                    val parts = regexMMDDYY.split(text)
//
//                    val builder = StringBuilder()
//
//                    for (p in parts) {
//                        builder.append(p).append("\n")
//                    }
//
//                    builder.toString()
//                }
//                mmdd -> {
//                    val parts = regexMMDD.split(text)
//
//                    val builder = StringBuilder()
//
//                    for (p in parts) {
//                        builder.append(p).append("\n")
//                    }
//
//                    builder.toString()
//                }
//                yyyymmdd -> {
//                    val parts = regexYYYYMMDD.split(text)
//
//                    val builder = StringBuilder()
//
//                    for (p in parts) {
//                        builder.append(p).append("\n")
//                    }
//
//                    builder.toString()
//                }
//                else -> "doesn't match any regex"
//            }
//        }
