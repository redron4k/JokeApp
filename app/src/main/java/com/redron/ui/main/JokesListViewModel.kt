package com.redron.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.data.api.createRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokesListViewModel(private val generator: JokesGenerator) : ViewModel() {
    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val retrofitClient = createRetrofit()

    fun loadJokes() {
        if (_isLoading.value) return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                generator.addJokes(retrofitClient.getJokes().jokes)
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
            }
        }
    }
}