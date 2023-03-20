package com.example.foosball.data.datasource

import com.example.foosball.domain.entity.GameResultEntity
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataStore @Inject constructor() : DataStore {
    override fun get(): Single<List<GameResultEntity>> {
        return Single.just(games)
    }

    override fun add(game: GameResultEntity): Single<List<GameResultEntity>> {
        games.add(0, game)
        return get()
    }

    private val games = mutableListOf(
        GameResultEntity("Amos", "Diego", 4, 5),
        GameResultEntity("Amos", "Diego", 1, 5),
        GameResultEntity("Amos", "Diego", 2, 5),
        GameResultEntity("Amos", "Diego", 0, 5),
        GameResultEntity("Amos", "Diego", 6, 5),
        GameResultEntity("Amos", "Diego", 5, 2),
        GameResultEntity("Amos", "Diego", 4, 0),
        GameResultEntity("Joel", "Diego", 4, 5),
        GameResultEntity("Tim", "Amos", 6, 5),
        GameResultEntity("Tim", "Amos", 5, 2),
        GameResultEntity("Amos", "Tim", 3, 5),
        GameResultEntity("Amos", "Tim", 5, 5),
        GameResultEntity("Amos", "Joel", 5, 4),
        GameResultEntity("Joel", "Tim", 5, 2),
    )
}