package com.example.stock.di

import android.content.Context
import com.example.stock.BuildConfig
import com.example.stock.data.remote.AlphaVantageAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    private const val CACHE_SIZE = 10 * 1024 * 1024
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        val cacheDir = File(context.cacheDir, "http-cache")
        val cache = Cache(cacheDir, CACHE_SIZE.toLong())

        val cacheInterceptor = Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.HOURS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(cacheInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.STOCK_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): AlphaVantageAPI {
        return retrofit.create(AlphaVantageAPI::class.java)
    }

}