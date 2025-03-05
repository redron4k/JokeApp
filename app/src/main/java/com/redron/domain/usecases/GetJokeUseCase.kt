package com.redron.domain.usecases

import com.redron.domain.entity.Joke

interface GetJokeUseCase {
    suspend operator fun invoke(id: String): Joke?
}