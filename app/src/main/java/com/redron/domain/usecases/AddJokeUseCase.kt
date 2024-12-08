package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.data.repository.JokesRepository

class AddJokeUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(joke: Joke) {
        repository.addJoke(joke)
    }
}