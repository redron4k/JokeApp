package com.redron.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.RefreshCacheUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import javax.inject.Inject


class JokesListViewModelFactory @Inject constructor(
    private val loadJokesLocal: LoadJokesLocalUseCase,
    private val addJoke: AddJokeUseCase,
    private val addJokes: AddJokesUseCase,
    private val clearLoadedJokes: ClearLoadedJokesUseCase,
    private val loadJokesFromNet: LoadJokesFromNetUseCase,
    private val addToFavorites: AddToFavoritesUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase,
    private val refreshCache: RefreshCacheUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == JokesListViewModel::class.java)
        return JokesListViewModel(
            loadJokesLocal = loadJokesLocal,
            addJoke = addJoke,
            addJokes = addJokes,
            clearLoadedJokes = clearLoadedJokes,
            loadJokesFromNet = loadJokesFromNet,
            addToFavorites = addToFavorites,
            removeFromFavorites = removeFromFavorites,
            refreshCache = refreshCache,
        ) as T
    }
}
