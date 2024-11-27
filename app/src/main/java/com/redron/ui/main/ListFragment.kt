package com.redron.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redron.R
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentListBinding
import com.redron.ui.main.recycler.JokesListAdapter
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding


    private val viewModel: JokesListViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = {
            ViewModelProvider.Factory.from(
                ViewModelInitializer(
                    clazz = JokesListViewModel::class.java,
                    initializer = {
                        JokesListViewModel(
                            JokesGenerator
                        )
                    }
                )
            )
        }
    )

    private val adapter = JokesListAdapter {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToJokeDetailsFragment(it)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: run {
            viewModel.loadJokes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.addFragmentButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToAddJokeFragment()
            )
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.loadJokes()
                }
            }
        })

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.jokes.collect {
                adapter.submitList(it)
                binding.textView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
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
            viewModel.isLoading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}