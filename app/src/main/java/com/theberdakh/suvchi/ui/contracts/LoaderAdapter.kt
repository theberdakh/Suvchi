package com.theberdakh.suvchi.ui.contracts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.databinding.NetworkLoaderBinding


private const val TAG = "LoaderAdapter"

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(val binding: NetworkLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState) {
            Log.i(TAG, "bind: $loadState")
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {

        return LoaderViewHolder(
            NetworkLoaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}
