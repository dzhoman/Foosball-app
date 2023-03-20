package com.example.foosball.di.module

import com.example.foosball.utils.AndroidResourceProvider
import com.example.foosball.utils.ResourceProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindResourceProvider(provider: AndroidResourceProvider): ResourceProvider
}