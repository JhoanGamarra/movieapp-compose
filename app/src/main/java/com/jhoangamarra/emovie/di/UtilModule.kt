package com.jhoangamarra.emovie.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Reusable
    @Provides
    fun provideGson(): Gson = Gson()
}