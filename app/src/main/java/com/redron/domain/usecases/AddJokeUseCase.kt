package com.redron.domain.usecases

import com.redron.domain.entity.Joke

interface AddJokeUseCase {
    suspend operator fun invoke(joke: Joke)
}