package com.example.foosball.data.datasource

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface DataStore {
    fun get(): Observable<List<GameResultEntity>>
    fun add(game: GameResultEntity): Single<List<GameResultEntity>>
}