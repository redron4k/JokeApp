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

    @Query("SELECT * FROM jokes WHERE isFavorite=1")
    suspend fun getFavorite(): List<JokeEntity>

    @Query("DELETE FROM jokes WHERE isFromNet=1 AND isFavorite=0")
    suspend fun clearLoaded()

    @Query("UPDATE jokes SET isFavorite=1 WHERE id=:uuid")
    suspend fun setFavorite(uuid: String)

    @Query("UPDATE jokes SET isFavorite=0 WHERE id=:uuid")
    suspend fun setUnFavorite(uuid: String)
}