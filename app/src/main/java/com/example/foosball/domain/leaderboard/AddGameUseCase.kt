package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.presentation.model.GameResult
import io.reactivex.rxjava3.core.Single

interface AddGameUseCase {
    fun invoke(gameResult: GameResult): Single<List<GameResultEntity>>
}