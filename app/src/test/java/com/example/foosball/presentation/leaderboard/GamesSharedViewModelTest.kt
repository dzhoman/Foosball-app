package com.example.foosball.presentation.leaderboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foosball.data.scheduler.BackgroundScheduler
import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.domain.entity.GameResultEntityFactory
import com.example.foosball.domain.entity.LeaderboardItemEntity
import com.example.foosball.domain.leaderboard.AddGameUseCase
import com.example.foosball.domain.leaderboard.AddGameUseCaseImpl
import com.example.foosball.domain.leaderboard.GetGamesUseCase
import com.example.foosball.domain.leaderboard.GetGamesUseCaseImpl
import com.example.foosball.domain.repository.GamesRepository
import com.example.foosball.domain.scheduler.ExecutionScheduler
import com.example.foosball.domain.scheduler.PostExecutionScheduler
import com.example.foosball.presentation.model.GameResult
import com.example.foosball.presentation.scheduler.UiScheduler
import com.example.foosball.presentation.utils.LiveDataTestUtil
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*


class GamesSharedViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getGameUseCase: GetGamesUseCase
    private lateinit var addGameUseCase: AddGameUseCase
    private lateinit var gamesRepository: GamesRepository
    private lateinit var viewModel: GamesSharedViewModel
    private lateinit var bgScheduler: ExecutionScheduler
    private lateinit var uiScheduler: PostExecutionScheduler
    private lateinit var gameResultFactory: GameResultEntityFactory

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        bgScheduler = BackgroundScheduler()
        uiScheduler = UiScheduler()
        gameResultFactory = mock(GameResultEntityFactory::class.java)
        gamesRepository = mock(GamesRepository::class.java)
        getGameUseCase = GetGamesUseCaseImpl(gamesRepository, bgScheduler, uiScheduler)
        addGameUseCase =
            AddGameUseCaseImpl(gamesRepository, bgScheduler, uiScheduler, gameResultFactory)
        viewModel = GamesSharedViewModel(getGameUseCase, addGameUseCase)
    }

    @Test
    fun `Get games works as expected`() {
        val gameResults = listOf(GameResultEntity("1", "Amos", "Diego", 4, 5))
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Observable.just(gameResults))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.gamesLiveData)[0], gameResults.first()
        )
    }

    @Test
    fun `Add game works as expected`() {
        val gameResult = GameResult("Amos", "Diego", 4, 5)
        val expectedResult = GameResultEntity(UUID.randomUUID().toString(), "Amos", "Diego", 4, 5)
        Mockito.`when`(
            gameResultFactory.createSingleGameResult(
                gameResult.firstPerson,
                gameResult.secondPerson,
                gameResult.firstScore,
                gameResult.secondScore
            )
        ).thenReturn(expectedResult)

        Mockito.`when`(gamesRepository.addGame(expectedResult))
            .thenReturn(Single.just(listOf(expectedResult)))

        viewModel.addGame(
            gameResult.firstPerson,
            gameResult.secondPerson,
            gameResult.firstScore,
            gameResult.secondScore
        )

        assertEquals(LiveDataTestUtil.getValue(viewModel.gamesLiveData)[0], expectedResult)
    }

    @Test
    fun `Leaderboard updates after get games flow`() {
        val gameResults = listOf(
            GameResultEntity("1", "Amos", "Diego", 4, 5),
            GameResultEntity("2", "Amos", "Diego", 1, 5),
        )
        val expectedLeaderboard = listOf(
            LeaderboardItemEntity("Diego", 2, 2), LeaderboardItemEntity("Amos", 2, 0)
        )
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Observable.just(gameResults))

        viewModel.getGames()

        assertEquals(LiveDataTestUtil.getValue(viewModel.leaderboardLiveData), expectedLeaderboard)
    }

    @Test
    fun `Add game emit error`() {
        val errorMsg = "smth went wrong"
        val gameResult = GameResult("Amos", "Diego", 4, 5)
        val expectedResult = GameResultEntity(UUID.randomUUID().toString(), "Amos", "Diego", 4, 5)
        Mockito.`when`(
            gameResultFactory.createSingleGameResult(
                gameResult.firstPerson,
                gameResult.secondPerson,
                gameResult.firstScore,
                gameResult.secondScore
            )
        ).thenReturn(expectedResult)
        Mockito.`when`(gamesRepository.addGame(expectedResult))
            .thenReturn(Single.error(Throwable(errorMsg)))

        viewModel.addGame("Amos", "Diego", 4, 5)

        assertEquals(LiveDataTestUtil.getValue(viewModel.errorEvent), errorMsg)
    }

    @Test
    fun `Get games emit error`() {
        val errorMsg = "smth went wrong"
        Mockito.`when`(gamesRepository.getGames()).thenReturn(Observable.error(Throwable(errorMsg)))

        viewModel.getGames()

        assertEquals(
            LiveDataTestUtil.getValue(viewModel.errorEvent), errorMsg
        )
    }
}