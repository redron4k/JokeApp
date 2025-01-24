package com.redron.domain.usecases

import com.redron.domain.entity.Joke
import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class RefreshCacheUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke() {
        repository.refreshCache()
    }
}