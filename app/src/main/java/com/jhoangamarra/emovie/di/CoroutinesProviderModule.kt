package com.jhoangamarra.emovie.di

import com.jhoangamarra.emovie.lib.definition.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesProviderModule {

    @Singleton
    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider =
        object : CoroutineContextProvider {
            override val io: CoroutineContext
                get() = Dispatchers.IO
            override val computation: CoroutineContext
                get() = Dispatchers.Default
            override val main: CoroutineContext
                get() = Dispatchers.Main
        }
}
