package com.redron.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class JokeEntityRemote(
    @SerialName("setup")
    val setup: String,
    @SerialName("delivery")
    val delivery: String,
    @SerialName("category")
    val category: String,
    val uuid: String = UUID.randomUUID().toString(),
    val isFromNet: Boolean = true,
    val isFavorite: Boolean = false,
)