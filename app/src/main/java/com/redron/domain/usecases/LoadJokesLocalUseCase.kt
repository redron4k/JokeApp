package com.redron.domain.usecases

import com.redron.domain.entity.Joke

interface LoadJokesLocalUseCase {
    suspend operator fun invoke(): List<Joke>
}