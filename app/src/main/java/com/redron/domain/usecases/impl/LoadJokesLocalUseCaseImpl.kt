package com.redron.domain.usecases.impl

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.LoadJokesLocalUseCase
import javax.inject.Inject

class LoadJokesLocalUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    LoadJokesLocalUseCase {
    override suspend operator fun invoke(): List<Joke> {
        return repository.getJokes()
    }
}