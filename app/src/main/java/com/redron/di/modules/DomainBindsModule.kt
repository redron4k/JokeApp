package com.redron.di.modules

import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.GetJokeUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.RefreshCacheUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import com.redron.domain.usecases.impl.AddJokeUseCaseImpl
import com.redron.domain.usecases.impl.AddJokesUseCaseImpl
import com.redron.domain.usecases.impl.AddToFavoritesUseCaseImpl
import com.redron.domain.usecases.impl.ClearLoadedJokesUseCaseImpl
import com.redron.domain.usecases.impl.GetJokeUseCaseImpl
import com.redron.domain.usecases.impl.LoadJokesFromNetUseCaseImpl
import com.redron.domain.usecases.impl.LoadJokesLocalUseCaseImpl
import com.redron.domain.usecases.impl.RefreshCacheUseCaseImpl
import com.redron.domain.usecases.impl.RemoveFromFavoritesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainBindsModule {
    @Binds
    fun bindAddJokesUseCase(addJokesUseCaseImpl: AddJokesUseCaseImpl): AddJokesUseCase

    @Binds
    fun bindAddJokeUseCase(addJokeUseCaseImpl: AddJokeUseCaseImpl): AddJokeUseCase

    @Binds
    fun bindAddToFavoritesUseCase(addToFavoritesUseCaseImpl: AddToFavoritesUseCaseImpl):
            AddToFavoritesUseCase

    @Binds
    fun bindClearLoadedJokesUseCase(clearLoadedJokesUseCaseImpl: ClearLoadedJokesUseCaseImpl):
            ClearLoadedJokesUseCase

    @Binds
    fun bindGetJokeUseCase(getJokeUseCaseImpl: GetJokeUseCaseImpl): GetJokeUseCase

    @Binds
    fun bindLoadJokesFromNetUseCase(loadJokesFromNetUseCaseImpl: LoadJokesFromNetUseCaseImpl):
            LoadJokesFromNetUseCase

    @Binds
    fun bindLoadJokesLocalUseCase(loadJokesLocalUseCaseImpl: LoadJokesLocalUseCaseImpl):
            LoadJokesLocalUseCase

    @Binds
    fun bindRefreshCacheUseCase(refreshCacheUseCaseImpl: RefreshCacheUseCaseImpl):
            RefreshCacheUseCase

    @Binds
    fun bindRemoveFromFavoritesUseCase(
        removeFromFavoritesUseCaseImpl: RemoveFromFavoritesUseCaseImpl
    ): RemoveFromFavoritesUseCase
}