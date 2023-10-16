package com.github.gasblg.firebaseapp.presentation.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.gasblg.firebaseapp.analytics.manager.AnalyticsManager
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.databinding.FragmentDetailBinding
import com.github.gasblg.firebaseapp.presentation.ui.fragments.main.MainFragment


import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var analytics: AnalyticsManager

    private var binding: FragmentDetailBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragment =
            FragmentDetailBinding.inflate(inflater, container, false)
        this.binding = fragment
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analytics.openDetailScreen()

        arguments?.let {
            val itemId = it.getString(MainFragment.ITEM_ID) as String
            loadItem(itemId)
        }
        observeState()
    }

    private fun loadItem(itemId: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getItem(itemId)
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.let { data ->
                        when (data) {
                            is DetailViewModel.UiState.Loading -> showLoadingState()
                            is DetailViewModel.UiState.Empty -> showMessageState(getString(R.string.no_data))
                            is DetailViewModel.UiState.Success -> showState(data.item)
                            is DetailViewModel.UiState.Error -> showMessageState(
                                data.exception.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingState() {
        with(binding!!) {
            ll.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            error.visibility = View.GONE
        }
    }

    private fun showState(item: Item) {
        with(binding!!) {
            ll.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            error.visibility = View.GONE
            name.text = item.name
            description.text = item.description
        }
    }

    private fun showMessageState(message: String) {
        with(binding!!) {
            ll.visibility = View.GONE
            progressBar.visibility = View.GONE
            error.visibility = View.VISIBLE
            error.text = message
        }
    }

}