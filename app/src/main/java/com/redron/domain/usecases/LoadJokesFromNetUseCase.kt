package com.redron.domain.usecases

import com.redron.domain.entity.Joke

interface LoadJokesFromNetUseCase {
    suspend operator fun invoke(): List<Joke>
}