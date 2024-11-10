package com.redron.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redron.data.JokesGenerator
import com.redron.ui.main.JokesListViewModel
import com.redron.ui.single_joke.JokeDetailsViewModel

class JokeViewModelFactory(private val generator: JokesGenerator) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokesListViewModel::class.java) -> {
                JokesListViewModel(JokesGenerator) as T
            }

            modelClass.isAssignableFrom(JokeDetailsViewModel::class.java) -> {
                JokeDetailsViewModel(JokesGenerator) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}