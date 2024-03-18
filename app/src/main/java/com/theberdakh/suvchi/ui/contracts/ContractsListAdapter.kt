package com.theberdakh.suvchi.ui.contracts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsEntity
import com.theberdakh.suvchi.databinding.ItemListContractBinding
import com.theberdakh.suvchi.util.convertDateTime
import com.theberdakh.suvchi.util.showToast

class ContractsListAdapter (val onAcceptClick: (AllContractsEntity) -> Unit, val onFileClick: (AllContractsEntity) -> Unit):
    ListAdapter<AllContractsEntity, ContractListViewHolder>(
        ContractListDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractListViewHolder {
        return ContractListViewHolder(
            ItemListContractBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContractListViewHolder, position: Int)  {
        val contract = getItem(position)
        holder.binding.apply {
            textviewFileTitle.text = contract.title
            textviewFileSubtitle.text = convertDateTime(contract.createdAt)




            layoutFile.setOnClickListener {
                showToast("File clicked")
                onFileClick.invoke(contract)
            }



            buttonAccept.setOnClickListener {
                showToast("Clicked")
                onAcceptClick.invoke(contract)
            }
        }
    }




}

class ContractListViewHolder(val binding: ItemListContractBinding) :
    RecyclerView.ViewHolder(binding.root)

class ContractListDiffCallback : DiffUtil.ItemCallback<AllContractsEntity>() {
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
