package com.redron.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.domain.entity.Joke
import com.redron.data.datasource.remote.RetrofitInstance
import com.redron.data.datasource.local.JokesDatabase
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.GetJokesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JokesListViewModel(
    private val getJokes: GetJokesUseCase,
    private val addJoke: AddJokeUseCase,
    private val addJokes: AddJokesUseCase
) : ViewModel() {
    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadFromCache = MutableStateFlow<Boolean>(false)
    val isLoadFromCache: StateFlow<Boolean> = _isLoadFromCache

    private val retrofitClient = RetrofitInstance.retrofitClient
    private val database = JokesDatabase.INSTANCE

    private suspend fun loadFromCache() {
        val criticalTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
        addJokes(database.jokeTempDao().getAll(
            criticalTime
        ).map {
            Joke(it.setup, it.delivery, it.category)
        })
        database.jokeTempDao().deleteExpired(criticalTime)
    }

    fun initJokes() {
        if (_isLoading.value) return
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    addJokes(retrofitClient.getJokes().jokes)
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
                    addJokes(retrofitClient.getJokes().jokes)
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
                database.jokeDao().clearLoaded()
            }
        }
    }
}