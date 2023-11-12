package com.example.apnamart.di

import android.app.Application
import androidx.room.Room
import com.example.apnamart.data.network.Api
import com.example.apnamart.data.repositories.AppRepositoryImpl
import com.example.apnamart.data.room.dao.AppDao
import com.example.apnamart.data.room.database.AppDatabase
import com.example.apnamart.data.usecases.FetchReposUseCaseImpl
import com.example.apnamart.domain.repostiory.AppRepository
import com.example.apnamart.domain.usecases.FetchReposeUseCase
import com.example.apnamart.ui.helper.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideApi(): Api {
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .build().create(Api::class.java)
    }

    @OptIn(InternalCoroutinesApi::class)
    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): AppDatabase {
        var INSTANCE: AppDatabase? = null

        return INSTANCE ?: synchronized(this) {

            val instance: AppDatabase = Room.databaseBuilder<AppDatabase>(
                application.applicationContext,
                AppDatabase::class.java,
                "repo_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
            INSTANCE = instance


            return instance
        }
    }

    @Singleton
    @Provides
    fun providesAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.getAppDao()
    }


    @Singleton
    @Provides
    fun provideAppRepo(api: Api): AppRepository {
        return AppRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideFetchRepoUseCase(appRepository: AppRepository, appDao: AppDao): FetchReposeUseCase {
        return FetchReposUseCaseImpl(appRepository, appDao)
    }

}