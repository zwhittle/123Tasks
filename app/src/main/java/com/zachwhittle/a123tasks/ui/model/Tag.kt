package com.zachwhittle.a123tasks.ui.model

import com.zachwhittle.a123tasks.data.entity.DatabaseTag
import java.lang.StringBuilder

data class Tag(
    var id: Int = 0,
    var name: String) {

    override fun toString(): String {
        return StringBuilder().append("Tag:\n")
            .append(name).toString()
    }

    fun toDatabaseModel(): DatabaseTag {
        return DatabaseTag(
            id = id,
            name = name
        )
    }
}