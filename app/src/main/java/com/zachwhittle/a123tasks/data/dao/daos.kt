package com.zachwhittle.a123tasks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zachwhittle.a123tasks.data.entity.DatabaseProject
import com.zachwhittle.a123tasks.data.entity.DatabaseTag
import com.zachwhittle.a123tasks.data.entity.DatabaseTask

@Dao
interface TaskDao {

    @Query("select * from databasetask order by id desc")
    fun getAll(): LiveData<List<DatabaseTask>>

    @Query("select * from databasetask where isComplete = 1")
    fun getCompleted(): LiveData<List<DatabaseTask>>

    @Query("select * from databasetask where isComplete = 0")
    fun getActive(): LiveData<List<DatabaseTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: DatabaseTask): Long

    @Update
    suspend fun update(task: DatabaseTask): Int

    @Query("delete from databasetask")
    suspend fun clear(): Int
}

@Dao
interface ProjectDao {

    @Query("select * from databaseproject order by name asc")
    fun getAll(): LiveData<List<DatabaseProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun seed(projects: List<DatabaseProject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: DatabaseProject): Long

    @Update
    suspend fun update(project: DatabaseProject): Int

    @Query("delete from databaseproject")
    suspend fun clear(): Int
}

@Dao
interface TagDao {
    @Query("select * from databasetag order by name asc")
    fun getAll(): LiveData<List<DatabaseTag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun seed(tags: List<DatabaseTag>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: DatabaseTag): Long

    @Update
    suspend fun update(tag: DatabaseTag): Int

    @Query("delete from databasetag")
    suspend fun clear(): Int
}