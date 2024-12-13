package com.redron.data.datasource.local

import com.redron.data.mapper.JokeItemMapper
import com.redron.data.mapper.JokeTempEntityMapper
import com.redron.domain.entity.Joke

class CacheJokesDataSourceImpl(private val database: JokesDatabase) : CacheJokesDataSource {
    override suspend fun saveJokes(jokes: List<Joke>) {
        database.jokeTempDao().clearAll()
        database.jokeTempDao().insertAll(
            jokes.map {
                JokeItemMapper.mapTempEntity(it)
            }
        )
    }

    override suspend fun loadJokes(criticalTime: Long): List<Joke> {
        return database.jokeTempDao().getAll(criticalTime).map {
            JokeTempEntityMapper.mapJoke(it)
        }
    }

    override suspend fun clearExpired(criticalTime: Long) {
        database.jokeTempDao().deleteExpired(criticalTime)
    }
}