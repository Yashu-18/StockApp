package com.example.stock.di

import android.content.SharedPreferences
import com.example.stock.data.local.StockDatabase
import com.example.stock.data.remote.AlphaVantageAPI
import com.example.stock.data.repository.StocksRepository
import com.example.stock.data.repository.StocksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesStocksRepository(
        remote: AlphaVantageAPI, db: StockDatabase
    ): StocksRepository = StocksRepositoryImpl(
        alphaVantageAPI = remote, stockDatabase = db
    )
}