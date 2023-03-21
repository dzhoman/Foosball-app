package com.example.foosball.data.datasource

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataStore @Inject constructor() : DataStore {
    override fun get(): Observable<List<GameResultEntity>> {
        return Observable.fromArray(games)
    }

    override fun add(game: GameResultEntity): Single<List<GameResultEntity>> {
        games.add(0, game)
        return Single.just(games)
    }

    private val games = mutableListOf(
        GameResultEntity("1", "Amos", "Diego", 4, 5),
        GameResultEntity("2", "Amos", "Diego", 1, 5),
        GameResultEntity("3", "Amos", "Diego", 2, 5),
        GameResultEntity("4", "Amos", "Diego", 0, 5),
        GameResultEntity("5", "Amos", "Diego", 6, 5),
        GameResultEntity("6", "Amos", "Diego", 5, 2),
        GameResultEntity("7", "Amos", "Diego", 4, 0),
        GameResultEntity("8", "Joel", "Diego", 4, 5),
        GameResultEntity("9", "Tim", "Amos", 6, 5),
        GameResultEntity("10", "Tim", "Amos", 5, 2),
        GameResultEntity("11", "Amos", "Tim", 3, 5),
        GameResultEntity("12", "Amos", "Tim", 5, 5),
        GameResultEntity("13", "Amos", "Joel", 5, 4),
        GameResultEntity("14", "Joel", "Tim", 5, 2),
    )
}