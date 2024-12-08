package com.redron.presentation.single_joke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.domain.entity.Joke
import com.redron.domain.usecases.GetJokeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JokeDetailsViewModel(
    private val getJoke: GetJokeUseCase
) : ViewModel() {
    private val _joke = MutableStateFlow<Joke?>(null)
    val joke: StateFlow<Joke?> = _joke

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadSingleJoke(jokeId: String) {
        viewModelScope.launch {
            (getJoke(jokeId)).let {
                _joke.value = it
            }
        }
    }
}