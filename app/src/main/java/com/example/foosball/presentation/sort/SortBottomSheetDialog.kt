package com.example.foosball.presentation.sort

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.foosball.R
import com.example.foosball.databinding.SortBottomSheetFragmentBinding
import com.example.foosball.utils.onClick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetDialog(private val listener: OnSortTypeClickListener) :
    BottomSheetDialogFragment(R.layout.sort_bottom_sheet_fragment) {

    private val viewBinding by viewBinding(SortBottomSheetFragmentBinding::bind)

    interface OnSortTypeClickListener {
        fun sortByGamesWon()
        fun sortByGamesCount()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tvSortByGamesWon.onClick {
            listener.sortByGamesWon()
            dismiss()
        }
        viewBinding.tvSortByGamesCount.onClick {
            listener.sortByGamesCount()
            dismiss()
        }
    }

    companion object {
        const val TAG = "SortBottomSheetDialog"
    }
}