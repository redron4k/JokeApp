package com.redron.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.redron.data.entity.JokeEntityCache

@Dao
interface JokeTempDao {
    @Insert
    suspend fun insert(joke: JokeEntityCache)

    @Insert
    suspend fun insertAll(jokes: List<JokeEntityCache>)

    @Query("SELECT * FROM jokesTemp WHERE dumpTime > :criticalTime")
    suspend fun getAll(criticalTime: Long): List<JokeEntityCache>

    @Query("DELETE FROM jokesTemp")
    suspend fun clearAll()

    @Query("DELETE FROM jokesTemp WHERE dumpTime < :criticalTime")
    suspend fun deleteExpired(criticalTime: Long)
}