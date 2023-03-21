package com.example.foosball.domain.entity

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameResultEntityFactory @Inject constructor() {
    fun createSingleGameResult(
        firstPlayer: String,
        secondPlayer: String,
        firstScore: Int,
        secondScore: Int
    ): GameResultEntity {
        return GameResultEntity(
            UUID.randomUUID().toString(),
            firstPlayer,
            secondPlayer,
            firstScore,
            secondScore
        )
    }
}