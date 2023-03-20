package com.example.foosball.presentation.leaderboard

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.foosball.App
import com.example.foosball.R
import com.example.foosball.databinding.FragmentLeaderboardBinding
import com.example.foosball.presentation.sort.SortBottomSheetDialog
import com.example.foosball.utils.onClick
import com.example.foosball.utils.showSnackbar
import javax.inject.Inject

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: LeaderboardViewModel by activityViewModels { factory }

    private val viewBinding by viewBinding(FragmentLeaderboardBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewBinding.btnSort.onClick {
            showSortBottomSheet()
        }
        viewModel.getGames()
        viewModel.leaderboardLiveData.observe(viewLifecycleOwner) { results ->
            viewBinding.progressBar.visibility = GONE
            (viewBinding.rvLeaderboard.adapter as LeaderboardsAdapter).update(results)
        }
        viewModel.errorEvent.observe(viewLifecycleOwner) {
            viewBinding.progressBar.visibility = GONE
            showSnackbar(viewBinding.root, it)
        }
    }

    private fun initList() {
        viewBinding.rvLeaderboard.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvLeaderboard.adapter = LeaderboardsAdapter(listOf())
    }

    private fun showSortBottomSheet() {
        SortBottomSheetDialog(object : SortBottomSheetDialog.OnSortTypeClickListener {
            override fun sortByGamesWon() {
                viewModel.sortChanged(LeaderboardViewModel.SortBy.GAMES_WON)
            }

            override fun sortByGamesCount() {
                viewModel.sortChanged(LeaderboardViewModel.SortBy.GAMES_COUNT)
            }
        }).show(
            requireActivity().supportFragmentManager,
            SortBottomSheetDialog.TAG
        )
    }
}