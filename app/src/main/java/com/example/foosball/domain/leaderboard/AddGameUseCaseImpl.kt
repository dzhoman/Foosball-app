package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddGameUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler
) : AddGameUseCase {
    override fun invoke(
        gameResult: GameResultEntity
    ): Single<List<GameResultEntity>> {
        return gamesRepository.addGame(gameResult)
            .subscribeOn(executionScheduler.provide())
            .observeOn(postExecutionScheduler.provide())
    }
}