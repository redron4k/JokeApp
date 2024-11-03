package com.redron.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redron.data.Joke
import com.redron.data.JokesGenerator

class JokesListViewModel(private val generator: JokesGenerator) : ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun generateJokes() {
        _jokes.value = generator.generate()
    }

    fun showGeneratedJokes() {
        _jokes.value = generator.jokes
    }
}