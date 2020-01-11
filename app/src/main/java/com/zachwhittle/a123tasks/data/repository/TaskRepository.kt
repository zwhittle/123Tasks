package com.zachwhittle.a123tasks.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zachwhittle.a123tasks.data.dao.TaskDao
import com.zachwhittle.a123tasks.data.entity.asDomainModel
import com.zachwhittle.a123tasks.ui.model.Task

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = Transformations.map(taskDao.getAll()) {
        it.asDomainModel()
    }

    val completedTasks: LiveData<List<Task>> = Transformations.map(taskDao.getCompleted()) {
        it.asDomainModel()
    }

    val activeTasks: LiveData<List<Task>> = Transformations.map(taskDao.getActive()) {
        it.asDomainModel()
    }

    suspend fun insert(task: Task) {
        val dbTask = task.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Task: $dbTask")

        taskDao.insert(dbTask)
    }

    suspend fun update(task: Task): Int {
        val dbTask = task.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Task $dbTask")

        return taskDao.update(dbTask)
    }

    suspend fun clear(): Int {
        return taskDao.clear()
    }
}