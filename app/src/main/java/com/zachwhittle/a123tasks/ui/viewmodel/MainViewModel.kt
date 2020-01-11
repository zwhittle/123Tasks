package com.zachwhittle.a123tasks.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zachwhittle.a123tasks.data.TaskDatabase
import com.zachwhittle.a123tasks.data.repository.ProjectRepository
import com.zachwhittle.a123tasks.data.repository.TagRepository
import com.zachwhittle.a123tasks.data.repository.TaskRepository
import com.zachwhittle.a123tasks.ui.model.Project
import com.zachwhittle.a123tasks.ui.model.Tag
import com.zachwhittle.a123tasks.ui.model.Task
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val logTag = this.javaClass.simpleName

    // The ViewModel maintains a reference to the taskRepository to get data.
    private val taskRepository: TaskRepository
    private val projectRepository: ProjectRepository
    private val tagRepository: TagRepository

    // LiveData gives us updated tasks when they change.
    val allTasks: LiveData<List<Task>>
    val completedTasks: LiveData<List<Task>>
    val activeTasks: LiveData<List<Task>>

    val allProjects: LiveData<List<Project>>
    val allTags: LiveData<List<Tag>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val database = TaskDatabase.getInstance(application)

        val taskDao = database.taskDao()
        val projectDao = database.projectDao()
        val tagDao = database.tagDao()

        taskRepository = TaskRepository(taskDao)
        projectRepository = ProjectRepository(projectDao)
        tagRepository = TagRepository(tagDao)

        allTasks = taskRepository.allTasks
        completedTasks = taskRepository.completedTasks
        activeTasks = taskRepository.activeTasks

        allProjects = projectRepository.allProjects
        allTags = tagRepository.allTags
    }

    /**
     * The implementation of insertTask() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        val count = taskRepository.update(task)

        Log.d(this::class.java.simpleName, "count: $count")
    }

    fun clearTasks() = viewModelScope.launch {
        val count = taskRepository.clear()

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
        updateTask(task)
    }

    private fun uncompleteTask(task: Task) {
        task.isComplete = false
        updateTask(task)
    }


    /**
     * Projects
     */
    fun insertProject(project: Project) = viewModelScope.launch {
        projectRepository.insert(project)
    }

    private fun updateProject(project: Project) = viewModelScope.launch {
        val count = projectRepository.update(project)

        Log.d(this::class.java.simpleName, "count: $count")
    }

    fun clearProjects() = viewModelScope.launch {
        val count = projectRepository.clear()

        Log.d(logTag, "$count projects deleted.")
    }

    /**
     * Tag
     */
    fun insertTag(tag: Tag) = viewModelScope.launch {
        tagRepository.insert(tag)
    }

    private fun updateTag(tag: Tag) = viewModelScope.launch {
        val count = tagRepository.update(tag)

        Log.d(this::class.java.simpleName, "count: $count")
    }

    fun clearTags() = viewModelScope.launch {
        val count = tagRepository.clear()

        Log.d(logTag, "$count tags deleted.")
    }
}
