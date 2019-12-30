package com.zachwhittle.a123tasks.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.databinding.MainFragmentBinding
import com.zachwhittle.a123tasks.ui.adapter.TaskRVAdapter
import com.zachwhittle.a123tasks.ui.model.Task
import com.zachwhittle.a123tasks.ui.viewmodel.MainViewModel
import com.zachwhittle.a123tasks.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var inbox: EditText
    private lateinit var outbox: TextView

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskRVAdapter

    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false)

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

        viewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            adapter.tasks = tasks
            adapter.notifyDataSetChanged()
        })
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

        viewModel.insert(task)

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
