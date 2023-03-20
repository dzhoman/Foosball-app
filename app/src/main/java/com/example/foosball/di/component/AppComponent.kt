package com.example.foosball.di.component

import android.content.Context
import com.example.foosball.di.module.AppModule
import com.example.foosball.di.module.LeaderboardModule
import com.example.foosball.presentation.MainActivity
import com.example.foosball.presentation.leaderboard.AddGameFragment
import com.example.foosball.presentation.leaderboard.GamesListFragment
import com.example.foosball.presentation.leaderboard.LeaderboardFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, LeaderboardModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): ApplicationComponent
    }

    fun inject(fragment: LeaderboardFragment)
    fun inject(fragment: GamesListFragment)
    fun inject(fragment: AddGameFragment)
    fun inject(activity: MainActivity)
}