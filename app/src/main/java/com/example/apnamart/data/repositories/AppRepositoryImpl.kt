package com.example.apnamart.data.repositories

import com.example.apnamart.data.network.Api
import com.example.apnamart.domain.models.RepositoryResponseModel
import com.example.apnamart.domain.repostiory.AppRepository
import retrofit2.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val api: Api) : AppRepository {
    override suspend fun fetchRepos(query: String): Response<RepositoryResponseModel> {
        return api.fetchRepos(query)
    }
}