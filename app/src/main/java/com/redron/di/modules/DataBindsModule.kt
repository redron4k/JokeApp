package com.redron.di.modules

import com.redron.data.datasource.local.CacheJokesDataSource
import com.redron.data.datasource.local.CacheJokesDataSourceImpl
import com.redron.data.datasource.local.LocalJokesDataSource
import com.redron.data.datasource.local.LocalJokesDataSourceImpl
import com.redron.data.datasource.remote.RemoteJokesDataSource
import com.redron.data.datasource.remote.RemoteJokesDataSourceImpl
import com.redron.data.repository.JokesRepositoryImpl
import com.redron.domain.repository.JokesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataBindsModule {
    @Binds
    fun bindRemoteDataSource(remoteJokesDataSourceImpl: RemoteJokesDataSourceImpl):
            RemoteJokesDataSource

    @Binds
    fun bindLocalDataSource(localJokesDataSourceImpl: LocalJokesDataSourceImpl):
            LocalJokesDataSource

    @Binds
    fun bindCacheDataSource(cacheJokesDataSourceImpl: CacheJokesDataSourceImpl):
            CacheJokesDataSource

    @Binds
    fun bindRepository(repositoryImpl: JokesRepositoryImpl): JokesRepository
}