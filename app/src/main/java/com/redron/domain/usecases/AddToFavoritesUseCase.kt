package com.redron.domain.usecases

interface AddToFavoritesUseCase {
    suspend operator fun invoke(id: String)
}