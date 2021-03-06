package com.zachwhittle.a123tasks.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zachwhittle.a123tasks.data.TaskDatabase
import com.zachwhittle.a123tasks.data.repository.TaskRepository
import com.zachwhittle.a123tasks.ui.model.Task
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val logTag = this.javaClass.simpleName

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: TaskRepository

    // LiveData gives us updated tasks when they change.
    val allTasks: LiveData<List<Task>>

    val completedTasks: LiveData<List<Task>>

    val activeTasks: LiveData<List<Task>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
        completedTasks = repository.completedTasks
        activeTasks = repository.activeTasks
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    private fun update(task: Task) = viewModelScope.launch {
        val count = repository.update(task)

        Log.d(this::class.java.simpleName, "count: $count")
    }

    fun clearAll() = viewModelScope.launch {
        val count = repository.clearAll()

        Log.d(logTag, "$count tasks deleted.")
    }

    fun toggleComplete(task: Task) {
        if (task.isComplete) {
            uncompleteTask(task)
        } else {
            completeTask(task)
        }
    }

    private fun completeTask(task: Task) {
        task.isComplete = true
        update(task)
    }

    private fun uncompleteTask(task: Task) {
        task.isComplete = false
        update(task)
    }
}
