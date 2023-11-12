package com.example.apnamart.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apnamart.databinding.ListItemRepositoryBinding
import com.example.apnamart.domain.models.RepositoryModel
import com.example.apnamart.ui.helper.makeGone
import com.example.apnamart.ui.helper.makeVisible
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeScreenAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>() {
    private var list: List<RepositoryModel> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun bindList(list: List<RepositoryModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    class HomeScreenViewHolder(_binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        var binding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val adapterLayout =
            ListItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        holder.binding.apply {
            list[position].let { model ->
                try {
                    Picasso.get().load(model.owner?.avatarUrl).into(ivRepo)
                } catch (e: Exception) {
                    Log.d("ERROR", "AVATAR NULL")
                }
                tvLang.text = model.language ?: "--"
                tvFork.text = model.forks?.toString() ?: "--"
                tvStar.text = model.watchers?.toString() ?: "--"
                tvDesc.text = model.description ?: "--"
                tvRepoName.text = model.name ?: "--"
                tvOwnerName.text = model.owner?.login ?: "--"
                holder.itemView.setOnClickListener {
                    if (!model.visible) clCollapse.makeVisible()
                    else clCollapse.makeGone()
                    model.visible = !model.visible
                }
            }
        }
    }
}