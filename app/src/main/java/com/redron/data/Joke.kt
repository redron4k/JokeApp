package com.redron.data

import java.util.UUID
import kotlinx.serialization.*

@Serializable
data class Joke(
    @SerialName("setup")
    val jokeQuestion: String,
    @SerialName("delivery")
    val jokeAnswer: String,
    @SerialName("category")
    val category: String,
    val uuid: String = UUID.randomUUID().toString(),
    val fromNet: Boolean = true
)

@Serializable
data class JokeResponseBody(
    @SerialName("jokes")
    val jokes: List<Joke>
)
