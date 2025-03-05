package com.redron.domain.usecases.impl

import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import javax.inject.Inject

class ClearLoadedJokesUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    ClearLoadedJokesUseCase {
    override suspend operator fun invoke() {
        repository.clearLoaded()
    }
}