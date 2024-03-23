package com.theberdakh.suvchi.ui.contracts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsEntity
import com.theberdakh.suvchi.databinding.ItemListContractBinding

class ContractPagingAdapter(val listener: ContractClickEvent) :
    PagingDataAdapter<AllContractsEntity, ContractPagingAdapter.ContractPagingViewHolder>(
        ContractDiffCallback()
    ) {

    interface ContractClickEvent {
        fun onClick(contract: AllContractsEntity)
    }


    inner class ContractPagingViewHolder(private val binding: ItemListContractBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: AllContractsEntity) {
            binding.textviewFileTitle.text = contract.title
            binding.textviewFileSubtitle.text = contract.file
            binding.root.setOnClickListener {
                listener.onClick(contract)
            }

        }
    }

    class ContractDiffCallback : DiffUtil.ItemCallback<AllContractsEntity>() {
        override fun areItemsTheSame(
            oldItem: AllContractsEntity,
            newItem: AllContractsEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AllContractsEntity,
            newItem: AllContractsEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ContractPagingViewHolder, position: Int) {
        val contract = getItem(position)
        holder.bind(contract!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractPagingViewHolder {
        return ContractPagingViewHolder(
            ItemListContractBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}