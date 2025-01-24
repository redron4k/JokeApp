package com.redron.data.mapper

import com.redron.data.entity.JokeEntityLocal
import com.redron.data.entity.JokeEntityCache
import com.redron.domain.entity.Joke

object JokeItemMapper {
    fun mapEntity(joke: Joke): JokeEntityLocal {
        return with(joke) {
            JokeEntityLocal(
                uuid = uuid,
                setup = jokeQuestion,
                delivery = jokeAnswer,
                category = category,
                isFromNet = isFromNet,
            )
        }
    }

    fun mapTempEntity(joke: Joke): JokeEntityCache {
        return with(joke) {
            JokeEntityCache(
                uuid = uuid,
                setup = jokeQuestion,
                delivery = jokeAnswer,
                category = category,
                dumpTime = System.currentTimeMillis(),
            )
        }
    }
}