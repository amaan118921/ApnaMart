package com.example.apnamart.domain.usecases

import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.ui.helper.ResultState

class FakeFetchReposUseCase : FetchReposeUseCase {

    private var failed = false
    override suspend operator fun invoke(query: String): ResultState<List<RepositoryModel>> {
        if (failed) return ResultState.Error(null, "something went wrong")
        return ResultState.Success(emptyList())
    }

    fun failed(boolean: Boolean) {
        failed = boolean
    }
}