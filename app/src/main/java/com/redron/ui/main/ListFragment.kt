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
import com.redron.R
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentListBinding
import com.redron.ui.main.recycler.JokesListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding


    private val viewModel: JokesListViewModel by viewModels {
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

    private val adapter = JokesListAdapter {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToJokeDetailsFragment(it)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: run {
            viewModel.generateJokes()
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
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            delay(1500L)
            viewModel.jokes.collect {
                adapter.submitList(it)
                if (it.isEmpty()) {
                    binding.textView.visibility = View.VISIBLE
                } else {
                    binding.textView.visibility = View.GONE
                }
            }
        }
        lifecycleScope.launch {
            viewModel.error.collect {
                it?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}