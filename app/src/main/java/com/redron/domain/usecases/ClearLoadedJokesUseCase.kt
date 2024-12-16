package com.redron.domain.usecases

import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class ClearLoadedJokesUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke() {
        repository.clearLoaded()
    }
}