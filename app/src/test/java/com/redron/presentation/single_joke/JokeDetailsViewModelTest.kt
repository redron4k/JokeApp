package com.redron.presentation.single_joke

import com.redron.domain.entity.Joke
import com.redron.domain.usecases.GetJokeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class JokeDetailsViewModelTest {

    private val getJoke = mock<GetJokeUseCase>()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: JokeDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = JokeDetailsViewModel(getJoke)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `loadSingleJoke() should load joke by id`() = runTest{
        val joke = Joke(
            "question",
            "answer",
            "category",
        )

        whenever(getJoke.invoke(joke.uuid)).thenReturn(joke)

        viewModel.loadSingleJoke(joke.uuid)
        advanceUntilIdle()

        verify(getJoke).invoke(joke.uuid)
        assertEquals(joke, viewModel.joke.value)
    }
}