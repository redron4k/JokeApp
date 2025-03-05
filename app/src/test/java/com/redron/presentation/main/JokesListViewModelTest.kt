package com.redron.presentation.main

import com.redron.domain.entity.Joke
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.RefreshCacheUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class JokesListViewModelTest {

    private val loadJokesLocal = mock<LoadJokesLocalUseCase>()
    private val addJoke = mock<AddJokeUseCase>()
    private val addJokes = mock<AddJokesUseCase>()
    private val clearLoadedJokes = mock<ClearLoadedJokesUseCase>()
    private val loadJokesFromNet = mock<LoadJokesFromNetUseCase>()
    private val addToFavorites = mock<AddToFavoritesUseCase>()
    private val removeFromFavorites = mock<RemoveFromFavoritesUseCase>()
    private val refreshCache = mock<RefreshCacheUseCase>()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: JokesListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = JokesListViewModel(
            loadJokesLocal = loadJokesLocal,
            addJoke = addJoke,
            addJokes = addJokes,
            clearLoadedJokes = clearLoadedJokes,
            loadJokesFromNet = loadJokesFromNet,
            addToFavorites = addToFavorites,
            removeFromFavorites = removeFromFavorites,
            refreshCache = refreshCache,
        )
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `initJokes() should load jokes from net`() = runTest {
        val testJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
            Joke(
                "question 2",
                "answer 2",
                "category 2"
            ),
        )
        whenever(loadJokesFromNet.invoke()).thenReturn(testJokes)
        whenever(loadJokesLocal.invoke()).thenReturn(testJokes)

        viewModel.initJokes()

        verify(addJokes).invoke(testJokes)
        assertEquals(testJokes, viewModel.jokesState.value.jokes)
        assertEquals(false, viewModel.jokesState.value.isLoading)
        assertEquals(false, viewModel.isLoadFromCache.value)
    }

    @Test
    fun `initJokes() should load jokes from cache`() = runTest {
        val testJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
            Joke(
                "question 2",
                "answer 2",
                "category 2"
            ),
        )
        whenever(loadJokesFromNet.invoke()).thenThrow(RuntimeException("NetworkError"))
        whenever(loadJokesLocal.invoke()).thenReturn(testJokes)

        viewModel.initJokes()

        verify(refreshCache).invoke()
        assertEquals(testJokes, viewModel.jokesState.value.jokes)
        assertEquals(false, viewModel.jokesState.value.isLoading)
        assertEquals(true, viewModel.isLoadFromCache.value)
    }

    @Test
    fun `loadMoreJokes() should load jokes from net`() = runTest {
        val initJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
        )

        val loadedJokes = listOf(
            Joke(
                "question 2",
                "answer 2",
                "category 2"
            ),
            Joke(
                "question 3",
                "answer 3",
                "category 1"
            ),
        )
        whenever(loadJokesFromNet.invoke()).thenReturn(loadedJokes)
        whenever(loadJokesLocal.invoke()).thenReturn(initJokes + loadedJokes)

        viewModel.loadMoreJokes()

        verify(addJokes).invoke(loadedJokes)
        assertEquals(initJokes + loadedJokes, viewModel.jokesState.value.jokes)
        assertEquals(false, viewModel.jokesState.value.isLoading)
        assertEquals(false, viewModel.isLoadFromCache.value)
    }

    @Test
    fun `loadMoreJokes() should load jokes from cache and isLoadFromCache == false`() = runTest {
        val initJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
        )

        val cachedJokes = listOf(
            Joke(
                "question 2",
                "answer 2",
                "category 2"
            ),
            Joke(
                "question 3",
                "answer 3",
                "category 1"
            ),
        )

        whenever(loadJokesFromNet.invoke()).thenThrow(RuntimeException("NetworkError"))
        whenever(loadJokesLocal.invoke()).thenReturn(initJokes + cachedJokes)

        viewModel.loadMoreJokes()

        verify(refreshCache).invoke()
        assertEquals(initJokes + cachedJokes, viewModel.jokesState.value.jokes)
        assertEquals(false, viewModel.jokesState.value.isLoading)
        assertEquals(true, viewModel.isLoadFromCache.value)
    }

    @Test
    fun `loadMoreJokes() should load jokes from cache and isLoadFromCache == true`() = runTest {
        val initJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
        )

        val cachedJokes = listOf(
            Joke(
                "question 2",
                "answer 2",
                "category 2"
            ),
            Joke(
                "question 3",
                "answer 3",
                "category 1"
            ),
        )

        whenever(loadJokesFromNet.invoke()).thenThrow(RuntimeException("NetworkError"))
        whenever(loadJokesLocal.invoke()).thenReturn(initJokes + cachedJokes)

        viewModel.loadMoreJokes()   // makes flag isLoadFromCache true
        viewModel.loadMoreJokes()

        verify(refreshCache, times(1)).invoke()
        assertEquals(initJokes + cachedJokes, viewModel.jokesState.value.jokes)
        assertEquals(false, viewModel.jokesState.value.isLoading)
        assertEquals(true, viewModel.isLoadFromCache.value)
    }

    @Test
    fun `addNewJoke() should add joke`() = runTest {
        val initJokes = listOf(
            Joke(
                "question 1",
                "answer 1",
                "category 1"
            ),
        )

        val newJoke = Joke(
            "question 2",
            "answer 2",
            "category 2"
        )

        whenever(loadJokesLocal.invoke()).thenReturn(initJokes + newJoke)

        viewModel.addNewJoke(newJoke)

        verify(addJoke).invoke(newJoke)
        assertEquals(initJokes + newJoke, viewModel.jokesState.value.jokes)
    }

    @Test
    fun `onFavClicked() should add joke to favorites if it is not`() = runTest {
        val initJoke = Joke(
            "question 1",
            "answer 1",
            "category 1",
        )

        whenever(loadJokesLocal.invoke()).thenReturn(listOf(initJoke.copy(isFavorite = true)))

        viewModel.onFavClicked(initJoke)

        verify(addToFavorites).invoke(initJoke.uuid)
        assertEquals(true, viewModel.jokesState.value.jokes[0].isFavorite)
    }

    @Test
    fun `onFavClicked() should remove joke from favorites if it is`() = runTest {
        val initJoke = Joke(
            "question 1",
            "answer 1",
            "category 1",
            isFavorite = true,
        )

        whenever(loadJokesLocal.invoke()).thenReturn(listOf(initJoke.copy(isFavorite = false)))

        viewModel.onFavClicked(initJoke)

        verify(removeFromFavorites).invoke(initJoke.uuid)
        assertEquals(false, viewModel.jokesState.value.jokes[0].isFavorite)
    }
}