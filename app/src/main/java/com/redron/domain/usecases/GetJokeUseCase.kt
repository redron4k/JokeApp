package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.data.repository.JokesRepository

class GetJokeUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(id: String): Joke? {
        return repository.getJoke(id)
    }
}