package com.example.foosball.presentation.leaderboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foosball.data.scheduler.BackgroundScheduler
import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.entity.LeaderboardItemEntity
import com.example.foosball.domain.leaderboard.AddGameUseCase
import com.example.foosball.domain.leaderboard.AddGameUseCaseImpl
import com.example.foosball.domain.leaderboard.GetGamesUseCase
import com.example.foosball.domain.leaderboard.GetGamesUseCaseImpl
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import com.example.foosball.presentation.scheduler.UiScheduler
import com.example.foosball.presentation.utils.LiveDataTestUtil
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class LeaderboardViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getGameUseCase: GetGamesUseCase
    private lateinit var addGameUseCase: AddGameUseCase
    private lateinit var gamesRepository: GamesRepository
    private lateinit var viewModel: LeaderboardViewModel
    private lateinit var bgScheduler: ExecutionScheduler
    private lateinit var uiScheduler: PostExecutionScheduler

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        bgScheduler = BackgroundScheduler()
        uiScheduler = UiScheduler()
        gamesRepository = mock(GamesRepository::class.java)
        getGameUseCase = GetGamesUseCaseImpl(gamesRepository, bgScheduler, uiScheduler)
        addGameUseCase = AddGameUseCaseImpl(gamesRepository, bgScheduler, uiScheduler)
        viewModel = LeaderboardViewModel(getGameUseCase, addGameUseCase)
    }

    @Test
    fun `Get games works as expected`() {
        val gameResults = listOf(GameResultEntity("Amos", "Diego", 4, 5))
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Single.just(gameResults))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.gamesLiveData)[0], gameResults.first()
        )
    }

    @Test
    fun `Add game works as expected`() {
        val gameResult = GameResultEntity("Amos", "Diego", 4, 5)
        Mockito.`when`(gamesRepository.addGame(gameResult))
            .thenReturn(Single.just(listOf(gameResult)))

        viewModel.addGame("Amos", "Diego", 4, 5)

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.gamesLiveData)[0], gameResult
        )
    }

    @Test
    fun `Leaderboard updates after get games flow`() {
        val gameResults = listOf(
            GameResultEntity("Amos", "Diego", 4, 5),
            GameResultEntity("Amos", "Diego", 1, 5),
        )
        val expectedLeaderboard = listOf(
            LeaderboardItemEntity("Diego", 2, 2), LeaderboardItemEntity("Amos", 2, 0)
        )
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Single.just(gameResults))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.leaderboardLiveData), expectedLeaderboard
        )
    }

    @Test
    fun `Leaderboard updates after add game flow`() {
        val gameResults = mutableListOf(
            GameResultEntity("Amos", "Diego", 4, 5),
            GameResultEntity("Amos", "Diego", 1, 5),
        )
        val gameResult = GameResultEntity("Amos", "Diego", 6, 5)
        val expectedLeaderboard = listOf(
            LeaderboardItemEntity("Diego", 2, 2), LeaderboardItemEntity("Amos", 2, 0)
        )
        val expectedUpdatedLeaderboard = listOf(
            LeaderboardItemEntity("Diego", 3, 2), LeaderboardItemEntity("Amos", 3, 1)
        )
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Single.just(gameResults))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.leaderboardLiveData), expectedLeaderboard
        )

        //now add game flow, leaderboards should be updated
        gameResults.add(gameResult)
        Mockito.`when`(gamesRepository.addGame(gameResult)).thenReturn(Single.just(gameResults))
        viewModel.addGame("Amos", "Diego", 6, 5)

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.leaderboardLiveData), expectedUpdatedLeaderboard
        )
    }

    @Test
    fun `Add game emit error`() {
        val errorMsg = "smth went wrong"
        val gameResult = GameResultEntity("Amos", "Diego", 4, 5)
        Mockito.`when`(gamesRepository.addGame(gameResult))
            .thenReturn(Single.error(Throwable(errorMsg)))

        viewModel.addGame("Amos", "Diego", 4, 5)

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.errorEvent), errorMsg
        )
    }

    @Test
    fun `Get games emit error`() {
        val errorMsg = "smth went wrong"
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Single.error(Throwable(errorMsg)))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.errorEvent), errorMsg
        )
    }
}