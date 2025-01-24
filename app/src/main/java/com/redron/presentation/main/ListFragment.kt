package com.redron.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.redron.App
import com.redron.R
import com.redron.databinding.FragmentListBinding
import com.redron.presentation.main.recycler.JokesListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactory: JokesListViewModelFactory

    private val viewModel: JokesListViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { viewModelFactory },
    )

    private val adapter = JokesListAdapter(
        clickListener = {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToJokeDetailsFragment(it)
            )
        },

        favClickListener = {
            viewModel.onFavClicked(it)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as App).appComponent.inject(this)
        viewModel.clearLoaded()

        savedInstanceState ?: run {
            viewModel.initJokes()
        }
    }

    private fun setOnAddJokeButtonClickListener() {
        binding.addJokeButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToAddJokeFragment()
            )
        }
    }

    private fun addOnRecyclerViewScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.loadMoreJokes()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)
        binding.recyclerView.adapter = adapter

        addOnRecyclerViewScrollListener()

        setOnAddJokeButtonClickListener()

        collectViewModelState()
    }

    private fun collectViewModelState() {

        lifecycleScope.launch {
            viewModel.jokesState.collect {
                binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(it.jokes)
                binding.textView.visibility = if (it.jokes.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                it?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoadFromCache.collect {
                if (it)
                    Snackbar.make(
                        binding.root,
                        requireActivity().getString(R.string.is_load_from_cache_true),
                        Snackbar.LENGTH_SHORT
                    ).apply {
                        setGestureInsetBottomIgnored(false)
                        show()
                    }
            }
        }
    }
}