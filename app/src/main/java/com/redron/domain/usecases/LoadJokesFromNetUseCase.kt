package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class LoadJokesFromNetUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke(): List<Joke> {
        return repository.loadJokesFromNet()
    }
}