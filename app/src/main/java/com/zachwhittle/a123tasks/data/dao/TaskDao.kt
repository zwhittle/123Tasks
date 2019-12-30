package com.zachwhittle.a123tasks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zachwhittle.a123tasks.data.entity.DatabaseTask
import com.zachwhittle.a123tasks.ui.model.Task

@Dao
interface TaskDao {

    @Query("select * from databasetask order by isComplete")
    fun getAllTasks(): LiveData<List<DatabaseTask>>

    @Query("select * from databasetask where isComplete = 1")
    fun getCompletedTasks(): LiveData<List<DatabaseTask>>

    @Query("select * from databasetask where isComplete = 0")
    fun getActiveTasks(): LiveData<List<DatabaseTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: DatabaseTask): Long

    @Update
    suspend fun update(task: DatabaseTask): Int

    @Query("delete from databasetask")
    suspend fun deleteAll(): Int
}