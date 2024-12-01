package com.redron.data

object JokesGenerator {
    private val _jokesList = mutableListOf<Joke>()

    fun getAllJokes(): List<Joke> {
        return _jokesList.toList()
    }

    fun getJoke(id: String): Joke? {
        return _jokesList.find { it.uuid == id }
    }

    fun addJoke(joke: Joke) {
        _jokesList.add(0, joke)
    }

    fun addJokes(jokes: List<Joke>) {
        _jokesList.addAll(jokes)
    }
}