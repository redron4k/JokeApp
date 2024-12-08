package com.redron.data.datasource.remote

import com.redron.domain.entity.Joke

interface RemoteJokesDataSource {
    suspend fun getJokes(): List<Joke>
}