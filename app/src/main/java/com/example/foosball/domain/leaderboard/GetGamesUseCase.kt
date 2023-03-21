package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Observable

interface GetGamesUseCase {
    fun invoke(): Observable<List<GameResultEntity>>
}