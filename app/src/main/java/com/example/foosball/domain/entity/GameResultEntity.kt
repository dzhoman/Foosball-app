package com.example.foosball.domain.entity

data class GameResultEntity(
    val id: String,
    val firstPerson: String,
    val secondPerson: String,
    val firstScore: Int,
    val secondScore: Int
)
