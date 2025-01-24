package com.redron.data.datasource.local

import com.redron.domain.entity.Joke
import com.redron.data.mapper.JokeEntityLocalMapper
import com.redron.data.mapper.JokeItemMapper
import javax.inject.Inject

class LocalJokesDataSourceImpl @Inject constructor(private val database: JokesDatabase):
    LocalJokesDataSource {

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
            JokeEntityLocalMapper.mapJoke(it)
        }
    }

    override suspend fun getJokes(): List<Joke> {
        return database.jokeDao().getAll().map {
            JokeEntityLocalMapper.mapJoke(it)
        }.sortedWith(compareBy<Joke> { !it.isFavorite }.thenBy { it.isFromNet } )
    }

    override suspend fun clearLoaded() {
        database.jokeDao().clearLoaded()
    }

    override suspend fun addToFavorites(uuid: String) {
        database.jokeDao().setFavorite(uuid)
    }

    override suspend fun removeFromFavorites(uuid: String) {
        database.jokeDao().setUnFavorite(uuid)
    }

    override suspend fun getFavorites(): List<Joke> {
        return database.jokeDao().getFavorite().map { JokeEntityLocalMapper.mapJoke(it) }
    }
}