package com.zachwhittle.a123tasks.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zachwhittle.a123tasks.data.dao.TagDao
import com.zachwhittle.a123tasks.data.entity.asDomainModel
import com.zachwhittle.a123tasks.ui.model.Tag

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TagRepository(private val dao: TagDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTags: LiveData<List<Tag>> = Transformations.map(dao.getAll()) {
        it.asDomainModel()
    }

    suspend fun insert(tag: Tag) {
        val dbTag = tag.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Tag: $dbTag")

        dao.insert(dbTag)
    }

    suspend fun update(tag: Tag): Int {
        val dbTag = tag.toDatabaseModel()

        Log.d(this::class.java.simpleName, "Tag $dbTag")

        return dao.update(dbTag)
    }

    suspend fun clear(): Int {
        return dao.clear()
    }
}