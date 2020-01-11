package com.zachwhittle.a123tasks.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zachwhittle.a123tasks.data.dao.ProjectDao
import com.zachwhittle.a123tasks.data.entity.asDomainModel
import com.zachwhittle.a123tasks.ui.model.Project

class ProjectRepository (private val dao: ProjectDao) {

    val allProjects: LiveData<List<Project>> = Transformations.map(dao.getAll()) {
        it.asDomainModel()
    }

    suspend fun insert(project: Project) {
        val dbProject = project.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Project: $dbProject")

        dao.insert((dbProject))
    }

    suspend fun update(project: Project): Int {
        val dbProject = project.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Project: $dbProject")

        return dao.update((dbProject))
    }

    suspend fun clear(): Int {
        return dao.clear()
    }
}