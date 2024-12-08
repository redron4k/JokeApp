package com.redron.data.mapper

import com.redron.data.entity.JokeEntity
import com.redron.domain.entity.Joke

object JokeEntityMapper {
    fun mapJoke(joke: JokeEntity): Joke {
        return with(joke) {
            Joke(jokeQuestion = setup,
                jokeAnswer = delivery,
                category = category,
                uuid = uuid,
                isFromNet = isFromNet)
        }
    }
}