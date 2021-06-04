package com.elixer.paws.di

import com.elixer.paws.RetrofitService
import com.elixer.paws.interacters.GetDogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetDogs(retrofitService: RetrofitService) = GetDogs(retrofitService)

}











