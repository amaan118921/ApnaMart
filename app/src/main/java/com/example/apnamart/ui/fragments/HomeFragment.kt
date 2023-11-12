package com.example.apnamart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.apnamart.R
import com.example.apnamart.ui.helper.ResultState
import com.example.apnamart.databinding.FragmentHomeBinding
import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.ui.viewModel.AppViewModel
import com.example.apnamart.ui.adapter.HomeScreenAdapter
import com.example.apnamart.ui.helper.Constants.Companion.LOCAL_DATA
import com.example.apnamart.ui.helper.Constants.Companion.QUERY
import com.example.apnamart.ui.helper.Constants.Companion.SUCCESS_MSG
import com.example.apnamart.ui.helper.makeGone
import com.example.apnamart.ui.helper.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {

    private val viewModel: AppViewModel by activityViewModels()
    private var binding: FragmentHomeBinding? = null

    @Inject
    lateinit var adapter: HomeScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        binding?.apply {
            tbHome.tvTitle.text = getString(R.string.trending)
            rvHome.adapter = adapter
            rvHome.layoutManager = LinearLayoutManager(requireContext())
            srHome.setOnRefreshListener(this@HomeFragment)
            ef.cvRetry.setOnClickListener(this@HomeFragment)
        }
        setObservers()
        fetchAllRepos(QUERY)
    }

    private fun fetchAllRepos(query: String) {
        hideErrorFrame()
        showProgressFrame()
        viewModel.fetchRepos(query)
    }

    private fun setObservers() {
        viewModel.getRepoLiveData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> onRepoFetchSuccess(result.data)
                is ResultState.Error -> onRepoFetchFailure(result.message, result.data)
                else -> {}
            }
        }
    }


    private fun onRepoFetchFailure(message: String?, localData: List<RepositoryModel>?) {
        hideProgressFrame()
        if (!localData.isNullOrEmpty()) {
            showToast(LOCAL_DATA)
            adapter.bindList(localData)
        } else {
            showToast(message ?: "")
            showErrorFrame()
        }
    }

    private fun onRepoFetchSuccess(data: List<RepositoryModel>?) {
        hideProgressFrame()
        showToast(SUCCESS_MSG)
        adapter.bindList(data ?: emptyList())
    }

    private fun showErrorFrame() {
        binding?.efHome?.makeVisible()
    }

    private fun hideProgressFrame() {
        binding?.pfHome?.makeGone()
    }

    private fun hideErrorFrame() {
        binding?.efHome?.makeGone()
    }

    private fun showProgressFrame() {
        binding?.pfHome?.makeVisible()
    }

    override fun onRefresh() {
        fetchAllRepos(QUERY)
        binding?.srHome?.isRefreshing = false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cvRetry -> fetchAllRepos(QUERY)
        }
    }


}