package com.theberdakh.suvchi.ui.contracts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsEntity
import com.theberdakh.suvchi.databinding.FragmentMessageBinding
import com.theberdakh.suvchi.presentation.UserViewModel
import com.theberdakh.suvchi.util.downloadFile
import com.theberdakh.suvchi.util.showSnackbar
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : Fragment(), ContractPagingAdapter.ContractClickEvent {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val userViewModel by viewModel<UserViewModel>()
    private val contractAdapter = ContractPagingAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)


        initRecyclerView()

        initStatusObservers()




        binding.recyclerViewContracts.adapter = contractAdapter
        lifecycleScope.launch {
          userViewModel.contracts.collect{
              contractAdapter.submitData(it)
          }
        }


        userViewModel.allContractsResponseMessage.onEach {
            Log.d("Tag", it)
        }.launchIn(lifecycleScope)

        userViewModel.allContractsResponseError.onEach {
            it.printStackTrace()
        }.launchIn(lifecycleScope)


        return binding.root
    }

    private fun acceptListener(id: Int) {
        lifecycleScope.launch {
            userViewModel.setUserContractPositive(id)
            userViewModel.getUserContracts()
        }
    }

    private fun initStatusObservers() {

    }

    private fun initRecyclerView() {

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(contract: AllContractsEntity) {
        downloadFile(requireActivity(), contract.file, "${contract.title}.${contract.file.subSequence(contract.file.length-3, contract.file.length)}")
    }


}