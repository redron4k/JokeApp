package com.redron.domain.usecases.impl

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.AddJokeUseCase
import javax.inject.Inject

class AddJokeUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    AddJokeUseCase {
    override suspend operator fun invoke(joke: Joke) {
        repository.addJoke(joke)
    }
}