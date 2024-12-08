package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.data.repository.JokesRepository

class AddJokesUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(jokes: List<Joke>) {
        repository.addJokes(jokes)
        repository.cacheJokes(jokes)
    }
}