package com.redron.data.mapper

import com.redron.data.entity.JokeEntityCache
import com.redron.domain.entity.Joke

object JokeEntityCacheMapper {
    fun mapJoke(joke: JokeEntityCache): Joke {
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