package com.redron.domain.usecases.impl

import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.RefreshCacheUseCase
import javax.inject.Inject

class RefreshCacheUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    RefreshCacheUseCase {
    override suspend operator fun invoke() {
        repository.refreshCache()
    }
}