package com.example.apnamart.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnamart.ui.helper.ResultState
import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.domain.usecases.FetchReposeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val fetchReposUseCase: FetchReposeUseCase,
    private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _repos: MutableLiveData<ResultState<List<RepositoryModel>>?> = MutableLiveData()


    fun getRepoLiveData(): LiveData<ResultState<List<RepositoryModel>>?> {
        return _repos
    }

    fun fetchRepos(query: String) {
        viewModelScope.launch(dispatcher) {
            _repos.postValue(
                fetchReposUseCase(query)
            )
        }
    }
}