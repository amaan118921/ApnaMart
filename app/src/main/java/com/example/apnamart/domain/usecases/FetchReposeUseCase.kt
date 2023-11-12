package com.example.apnamart.domain.usecases

import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.ui.helper.ResultState

interface FetchReposeUseCase {
    suspend operator fun invoke(query: String): ResultState<List<RepositoryModel>>
}