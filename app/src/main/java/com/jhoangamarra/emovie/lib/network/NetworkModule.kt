package com.jhoangamarra.emovie.lib.network

import com.jhoangamarra.emovie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @IntoSet
    @Provides
    fun provideLoggingInterceptor(): Interceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    } else {
        Interceptor { chain -> chain.proceed(chain.request()) }
    }

    @IntoSet
    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder().addQueryParameter("api_key" , API_KEY).build()
            chain.proceed(
                request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
            )
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        return builder.build()
    }

}

private const val READ_TIME_OUT = 30L
private const val CONNECTION_TIMEOUT = 30L
private const val API_KEY = "7f0fdc8184b99c336d79b988f66ad774"