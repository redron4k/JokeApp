package com.redron.data.repository

import com.redron.domain.entity.Joke
import com.redron.data.datasource.local.LocalJokesDataSource
import com.redron.data.datasource.remote.RemoteJokesDataSource

class JokesRepository(
    private val remoteDataSource: RemoteJokesDataSource,
    private val localDataSource: LocalJokesDataSource
) {
    suspend fun getJokes(): List<Joke> {
        return localDataSource.getJokes()
    }

    suspend fun addJoke(joke: Joke) {
        localDataSource.addJoke(joke)
    }

    suspend fun addJokes(jokes: List<Joke>) {
        localDataSource.addJokes(jokes)
    }

    suspend fun cacheJokes(jokes: List<Joke>) {
        localDataSource.cacheJokes(jokes)
    }

    suspend fun getJoke(id: String): Joke? {
        return localDataSource.getJoke(id)
    }
}