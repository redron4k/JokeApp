package com.redron.domain.usecases.impl

import com.redron.domain.repository.JokesRepository
import com.redron.domain.usecases.AddToFavoritesUseCase
import javax.inject.Inject

class AddToFavoritesUseCaseImpl @Inject constructor(private val repository: JokesRepository) :
    AddToFavoritesUseCase {
    override suspend operator fun invoke(id: String) {
        repository.addToFavorites(id)
    }
}