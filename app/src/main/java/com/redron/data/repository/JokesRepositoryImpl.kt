package com.redron.data.repository

import com.redron.data.datasource.local.CacheJokesDataSource
import com.redron.domain.entity.Joke
import com.redron.data.datasource.local.LocalJokesDataSource
import com.redron.data.datasource.remote.RemoteJokesDataSource
import com.redron.domain.repository.JokesRepository
import java.util.concurrent.TimeUnit

class JokesRepositoryImpl(
    private val cacheDataSource: CacheJokesDataSource,
    private val localDataSource: LocalJokesDataSource,
    private val remoteDataSource: RemoteJokesDataSource
) : JokesRepository {

    companion object {
        private val EXPIRATION_TIME = TimeUnit.HOURS.toMillis(24)
    }

    override suspend fun addJoke(joke: Joke) {
        localDataSource.addJoke(joke)
    }

    override suspend fun addJokes(jokes: List<Joke>) {
        localDataSource.addJokes(jokes)
    }

    override suspend fun getJoke(id: String): Joke? {
        return localDataSource.getJoke(id)
    }

    override suspend fun getJokes(): List<Joke> {
        return localDataSource.getJokes()
    }

    override suspend fun clearLoaded() {
        localDataSource.clearLoaded()
    }

    override suspend fun saveJokesToCache(jokes: List<Joke>) {
        cacheDataSource.saveJokes(jokes)
    }

    override suspend fun loadActualJokesFromCache(): List<Joke> {
        val criticalTime = System.currentTimeMillis() - EXPIRATION_TIME
        clearExpiredCache(criticalTime)
        return cacheDataSource.loadJokes(criticalTime)
    }

    override suspend fun clearExpiredCache(criticalTime: Long) {
        cacheDataSource.clearExpired(criticalTime)
    }

    override suspend fun loadJokesFromNet(): List<Joke> {
        return remoteDataSource.getJokes()
    }
}