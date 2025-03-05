package com.redron.domain.usecases

interface RemoveFromFavoritesUseCase {
    suspend operator fun invoke(id: String)
}