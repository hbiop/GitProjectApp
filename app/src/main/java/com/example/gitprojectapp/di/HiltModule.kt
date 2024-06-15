package com.example.gitprojectapp.di

import android.content.Context
import com.example.gitprojectapp.data.api.ApiService
import com.example.gitprojectapp.data.mapper.UserMapper
import com.example.gitprojectapp.data.repository.ApiRepositoryImpl
import com.example.gitprojectapp.data.repository.SharedPreferencesImpl
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideApiRepository(apiService: ApiService): RepositoryApi {
        return ApiRepositoryImpl(apiService)
    }
    @Singleton
    @Provides
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
    @Singleton
    @Provides
    fun provideSharedPrefsRepository(context: Context): SharedPreferenceRepository {
        return SharedPreferencesImpl(context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}