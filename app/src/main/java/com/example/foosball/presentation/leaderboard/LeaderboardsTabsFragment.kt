package com.example.foosball.presentation.leaderboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.foosball.R
import com.example.foosball.databinding.FragmentLeaderboardsTabsBinding
import com.google.android.material.tabs.TabLayoutMediator

class LeaderboardsTabsFragment : Fragment(R.layout.fragment_leaderboards_tabs) {

    private val viewBinding by viewBinding(FragmentLeaderboardsTabsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    private fun initPager() {
        viewBinding.leaderboardPager.adapter = LeaderboardsPagerAdapter(this)
        TabLayoutMediator(
            viewBinding.leaderboardTabLayout,
            viewBinding.leaderboardPager
        ) { tab, position ->
            when (position) {
                TAB_LEADERBOARD -> tab.text = getString(R.string.leaderboard_title)
                TAB_GAMES -> tab.text = getString(R.string.games_title)
            }
        }.attach()
    }

    class LeaderboardsPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == TAB_LEADERBOARD) {
                LeaderboardFragment()
            } else {
                GamesListFragment()
            }
        }
    }

    companion object {
        const val TAB_LEADERBOARD = 0
        const val TAB_GAMES = 1
        const val TAB_COUNT = 2
    }

}