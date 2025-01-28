package com.redron.domain.usecases.impl

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.GetJokeUseCase
import javax.inject.Inject

class GetJokeUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    GetJokeUseCase {
    override suspend operator fun invoke(id: String): Joke? {
        return repository.getJoke(id)
    }
}