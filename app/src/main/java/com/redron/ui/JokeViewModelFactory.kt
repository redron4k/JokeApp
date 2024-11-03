package com.redron.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redron.data.JokesGenerator
import com.redron.ui.main.JokesListViewModel
import com.redron.ui.single_joke.SingleJokeViewModel

class JokeViewModelFactory(private val generator: JokesGenerator) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokesListViewModel::class.java) -> {
                JokesListViewModel(JokesGenerator) as T
            }

            modelClass.isAssignableFrom(SingleJokeViewModel::class.java) -> {
                SingleJokeViewModel(JokesGenerator) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}