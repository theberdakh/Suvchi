package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.databinding.DialogDeclineBinding

class BottomSheet: BottomSheetDialogFragment(){
    private var _binding: DialogDeclineBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDeclineBinding.inflate(inflater, container, false)
      //  setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.FullBottomSheetStyle)

        binding.layoutWaterUsage.isVisible = true


        binding.titleWaterUsage.setOnClickListener {
            binding.layoutWaterUsage.isVisible = !binding.layoutWaterUsage.isVisible
        }

        return binding.root
    }
    


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}