package com.redron.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.data.api.RetrofitInstance
import com.redron.data.db.JokesDatabase
import com.redron.data.db.entities.JokeEntity
import com.redron.data.db.entities.JokeTempEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JokesListViewModel(private val generator: JokesGenerator) : ViewModel() {
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
        generator.addJokes(database.jokeTempDao().getAll(
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
                generator.addJokes(database.jokeDao().getAll().map {
                    Joke(it.setup, it.delivery, it.category, isFromNet = false)
                })
                try {
                    generator.addJokes(retrofitClient.getJokes().jokes)
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    loadFromCache()
                    _isLoadFromCache.value = true
                }
                _jokes.value = generator.getAllJokes()
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
                    val loadedJokes = retrofitClient.getJokes().jokes
                    generator.addJokes(loadedJokes)
                    database.jokeTempDao().clearAll()
                    database.jokeTempDao().insertAll(loadedJokes.map {
                        JokeTempEntity(
                            setup = it.jokeQuestion,
                            delivery = it.jokeAnswer,
                            category = it.category,
                            dumpTime = System.currentTimeMillis()
                        )
                    })
                    _isLoadFromCache.value = false
                } catch (_: Exception) {
                    if (!_isLoadFromCache.value) {
                        loadFromCache()
                        _isLoadFromCache.value = true
                    }
                }
                _jokes.value = generator.getAllJokes()
                _isLoading.value = false
            }
        }
    }

    fun addJoke(joke: Joke) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                generator.addJoke(joke)
                _jokes.value = generator.getAllJokes()
                database.jokeDao().insert(
                    JokeEntity(
                        setup = joke.jokeQuestion,
                        delivery = joke.jokeAnswer,
                        category = joke.category
                    )
                )
            }
        }
    }
}