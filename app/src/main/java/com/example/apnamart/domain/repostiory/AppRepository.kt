package com.example.apnamart.domain.repostiory

import com.example.apnamart.domain.models.RepositoryResponseModel
import retrofit2.Response

interface AppRepository {
    suspend fun fetchRepos(query: String): Response<RepositoryResponseModel>
}