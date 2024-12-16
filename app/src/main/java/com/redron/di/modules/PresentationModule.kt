package com.redron.di.modules

import androidx.lifecycle.ViewModel
import com.redron.di.ViewModelKey
import com.redron.presentation.main.JokesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(JokesListViewModel::class)
    fun bindJokesListViewModel(viewModel: JokesListViewModel): ViewModel
}