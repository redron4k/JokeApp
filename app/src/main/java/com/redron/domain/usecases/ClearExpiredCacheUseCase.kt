package com.redron.domain.usecases

import com.redron.domain.repository.JokesRepository

class ClearExpiredCacheUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke(criticalTime: Long) {
        repository.clearExpiredCache(criticalTime)
    }
}