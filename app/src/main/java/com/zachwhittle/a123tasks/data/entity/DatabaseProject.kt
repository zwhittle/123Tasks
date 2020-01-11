package com.zachwhittle.a123tasks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zachwhittle.a123tasks.ui.model.Project
import com.zachwhittle.a123tasks.ui.model.Task

@Entity
data class DatabaseProject(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

fun List<DatabaseProject>.asDomainModel(): List<Project> {
    return map {
        Project(
            id = it.id,
            name = it.name
        )
    }
}