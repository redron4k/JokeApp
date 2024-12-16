package com.redron.data.datasource.remote

import com.redron.domain.entity.Joke
import javax.inject.Inject

class RemoteJokesDataSourceImpl @Inject constructor(private val api: ApiService):
    RemoteJokesDataSource {

    override suspend fun getJokes(): List<Joke> {
        return api.getJokes().jokes
    }
}