package com.redron.data.datasource.local

import com.redron.domain.entity.Joke

interface LocalJokesDataSource {
    suspend fun getJokes(): List<Joke>

    suspend fun addJoke(joke: Joke)

    suspend fun addJokes(jokes: List<Joke>)

    suspend fun getJoke(id: String): Joke?

    suspend fun clearLoaded()
}