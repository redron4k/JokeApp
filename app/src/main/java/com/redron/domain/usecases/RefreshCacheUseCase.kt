package com.redron.domain.usecases

interface RefreshCacheUseCase {
    suspend operator fun invoke()
}