package com.example.apnamart.data.usecases

import com.example.apnamart.data.room.dao.AppDao
import com.example.apnamart.data.room.enitities.toRepositoryModel
import com.example.apnamart.ui.helper.ResultState
import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.domain.models.toRepositoryModelEntity
import com.example.apnamart.domain.repostiory.AppRepository
import com.example.apnamart.domain.usecases.FetchReposeUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchReposUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository,
    private val appDao: AppDao
) :
    FetchReposeUseCase {

    override suspend operator fun invoke(query: String): ResultState<List<RepositoryModel>> {
        val localData = appDao.getAllRepos().map {
            it.toRepositoryModel()
        }
        try {
            val response = appRepository.fetchRepos(query)

            if (response.isSuccessful) {
                val remoteData = response.body()?.items
                remoteData?.let {
                    appDao.deleteAllRepos()
                    appDao.insertRepos(it.map { model ->
                        model.toRepositoryModelEntity()
                    })
                    val updatedData = appDao.getAllRepos().map {
                        it.toRepositoryModel()
                    }
                    return ResultState.Success(updatedData)
                }
                return ResultState.Success(localData)
            } else return ResultState.Error(localData, "something went wrong", response.code())
        } catch (e: Exception) {
            return ResultState.Error(localData, e.localizedMessage ?: "")
        }
    }
}