package com.zachwhittle.a123tasks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zachwhittle.a123tasks.data.entity.DatabaseTask
import com.zachwhittle.a123tasks.ui.model.Task

@Dao
interface TaskDao {

    @Query("select * from databasetask")
    fun getAllTasks(): LiveData<List<DatabaseTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: DatabaseTask): Long

    @Update
    suspend fun update(task: DatabaseTask): Int

    @Query("delete from databasetask")
    suspend fun deleteAll()
}