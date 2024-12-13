package com.redron.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.domain.entity.Joke
import com.redron.data.datasource.remote.RetrofitInstance
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.ClearExpiredCacheUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.GetJokesUseCase
import com.redron.domain.usecases.LoadJokesFromCacheUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JokesListViewModel(
    private val getJokes: GetJokesUseCase,
    private val addJoke: AddJokeUseCase,
    private val addJokes: AddJokesUseCase,
    private val getJokesFromCache: LoadJokesFromCacheUseCase,
    private val deleteExpiredCache: ClearExpiredCacheUseCase,
    private val clearLoadedJokes: ClearLoadedJokesUseCase,
    private val getJokesFromNet: LoadJokesFromNetUseCase
) : ViewModel() {

    companion object {
        private val EXPIRATION_TIME = TimeUnit.HOURS.toMillis(24)
    }

    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadFromCache = MutableStateFlow<Boolean>(false)
    val isLoadFromCache: StateFlow<Boolean> = _isLoadFromCache

    private suspend fun loadFromCache() {
        val criticalTime = System.currentTimeMillis() - EXPIRATION_TIME
        addJokes(getJokesFromCache(criticalTime))
        deleteExpiredCache(criticalTime)
    }

    fun initJokes() {
        if (_isLoading.value) return
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    addJokes(getJokesFromNet())
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    loadFromCache()
                    _isLoadFromCache.value = true
                }
                _jokes.value = getJokes()
                _isLoading.value = false
            }
        }
    }

    fun loadMoreJokes() {
        if (_isLoading.value) return
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    addJokes(getJokesFromNet())
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    if (!_isLoadFromCache.value) {
                        loadFromCache()
                        _isLoadFromCache.value = true
                    }
                }
                _jokes.value = getJokes()
                _isLoading.value = false
            }
        }
    }

    fun addNewJoke(joke: Joke) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addJoke(joke)
                _jokes.value = getJokes()
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
}