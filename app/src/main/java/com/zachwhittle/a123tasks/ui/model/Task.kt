package com.zachwhittle.a123tasks.ui.model

import android.util.Log
import com.zachwhittle.a123tasks.data.entity.DatabaseTask
import com.zachwhittle.a123tasks.util.withCommas
import com.zachwhittle.a123tasks.util.withNewLines

data class Task(
    var id: Int = 0,
    var name: String,
    var tags: ArrayList<String>,
    var projects: ArrayList<String>,
    var due: String? = null,
    var isComplete: Boolean = false) {

    override fun toString(): String {
        return StringBuilder().append("Task:\n")
            .append(name).append("\n\n")
            .append("Projects:\n")
            .append(projects.withNewLines()).append("\n\n")
            .append("Tags:\n")
            .append(tags.withNewLines()).append("\n\n")
            .append("Due:\n")
            .append(due)
            .toString()
    }

    fun toDatabaseModel(): DatabaseTask {
        Log.d(this::class.java.simpleName, "tags: ${tags.withCommas()}")
        Log.d(this::class.java.simpleName, "projects: ${projects.withCommas()}")

        return DatabaseTask(
            id = id,
            name = name,
            tags = tags.withCommas(),
            projects = projects.withCommas(),
            due = due,
            isComplete = isComplete)
    }

}

