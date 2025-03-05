package com.redron.domain.usecases.impl

import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import javax.inject.Inject

class RemoveFromFavoritesUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    RemoveFromFavoritesUseCase {
    override suspend operator fun invoke(id: String) {
        repository.removeFromFavorites(id)
    }
}