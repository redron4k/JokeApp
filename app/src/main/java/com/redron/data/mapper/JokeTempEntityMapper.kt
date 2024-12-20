package com.redron.data.mapper

import com.redron.data.entity.JokeTempEntity
import com.redron.domain.entity.Joke

object JokeTempEntityMapper {
    fun mapJoke(joke: JokeTempEntity): Joke {
        return with(joke) {
            Joke(
                jokeQuestion = setup,
                jokeAnswer = delivery,
                category = category,
                uuid = uuid,
                isFromNet = true,
            )
        }
    }
}