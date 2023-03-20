package com.example.foosball

import android.app.Application
import com.example.foosball.di.component.ApplicationComponent
import com.example.foosball.di.component.DaggerApplicationComponent

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .appContext(this)
            .build()
    }
}