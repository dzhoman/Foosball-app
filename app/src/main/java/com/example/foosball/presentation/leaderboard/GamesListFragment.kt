package com.example.foosball.presentation.leaderboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.foosball.App
import com.example.foosball.R
import com.example.foosball.databinding.FragmentGamesBinding
import com.example.foosball.utils.onClick
import com.example.foosball.utils.showSnackbar
import javax.inject.Inject

class GamesListFragment : Fragment(R.layout.fragment_games) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: GamesSharedViewModel by activityViewModels { factory }

    private val viewBinding by viewBinding(FragmentGamesBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewBinding.btnAdd.onClick {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, AddGameFragment(), AddGameFragment.TAG)
                .addToBackStack(AddGameFragment.TAG).commit()
        }
        viewModel.gamesLiveData.observe(viewLifecycleOwner) { results ->
            viewBinding.progressBar.visibility = View.GONE
            (viewBinding.rvGames.adapter as GamesListAdapter).update(results)
        }
        viewModel.errorEvent.observe(viewLifecycleOwner) {
            viewBinding.progressBar.visibility = View.GONE
            showSnackbar(viewBinding.root, it)
        }
    }

    private fun initList() {
        viewBinding.rvGames.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvGames.adapter = GamesListAdapter()
    }
}