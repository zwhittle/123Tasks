package com.zachwhittle.a123tasks.ui.model

import com.zachwhittle.a123tasks.data.entity.DatabaseProject
import com.zachwhittle.a123tasks.data.entity.DatabaseTag
import java.lang.StringBuilder

data class Project(
    var id: Int = 0,
    var name: String
) {

    override fun toString(): String {
        return StringBuilder().append("Project:\n")
            .append(name).toString()
    }

    fun toDatabaseModel(): DatabaseProject {
        return DatabaseProject(
            id = id,
            name = name
        )
    }
}