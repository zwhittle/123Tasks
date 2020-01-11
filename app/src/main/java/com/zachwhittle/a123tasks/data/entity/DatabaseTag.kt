package com.zachwhittle.a123tasks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zachwhittle.a123tasks.ui.model.Tag

@Entity
data class DatabaseTag(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

fun List<DatabaseTag>.asDomainModel(): List<Tag> {
    return map {
        Tag(
            id = it.id,
            name = it.name
        )
    }
}