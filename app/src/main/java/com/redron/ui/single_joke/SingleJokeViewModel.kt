package com.redron.ui.single_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redron.data.Joke
import com.redron.data.JokesGenerator

class SingleJokeViewModel(private val generator: JokesGenerator) : ViewModel() {
    private val _jokes = MutableLiveData<Joke>()
    val jokes: LiveData<Joke> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadSingleJoke(jokeId: Int) {
        if (jokeId == -1) {
            _error.value = "Unexpeted id error"
        } else {
            (generator.getJoke(jokeId)).let {
                _jokes.value = it
            }
        }
    }
}