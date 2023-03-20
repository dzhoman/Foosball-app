package com.example.foosball.presentation.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.entity.LeaderboardItemEntity
import com.example.foosball.domain.leaderboard.AddGameUseCase
import com.example.foosball.domain.leaderboard.GetGamesUseCase
import com.example.foosball.utils.SingleLiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LeaderboardViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val addGameUseCase: AddGameUseCase
) : ViewModel() {

    enum class SortBy {
        GAMES_WON, GAMES_COUNT
    }

    private val sortTrigger = PublishSubject.create<List<GameResultEntity>>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _gamesLiveData = MutableLiveData<List<GameResultEntity>>()
    val gamesLiveData: LiveData<List<GameResultEntity>> = _gamesLiveData

    private val _gameAddedLiveData = SingleLiveEvent<Boolean>()
    val gameAddedLiveData: SingleLiveEvent<Boolean> = _gameAddedLiveData

    private val _leaderboardLiveData = MutableLiveData<List<LeaderboardItemEntity>>()
    val leaderboardLiveData: LiveData<List<LeaderboardItemEntity>> = _leaderboardLiveData

    private val _errorEvent = SingleLiveEvent<String>()
    val errorEvent: SingleLiveEvent<String> = _errorEvent


    private val _sortTypeLiveData = MutableLiveData(SortBy.GAMES_WON)

    init {
        compositeDisposable.add(
            sortTrigger
                .map { convertToLeaderboards(it) }
                .map { sort(it) }
                .subscribe({ games ->
                    _leaderboardLiveData.postValue(games)
                }, { error ->
                    errorEvent.postValue(error.localizedMessage ?: "")
                })
        )
    }

    fun getGames() {
        compositeDisposable.add(
            getGamesUseCase.invoke()
                .doOnSuccess {
                    _gamesLiveData.postValue(it)
                }
                .subscribe({ games ->
                    sortTrigger.onNext(games)
                }, { error ->
                    errorEvent.postValue(error.localizedMessage ?: "")
                })
        )
    }

    fun addGame(firstPlayer: String, secondPlayer: String, firstScore: Int, secondScore: Int) {
        compositeDisposable.add(addGameUseCase.invoke(
            GameResultEntity(
                firstPlayer,
                secondPlayer,
                firstScore,
                secondScore
            )
        )
            .doOnSuccess {
                _gamesLiveData.postValue(it)
                _gameAddedLiveData.postValue(true)
            }
            .subscribe({ games ->
                sortTrigger.onNext(games)
            }, { error ->
                _errorEvent.postValue(error.localizedMessage ?: "")
            })
        )
    }

    private fun sort(items: List<LeaderboardItemEntity>): List<LeaderboardItemEntity> {
        return when (_sortTypeLiveData.value) {
            SortBy.GAMES_COUNT -> {
                items.sortedByDescending { it.gamesCount }
            }
            SortBy.GAMES_WON -> {
                items.sortedByDescending { it.gamesWon }
            }
            else -> items
        }
    }

    private fun convertToLeaderboards(games: List<GameResultEntity>): List<LeaderboardItemEntity> {
        val result = mutableMapOf<String, LeaderboardItemEntity>()
        for (game in games) {
            val firstItem =
                result[game.firstPerson] ?: LeaderboardItemEntity(game.firstPerson, 0, 0)
            val secondItem =
                result[game.secondPerson] ?: LeaderboardItemEntity(game.secondPerson, 0, 0)
            if (game.firstScore > game.secondScore) {
                result[game.firstPerson] = firstItem.copy(
                    gamesCount = firstItem.gamesCount.inc(),
                    gamesWon = firstItem.gamesWon.inc()
                )
                result[game.secondPerson] =
                    secondItem.copy(gamesCount = secondItem.gamesCount.inc())
            }
            if (game.firstScore < game.secondScore) {
                result[game.secondPerson] = secondItem.copy(
                    gamesCount = secondItem.gamesCount.inc(),
                    gamesWon = secondItem.gamesWon.inc()
                )
                result[game.firstPerson] =
                    firstItem.copy(gamesCount = firstItem.gamesCount.inc())
            }
        }
        return result.values.toList()
    }

    fun sortChanged(sortType: SortBy) {
        _sortTypeLiveData.value = sortType
        sortTrigger.onNext(_gamesLiveData.value ?: emptyList())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}