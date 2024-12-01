package com.redron.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.redron.data.db.DAO.JokeDao
import com.redron.data.db.DAO.JokeTempDao
import com.redron.data.db.entities.JokeEntity
import com.redron.data.db.entities.JokeTempEntity

@Database(entities = [JokeEntity::class, JokeTempEntity::class], version = 1)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    abstract fun jokeTempDao(): JokeTempDao

    companion object {
        @Volatile
        lateinit var INSTANCE: JokesDatabase

        fun initDatabase(context: Context) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                JokesDatabase::class.java,
                "jokes_database"
            ).build()
            INSTANCE = instance
        }
    }
}