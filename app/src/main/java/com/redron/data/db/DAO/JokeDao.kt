package com.redron.data.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.redron.data.db.entities.JokeEntity

@Dao
interface JokeDao {
    @Insert
    suspend fun insert(joke: JokeEntity)
    @Insert
    suspend fun insertAll(jokes: List<JokeEntity>)
    @Query("SELECT * FROM jokes")
    suspend fun getAll(): List<JokeEntity>
}