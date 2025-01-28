package com.redron.domain.usecases

import com.redron.domain.entity.Joke

interface AddJokesUseCase {
    suspend operator fun invoke(jokes: List<Joke>)
}