package com.example.foosball.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.foosball.App
import com.example.foosball.R
import com.example.foosball.presentation.leaderboard.GamesSharedViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: GamesSharedViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        viewModel.getGames()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}