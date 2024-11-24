package com.redron.data

import java.util.UUID

data class Joke(
    val jokeQuestion: String,
    val jokeAnswer: String,
    val category: String,
    val id: String = UUID.randomUUID().toString()
)
