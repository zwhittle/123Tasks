package com.zachwhittle.a123tasks.ui.model

import com.zachwhittle.a123tasks.data.entity.DatabaseTask
import com.zachwhittle.a123tasks.util.withNewLines

data class Task(
    var name: String,
    var tags: ArrayList<String>,
    var projects: ArrayList<String>,
    var due: String? = null) {

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
        return DatabaseTask(name = name,
            tags = tags.toString(),
            projects = projects.toString(),
            due = due)
    }

}

