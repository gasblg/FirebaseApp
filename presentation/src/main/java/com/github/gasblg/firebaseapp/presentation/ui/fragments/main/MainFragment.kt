package com.github.gasblg.firebaseapp.presentation.ui.fragments.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.Transitions
import com.github.gasblg.firebaseapp.presentation.databinding.FragmentMainBinding
import com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs.AddDialog
import com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs.RemoveDialog

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var authInstance: FirebaseAuth

    private lateinit var adapter: MainAdapter
    private var binding: FragmentMainBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragment =
            FragmentMainBinding.inflate(inflater, container, false)
        this.binding = fragment
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecycler()
        bindClicks()
        bindListener()
        observeData()
    }

    private fun signOut() {
        authInstance.signOut()
        googleSignInClient.signOut()
        viewModel.logOut()
        navigateToAuth()
    }

    private fun navigateToAuth() {
        startActivity(Transitions.startAuth(requireContext().applicationContext))
        activity?.finish()
    }

    private fun observeData() {
        observeProfile()
        observeState()
    }


    private fun initAdapter() {
        adapter = MainAdapter()
        adapter.apply {
            setOnItemClickListener { item ->
                Toast.makeText(
                    context,
                    "name: ${item.name}\ndescription: ${item.description}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setOnDeleteItemClickListener { item ->
                val dialog = RemoveDialog.createInstance(item.id)
                dialog.show(childFragmentManager, RemoveDialog.TAG)
            }
        }
    }

    private fun initRecycler() {
        with(binding!!) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter = adapter
        }
    }

    private fun bindClicks() {
        with(binding!!) {
            fab.setOnClickListener {
                val dialog = AddDialog()
                dialog.show(childFragmentManager, AddDialog.TAG)
            }
            ivExit.setOnClickListener {
                signOut()
            }
        }
    }

    private fun bindListener() {
        addListener()
        removeListener()
    }

    private fun addListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            ADD_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.getSerializable(ITEM, Item::class.java)
            } else {
                result.getSerializable(ITEM) as Item
            }
            item?.let {
                viewModel.addItem(it)
            }
        }
    }

    private fun removeListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REMOVE_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val itemId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.getSerializable(ITEM_ID, String::class.java)
            } else {
                result.getSerializable(ITEM_ID) as String
            }
            viewModel.removeItem(itemId)
        }
    }


    private fun observeProfile() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileData.collect { profile ->
                    binding?.userName?.text = profile.name
                    binding?.email?.text = profile.email
                    Glide.with(requireContext())
                        .load(profile.photo)
                        .centerCrop()
                        .into(binding!!.photo)
                }
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.let { data ->
                        when (data) {
                            is MainViewModel.UiState.Loading -> showLoadingState()
                            is MainViewModel.UiState.Empty -> showMessageState(getString(R.string.empty))
                            is MainViewModel.UiState.Success -> showListState(data.items)
                            is MainViewModel.UiState.Error -> showMessageState(
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
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            error.visibility = View.GONE
        }
    }

    private fun showListState(list: List<Item>) {
        with(binding!!) {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            error.visibility = View.GONE
        }
        setAdapterData(list)
    }

    private fun setAdapterData(list: List<Item>) {
        adapter.setData(list)
    }

    private fun showMessageState(message: String) {
        with(binding!!) {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.GONE
            error.visibility = View.VISIBLE
            error.text = message
        }
    }

    companion object {
        const val ADD_KEY = "add_key"
        const val REMOVE_KEY = "remove_key"

        const val ITEM = "item"
        const val ITEM_ID = "item_id"

    }
}