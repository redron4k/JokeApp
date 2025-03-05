package com.redron.domain.usecases.impl

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import javax.inject.Inject

class LoadJokesFromNetUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    LoadJokesFromNetUseCase {
    override suspend operator fun invoke(): List<Joke> {
        return repository.loadJokesFromNet()
    }
}