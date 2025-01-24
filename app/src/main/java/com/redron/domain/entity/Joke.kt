package com.redron.domain.entity

import java.util.UUID
import kotlinx.serialization.*

data class Joke(
    val jokeQuestion: String,
    val jokeAnswer: String,
    val category: String,
    val uuid: String = UUID.randomUUID().toString(),
    val isFromNet: Boolean = true,
    val isFavorite: Boolean = false,
)
