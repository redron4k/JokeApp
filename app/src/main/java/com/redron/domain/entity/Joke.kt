package com.redron.domain.entity

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
    val isFromNet: Boolean = true
)
