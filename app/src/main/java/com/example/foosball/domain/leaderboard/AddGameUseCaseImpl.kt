package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.entity.GameResultEntityFactory
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import com.example.foosball.presentation.model.GameResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddGameUseCaseImpl @Inject constructor(

    private val gamesRepository: GamesRepository,
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler,
    private val gameResultFactory: GameResultEntityFactory
) : AddGameUseCase {
    override fun invoke(
        gameResult: GameResult
    ): Single<List<GameResultEntity>> {
        return gamesRepository.addGame(
            gameResultFactory.createSingleGameResult(
                gameResult.firstPerson,
                gameResult.secondPerson,
                gameResult.firstScore,
                gameResult.secondScore
            )
        )
            .subscribeOn(executionScheduler.provide())
            .observeOn(postExecutionScheduler.provide())
    }
}