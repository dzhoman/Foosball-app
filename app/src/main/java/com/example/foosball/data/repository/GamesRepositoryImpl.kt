package com.example.foosball.data.repository

import com.example.foosball.data.datasource.DataStore
import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.repository.GamesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore
) : GamesRepository {

    override fun getGames(): Single<List<GameResultEntity>> {
        return dataStore.get()
    }

    override fun addGame(gameResult: GameResultEntity): Single<List<GameResultEntity>> {
        return dataStore.add(gameResult)
    }
}