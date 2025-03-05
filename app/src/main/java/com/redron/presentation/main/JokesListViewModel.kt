package com.redron.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.domain.entity.Joke
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.RefreshCacheUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JokesListViewModel @Inject constructor(
    private val loadJokesLocal: LoadJokesLocalUseCase,
    private val addJoke: AddJokeUseCase,
    private val addJokes: AddJokesUseCase,
    private val clearLoadedJokes: ClearLoadedJokesUseCase,
    private val loadJokesFromNet: LoadJokesFromNetUseCase,
    private val addToFavorites: AddToFavoritesUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase,
    private val refreshCache: RefreshCacheUseCase,
) : ViewModel() {

    private val _jokesState = MutableStateFlow(JokesListState())
    val jokesState: StateFlow<JokesListState> = _jokesState.asStateFlow()

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error.asSharedFlow()

    private val _isLoadFromCache = MutableStateFlow<Boolean>(false)
    val isLoadFromCache: StateFlow<Boolean> = _isLoadFromCache.asStateFlow()

    fun initJokes() {
        if (_jokesState.value.isLoading) return
        _jokesState.value = _jokesState.value.copy(isLoading = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    addJokes(loadJokesFromNet())
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    refreshCache()
                    _isLoadFromCache.value = true
                }
                _jokesState.value = _jokesState.value.copy(
                    jokes = loadJokesLocal(),
                    isLoading = false
                )
            }
        }
    }

    fun loadMoreJokes() {
        if (_jokesState.value.isLoading) return
        _jokesState.value = _jokesState.value.copy(isLoading = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    addJokes(loadJokesFromNet())
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    if (!_isLoadFromCache.value) {
                        refreshCache()
                        _isLoadFromCache.value = true
                    }
                }
                _jokesState.value = _jokesState.value.copy(
                    jokes = loadJokesLocal(),
                    isLoading = false
                )
            }
        }
    }

    fun addNewJoke(joke: Joke) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addJoke(joke)
                _jokesState.value = _jokesState.value.copy(jokes = loadJokesLocal())
            }
        }
    }

    fun clearLoaded() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                clearLoadedJokes()
            }
        }
    }

    fun onFavClicked(joke: Joke) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (joke.isFavorite) {
                    removeFromFavorites(joke.uuid)
                } else {
                    addToFavorites(joke.uuid)
                }
                _jokesState.value = _jokesState.value.copy(jokes = loadJokesLocal())
            }
        }
    }

    data class JokesListState(
        val jokes: List<Joke> = emptyList(),
        val isLoading: Boolean = false,
    )
}