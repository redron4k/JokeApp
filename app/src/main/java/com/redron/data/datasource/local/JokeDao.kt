package com.redron.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.redron.data.entity.JokeEntityLocal

@Dao
interface JokeDao {
    @Insert
    suspend fun insert(joke: JokeEntityLocal)

    @Insert
    suspend fun insertAll(jokes: List<JokeEntityLocal>)

    @Query("SELECT * FROM jokes")
    suspend fun getAll(): List<JokeEntityLocal>

    @Query("SELECT * FROM jokes WHERE isFavorite=1")
    suspend fun getFavorite(): List<JokeEntityLocal>

    @Query("DELETE FROM jokes WHERE isFromNet=1 AND isFavorite=0")
    suspend fun clearLoaded()

    @Query("UPDATE jokes SET isFavorite=1 WHERE id=:uuid")
    suspend fun setFavorite(uuid: String)

    @Query("UPDATE jokes SET isFavorite=0 WHERE id=:uuid")
    suspend fun setUnFavorite(uuid: String)
}