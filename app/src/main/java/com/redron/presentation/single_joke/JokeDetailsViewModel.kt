package com.redron.presentation.single_joke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redron.domain.entity.Joke
import com.redron.domain.usecases.GetJokeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeDetailsViewModel @Inject constructor(
    private val getJoke: GetJokeUseCase
) : ViewModel() {
    private val _joke = MutableStateFlow<Joke?>(null)
    val joke: StateFlow<Joke?> = _joke.asStateFlow()

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error

    fun loadSingleJoke(jokeId: String) {
        viewModelScope.launch {
            (getJoke(jokeId)).let {
                _joke.value = it
            }
        }
    }
}