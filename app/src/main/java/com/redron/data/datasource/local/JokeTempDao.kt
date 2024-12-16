package com.redron.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.redron.data.entity.JokeTempEntity

@Dao
interface JokeTempDao {
    @Insert
    suspend fun insert(joke: JokeTempEntity)

    @Insert
    suspend fun insertAll(jokes: List<JokeTempEntity>)

    @Query("SELECT * FROM jokesTemp WHERE dumpTime > :criticalTime")
    suspend fun getAll(criticalTime: Long): List<JokeTempEntity>

    @Query("DELETE FROM jokesTemp")
    suspend fun clearAll()

    @Query("DELETE FROM jokesTemp WHERE dumpTime < :criticalTime")
    suspend fun deleteExpired(criticalTime: Long)
}