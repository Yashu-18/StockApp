package com.example.stock.di

import android.content.Context
import androidx.room.Room
import com.example.stock.data.local.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext applicationContext: Context): StockDatabase {
        val databaseBuilder = Room.databaseBuilder(
            applicationContext,
            StockDatabase::class.java,
            StockDatabase.NAME
        )

        return databaseBuilder.build()
    }

}