package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke(id: String): Joke? {
        return repository.getJoke(id)
    }
}