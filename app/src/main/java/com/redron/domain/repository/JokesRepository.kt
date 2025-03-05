package com.redron.domain.repository

import com.redron.domain.entity.Joke

interface JokesRepository {
    suspend fun addJoke(joke: Joke)

    suspend fun addJokes(jokes: List<Joke>)

    suspend fun getJoke(id: String): Joke?

    suspend fun getJokes(): List<Joke>

    suspend fun clearLoaded()

    suspend fun saveJokesToCache(jokes: List<Joke>)

    suspend fun loadActualJokesFromCache(): List<Joke>

    suspend fun clearExpiredCache(criticalTime: Long)

    suspend fun refreshCache()

    suspend fun loadJokesFromNet(): List<Joke>

    suspend fun addToFavorites(uuid: String)

    suspend fun removeFromFavorites(uuid: String)
}