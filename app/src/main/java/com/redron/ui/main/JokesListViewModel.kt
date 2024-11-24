package com.redron.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.data.Joke
import com.redron.data.JokesGenerator
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

    fun generateJokes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                generator.generate()
                _jokes.value = generator.getAllJokes()
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