package com.redron.di.modules

import android.content.Context
import androidx.room.Room
import com.redron.data.datasource.local.JokeDao
import com.redron.data.datasource.local.JokeTempDao
import com.redron.data.datasource.local.JokesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataDbModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): JokesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            JokesDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideJokeDao(database: JokesDatabase): JokeDao {
        return database.jokeDao()
    }

    @Provides
    @Singleton
    fun provideJokeTempDao(database: JokesDatabase): JokeTempDao {
        return database.jokeTempDao()
    }

    companion object {
        private const val DB_NAME = "jokes_database"
    }
}