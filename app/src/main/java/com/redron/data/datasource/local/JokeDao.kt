package com.redron.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.redron.data.entity.JokeEntity

@Dao
interface JokeDao {
    @Insert
    suspend fun insert(joke: JokeEntity)
    @Insert
    suspend fun insertAll(jokes: List<JokeEntity>)
    @Query("SELECT * FROM jokes")
    suspend fun getAll(): List<JokeEntity>
    @Query("DELETE FROM jokes WHERE isFromNet=1")
    suspend fun clearLoaded()
}