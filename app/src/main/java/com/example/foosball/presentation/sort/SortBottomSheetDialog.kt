package com.example.foosball.presentation.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foosball.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetDialog(private val listener: OnSortTypeClickListener) : BottomSheetDialogFragment() {

    interface OnSortTypeClickListener {
        fun sortByGamesWon()
        fun sortByGamesCount()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.sort_bottom_sheet_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvSortByGamesWon).setOnClickListener {
            listener.sortByGamesWon()
            dismiss()
        }
        view.findViewById<TextView>(R.id.tvSortByGamesCount).setOnClickListener {
            listener.sortByGamesCount()
            dismiss()
        }
    }

    companion object {
        const val TAG = "SortBottomSheetDialog"
    }
}