package com.redron.domain.usecases

import com.redron.domain.repository.JokesRepository

class ClearLoadedJokesUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke() {
        repository.clearLoaded()
    }
}