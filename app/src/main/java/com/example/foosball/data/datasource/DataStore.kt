package com.example.foosball.data.datasource

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Single

interface DataStore {
    fun get(): Single<List<GameResultEntity>>
    fun add(game: GameResultEntity): Single<List<GameResultEntity>>
}