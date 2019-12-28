package com.zachwhittle.a123tasks.data.entity

import android.graphics.Movie
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zachwhittle.a123tasks.ui.model.Task

@Entity
data class DatabaseTask(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var projects: String,
    var tags: String,
    var due: String?)

/**
* Map DatabaseMovies to domain entities
*/
fun List<DatabaseTask>.asDomainModel(): List<Task> {
    return map {
        Task(name = it.name,
            projects = ArrayList(it.projects.split(",")),
            tags = ArrayList(it.tags.split(",")),
            due = it.due)
    }
}