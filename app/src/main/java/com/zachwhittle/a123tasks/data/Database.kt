package com.zachwhittle.a123tasks.data

import android.content.Context
import android.telecom.Call
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zachwhittle.a123tasks.data.dao.ProjectDao
import com.zachwhittle.a123tasks.data.dao.TagDao
import com.zachwhittle.a123tasks.data.dao.TaskDao
import com.zachwhittle.a123tasks.data.entity.DatabaseProject
import com.zachwhittle.a123tasks.data.entity.DatabaseTag
import com.zachwhittle.a123tasks.data.entity.DatabaseTask
import com.zachwhittle.a123tasks.ui.model.Project
import com.zachwhittle.a123tasks.ui.model.Tag
import com.zachwhittle.a123tasks.util.ioThread

@Database(
    entities = [DatabaseTask::class, DatabaseProject::class, DatabaseTag::class],
    version = 2,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao
    abstract fun tagDao(): TagDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "database.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).tagDao().seed(tagSeed)
                            getInstance(context).projectDao().seed(projectSeed)
                        }
                    }
                }).build()

        val tagSeed = listOf(DatabaseTag(name = "home"), DatabaseTag(name = "work"))
        val projectSeed = listOf(DatabaseProject(name = "Groceries"), DatabaseProject(name = "Trash"))
    }
}