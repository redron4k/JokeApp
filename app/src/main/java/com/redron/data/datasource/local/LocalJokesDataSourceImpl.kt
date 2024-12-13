package com.redron.data.datasource.local

import com.redron.domain.entity.Joke
import com.redron.data.mapper.JokeEntityMapper
import com.redron.data.mapper.JokeItemMapper

class LocalJokesDataSourceImpl(private val database: JokesDatabase) : LocalJokesDataSource {

    override suspend fun addJoke(joke: Joke) {
        database.jokeDao().insert(
            JokeItemMapper.mapEntity(joke)
        )
    }

    override suspend fun addJokes(jokes: List<Joke>) {
        database.jokeDao().insertAll(
            jokes.map {
                JokeItemMapper.mapEntity(it)
            }
        )
    }

    override suspend fun getJoke(id: String): Joke? {
        return database.jokeDao().getAll().find {
            it.uuid == id
        }?.let {
            Joke(it.setup, it.delivery, it.category, isFromNet = false)
        }
    }

    override suspend fun getJokes(): List<Joke> {
        return database.jokeDao().getAll().map {
            JokeEntityMapper.mapJoke(it)
        }.sortedBy { it.isFromNet }
    }

    override suspend fun clearLoaded() {
        database.jokeDao().clearLoaded()
    }
}