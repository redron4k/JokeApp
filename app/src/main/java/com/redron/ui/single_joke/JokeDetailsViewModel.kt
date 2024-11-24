package com.redron.ui.single_joke

import androidx.lifecycle.ViewModel
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JokeDetailsViewModel(private val generator: JokesGenerator) : ViewModel() {
    private val _joke = MutableStateFlow<Joke?>(null)
    val joke: StateFlow<Joke?> = _joke

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadSingleJoke(jokeId: String) {
        (generator.getJoke(jokeId)).let {
            _joke.value = it
        }
    }
}