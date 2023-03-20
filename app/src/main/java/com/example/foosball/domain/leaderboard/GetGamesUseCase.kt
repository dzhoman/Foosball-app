package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Single

interface GetGamesUseCase {
    fun invoke(): Single<List<GameResultEntity>>
}