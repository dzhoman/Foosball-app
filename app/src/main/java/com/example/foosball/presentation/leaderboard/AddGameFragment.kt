package com.example.foosball.presentation.leaderboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.foosball.App
import com.example.foosball.R
import com.example.foosball.databinding.FragmentAddGameBinding
import com.example.foosball.utils.ResourceProvider
import com.example.foosball.utils.hideKeyboard
import com.example.foosball.utils.onClick
import com.example.foosball.utils.showSnackbar
import javax.inject.Inject

class AddGameFragment : Fragment(R.layout.fragment_add_game) {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: LeaderboardViewModel by activityViewModels { factory }

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val viewBinding by viewBinding(FragmentAddGameBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnAddResult.onClick {
            if (isInputValid()) {
                hideKeyboard()
                viewModel.addGame(
                    viewBinding.etFirstPlayer.text.toString(),
                    viewBinding.etSecondPlayer.text.toString(),
                    viewBinding.etFirstScore.text.toString().toInt(),
                    viewBinding.etSecondScore.text.toString().toInt(),
                )
            } else {
                showSnackbar(viewBinding.root, resourceProvider.string(R.string.bad_input))
            }
        }
        viewModel.gameAddedLiveData.observe(viewLifecycleOwner) {
            clearUI()
            showSnackbar(viewBinding.root, resourceProvider.string(R.string.game_added))
        }
    }

    private fun clearUI() {
        viewBinding.etFirstPlayer.text.clear()
        viewBinding.etSecondPlayer.text.clear()
        viewBinding.etFirstScore.text.clear()
        viewBinding.etSecondScore.text.clear()
    }

    private fun isInputValid(): Boolean {
        return viewBinding.etFirstPlayer.text.isNotEmpty() &&
                viewBinding.etSecondPlayer.text.isNotEmpty() &&
                viewBinding.etFirstScore.text.isNotEmpty() &&
                viewBinding.etFirstScore.text.isNotEmpty() &&
                viewBinding.etSecondScore.text.isNotEmpty()
    }

    companion object {
        const val TAG = "AddGameFragment"
    }
}