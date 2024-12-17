package com.redron.domain.usecases

import com.redron.domain.repository.JokesRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke(id: String) {
        repository.removeFromFavorites(id)
    }
}