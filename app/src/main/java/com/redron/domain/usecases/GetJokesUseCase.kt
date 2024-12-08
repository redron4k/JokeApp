package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.data.repository.JokesRepository

class GetJokesUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(): List<Joke> {
        return repository.getJokes()
    }
}