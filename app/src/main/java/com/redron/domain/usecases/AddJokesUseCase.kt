package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class AddJokesUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke(jokes: List<Joke>) {
        repository.addJokes(jokes)
        repository.saveJokesToCache(jokes)
    }
}