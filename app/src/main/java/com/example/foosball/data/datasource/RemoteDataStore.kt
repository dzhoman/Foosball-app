package com.example.foosball.data.datasource

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Сlass responsible for creating the remote date store. For example from network, etc...
 */
class RemoteDataStore : DataStore {
    override fun get(): Observable<List<GameResultEntity>> {
        TODO("Not yet implemented")
    }

    override fun add(game: GameResultEntity): Single<List<GameResultEntity>> {
        TODO("Not yet implemented")
    }
}