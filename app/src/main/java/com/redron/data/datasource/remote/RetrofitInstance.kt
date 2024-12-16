package com.redron.data.datasource.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://v2.jokeapi.dev/"

    val retrofitClient: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(provideSerializationFactory())
            .build()
            .create(ApiService::class.java)
    }

    private fun provideSerializationFactory(): Converter.Factory {
        val contentType: MediaType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }
}