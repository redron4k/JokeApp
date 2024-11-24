package com.redron.data.api

import com.redron.data.JokeResponseBody
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.http.GET
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory


interface ApiService {
    @GET(
        "joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&" +
                "type=twopart&amount=10"
    )
    suspend fun getJokes(): JokeResponseBody
}

fun provideSerializationFactory(): Converter.Factory {
    val contentType: MediaType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    return json.asConverterFactory(contentType)
}

fun createRetrofit(): ApiService {
    val retrofit = Retrofit.Builder().baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(provideSerializationFactory()).build()

    return retrofit.create(ApiService::class.java)
}