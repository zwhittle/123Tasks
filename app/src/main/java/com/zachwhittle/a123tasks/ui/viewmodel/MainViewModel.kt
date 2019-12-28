package com.zachwhittle.a123tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zachwhittle.a123tasks.data.TaskDatabase
import com.zachwhittle.a123tasks.data.repository.TaskRepository
import com.zachwhittle.a123tasks.ui.model.Task
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class MainViewModel(application: Application) : AndroidViewModel(application) {


    // The ViewModel maintains a reference to the repository to get data.
    private val repository: TaskRepository

    // LiveData gives us updated tasks when they change.
    val allTasks: LiveData<List<Task>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
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
}
