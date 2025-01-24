package com.redron.data.datasource.remote

import com.redron.data.entity.JokeEntityRemote
import com.redron.domain.entity.Joke
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeResponseBody(
    @SerialName("jokes")
    val jokes: List<JokeEntityRemote>
)
