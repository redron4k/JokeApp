package com.redron.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.redron.data.entity.JokeEntityLocal
import com.redron.data.entity.JokeEntityCache

@Database(entities = [JokeEntityLocal::class, JokeEntityCache::class], version = 1)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    abstract fun jokeTempDao(): JokeTempDao

    companion object {
        @Volatile
        var INSTANCE: JokesDatabase? = null

        fun initDatabase(context: Context) {
            var instance = INSTANCE
            if (instance == null) {
                synchronized(this) {
                    instance = INSTANCE
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            JokesDatabase::class.java,
                            "jokes_database",
                        ).build()
                        INSTANCE = instance
                    }
                }
            }
        }
    }
}