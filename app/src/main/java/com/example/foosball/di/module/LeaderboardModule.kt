package com.example.foosball.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foosball.data.datasource.DataStore
import com.example.foosball.data.datasource.LocalDataStore
import com.example.foosball.data.repository.GamesRepositoryImpl
import com.example.foosball.data.scheduler.BackgroundScheduler
import com.example.foosball.di.ViewModelFactory
import com.example.foosball.di.ViewModelKey
import com.example.foosball.domain.leaderboard.AddGameUseCase
import com.example.foosball.domain.leaderboard.AddGameUseCaseImpl
import com.example.foosball.domain.leaderboard.GetGamesUseCase
import com.example.foosball.domain.leaderboard.GetGamesUseCaseImpl
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import com.example.foosball.presentation.leaderboard.GamesSharedViewModel
import com.example.foosball.presentation.scheduler.UiScheduler
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class LeaderboardModule {

    //switch data store to remote if need
    @Binds
    abstract fun provideLocalDataStore(dataStore: LocalDataStore): DataStore

    @Binds
    abstract fun provideAddGameUseCase(useCase: AddGameUseCaseImpl): AddGameUseCase

    @Binds
    abstract fun provideGetGamesUseCase(useCase: GetGamesUseCaseImpl): GetGamesUseCase

    @Binds
    @Singleton
    abstract fun provideGamesRepository(gamesRepository: GamesRepositoryImpl): GamesRepository

    @Binds
    @Singleton
    abstract fun providerThreadExecutor(scheduler: BackgroundScheduler): ExecutionScheduler

    @Binds
    @Singleton
    abstract fun providePostExecutionScheduler(scheduler: UiScheduler): PostExecutionScheduler

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesSharedViewModel::class)
    abstract fun bindLeaderboardViewModel(viewModel: GamesSharedViewModel): ViewModel
}