package com.redron.di

//import android.content.Context
//import com.redron.di.modules.DataBindsModule
//import com.redron.di.modules.DataModule
//import com.redron.di.modules.DomainModule
//import com.redron.di.modules.PresentationModule
//import com.redron.presentation.add_joke.AddJokeFragment
//import com.redron.presentation.main.ListFragment
//import com.redron.presentation.single_joke.JokeDetailsFragment
//import dagger.BindsInstance
//import dagger.Component
//import javax.inject.Singleton
//
//@Singleton
//@Component(
//    modules = [
//        DataBindsModule::class,
//        DataModule::class,
//        DomainModule::class,
//        PresentationModule::class
//    ]
//)
//interface AppComponent {
//    @Component.Factory
//    interface AppComponentFactory {
//        fun create(@BindsInstance context: Context): AppComponent
//    }
//
//    fun inject(fragment: ListFragment)
//    fun inject(fragment: AddJokeFragment)
//    fun inject(fragment: JokeDetailsFragment)
//}