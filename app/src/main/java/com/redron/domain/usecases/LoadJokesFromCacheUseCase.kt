package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository

class LoadJokesFromCacheUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(): List<Joke> {
        return repository.loadActualJokesFromCache()
    }
}