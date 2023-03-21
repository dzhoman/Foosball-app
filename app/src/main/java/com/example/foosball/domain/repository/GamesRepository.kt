package com.example.foosball.domain.repository

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface GamesRepository {
    fun getGames(): Observable<List<GameResultEntity>>
    fun addGame(gameResult: GameResultEntity): Single<List<GameResultEntity>>
}