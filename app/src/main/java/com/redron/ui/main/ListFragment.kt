package com.redron.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redron.R
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentListBinding
import com.redron.ui.JokeViewModelFactory
import com.redron.ui.main.recycler.JokesListAdapter

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: JokesListViewModel
    private val adapter = JokesListAdapter {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToJokeDetailsFragment(
                viewModel.getJokeId(it)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        savedInstanceState ?: run {
            viewModel.generateJokes()
        }
    }

    private fun initViewModel() {
        val factory = JokeViewModelFactory(JokesGenerator)
        viewModel = ViewModelProvider(this, factory)[JokesListViewModel::class.java]
        viewModel.jokes.observe(this) {
            adapter.submitList(it)
        }
        viewModel.error.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}