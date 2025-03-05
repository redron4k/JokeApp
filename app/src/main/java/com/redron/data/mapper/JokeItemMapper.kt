package com.redron.data.mapper

import com.redron.data.entity.JokeEntityLocal
import com.redron.data.entity.JokeEntityCache
import com.redron.domain.entity.Joke

object JokeItemMapper {
    fun mapJokeEntityLocal(joke: Joke): JokeEntityLocal {
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

    fun mapJokeEntityCache(joke: Joke): JokeEntityCache {
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