package com.example.foosball.domain.leaderboard

import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetGamesUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler
) : GetGamesUseCase {
    override fun invoke(): Observable<List<GameResultEntity>> {
        return gamesRepository.getGames()
            .subscribeOn(executionScheduler.provide())
            .observeOn(postExecutionScheduler.provide())

    }
}