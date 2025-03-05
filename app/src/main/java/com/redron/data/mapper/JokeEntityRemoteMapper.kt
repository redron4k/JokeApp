package com.redron.data.mapper

import com.redron.data.entity.JokeEntityRemote
import com.redron.domain.entity.Joke

object JokeEntityRemoteMapper {
    fun mapJoke(joke: JokeEntityRemote): Joke {
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