package com.redron.data.mapper

import com.redron.data.entity.JokeEntity
import com.redron.data.entity.JokeTempEntity
import com.redron.domain.entity.Joke

object JokeItemMapper {
    fun mapEntity(joke: Joke): JokeEntity {
        return with(joke) {
            JokeEntity(
                uuid = uuid,
                setup = jokeQuestion,
                delivery = jokeAnswer,
                category = category,
                isFromNet = isFromNet,
            )
        }
    }

    fun mapTempEntity(joke: Joke): JokeTempEntity {
        return with(joke) {
            JokeTempEntity(
                uuid = uuid,
                setup = jokeQuestion,
                delivery = jokeAnswer,
                category = category,
                dumpTime = System.currentTimeMillis(),
            )
        }
    }
}