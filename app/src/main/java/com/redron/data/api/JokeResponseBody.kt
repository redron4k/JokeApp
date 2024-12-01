package com.redron.data.api

import com.redron.data.Joke
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeResponseBody(
    @SerialName("jokes")
    val jokes: List<Joke>
)
