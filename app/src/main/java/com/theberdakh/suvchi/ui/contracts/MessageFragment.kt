package com.theberdakh.suvchi.ui.contracts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.theberdakh.suvchi.databinding.FragmentMessageBinding
import com.theberdakh.suvchi.presentation.UserViewModel
import com.theberdakh.suvchi.util.downloadFile
import com.theberdakh.suvchi.util.showSnackbar
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        initRecyclerView()

        initStatusObservers()



        lifecycleScope.launch {
            userViewModel.getUserContracts()

        }
        val adapter = ContractsListAdapter({ onAcceptClick ->
            showToast("Aceepted click ${onAcceptClick.id}")
            lifecycleScope.launch {
                userViewModel.setUserContractPositive(onAcceptClick.id)
                userViewModel.getUserContracts()
            }
        }, { onFileClick ->
            showToast(onFileClick.fileId.toString())
            downloadFile(requireActivity(), onFileClick.file, onFileClick.title)

        })

        adapter.setHasStableIds(true)

        binding.recyclerViewContracts.adapter = adapter



        userViewModel.allContractsResponseSuccess.onEach {
            adapter.submitList(null)
            if (it.data.size != adapter.currentList.size){
                adapter.submitList(it.data)
            }
            Log.d("All count", it.count.toString())
        }.launchIn(lifecycleScope)





        userViewModel.allContractsResponseMessage.onEach {
            Log.d("Tag", it)
        }.launchIn(lifecycleScope)

        userViewModel.allContractsResponseError.onEach {
            it.printStackTrace()
        }.launchIn(lifecycleScope)


        return binding.root
    }

    private fun initStatusObservers() {
        userViewModel.statusContractSuccess.onEach {

            showToast(it.status)
        }.launchIn(lifecycleScope)

        userViewModel.statusContractMessage.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        userViewModel.statusContractError.onEach {
            it.printStackTrace()
        }.launchIn(lifecycleScope)
    }

    private fun initRecyclerView() {

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRefresh() {

    }


}