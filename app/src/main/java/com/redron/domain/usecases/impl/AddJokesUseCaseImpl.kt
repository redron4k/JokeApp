package com.redron.domain.usecases.impl

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.AddJokesUseCase
import javax.inject.Inject

class AddJokesUseCaseImpl @Inject constructor(private val repository: JokesRepository):
    AddJokesUseCase {
    override suspend operator fun invoke(jokes: List<Joke>) {
        repository.addJokes(jokes)
        repository.saveJokesToCache(jokes)
    }
}