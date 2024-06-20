package com.example.gitprojectapp.di

import android.content.Context
import com.example.gitprojectapp.data.api.ApiService
import com.example.gitprojectapp.data.mapper.FileMapper
import com.example.gitprojectapp.data.mapper.ReadmeMapper
import com.example.gitprojectapp.data.mapper.RepositoryMapper
import com.example.gitprojectapp.data.mapper.UserMapper
import com.example.gitprojectapp.data.repository.ApiRepositoryImpl
import com.example.gitprojectapp.data.repository.SharedPreferencesImpl
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
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

    @Provides
    fun provideApiRepository(
        apiService: ApiService,
        userMapper: UserMapper,
        repositoryMapper: RepositoryMapper,
        readmeMapper: ReadmeMapper,
        fileMapper: FileMapper
    ): RepositoryApi {
        return ApiRepositoryImpl(
            apiService = apiService,
            userMapper = userMapper,
            repositoryMapper = repositoryMapper,
            readmeMapper = readmeMapper,
            fileMapper = fileMapper
        )
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

    @Provides
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }

    @Provides
    fun provideReadmeMapper(): ReadmeMapper {
        return ReadmeMapper()
    }

    @Provides
    fun provideFileMapper(): FileMapper {
        return FileMapper()
    }
}