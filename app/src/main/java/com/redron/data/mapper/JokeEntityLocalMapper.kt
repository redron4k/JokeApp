package com.redron.data.mapper

import com.redron.data.entity.JokeEntityLocal
import com.redron.domain.entity.Joke

object JokeEntityLocalMapper {
    fun mapJoke(joke: JokeEntityLocal): Joke {
        return with(joke) {
            Joke(
                jokeQuestion = setup,
                jokeAnswer = delivery,
                category = category,
                uuid = uuid,
                isFromNet = isFromNet,
                isFavorite = isFavorite,
            )
        }
    }
}