package com.redron.presentation.single_joke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redron.domain.usecases.GetJokeUseCase
import javax.inject.Inject

class JokeDetailsViewModelFactory @Inject constructor(
    private val getJoke: GetJokeUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == JokeDetailsViewModel::class.java)
        return JokeDetailsViewModel(
            getJoke = getJoke,
        ) as T
    }
}