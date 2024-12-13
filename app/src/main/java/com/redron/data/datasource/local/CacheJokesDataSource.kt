package com.redron.data.datasource.local

import com.redron.domain.entity.Joke

interface CacheJokesDataSource {
    suspend fun saveJokes(jokes: List<Joke>)

    suspend fun loadJokes(criticalTime: Long): List<Joke>

    suspend fun clearExpired(criticalTime: Long)
}