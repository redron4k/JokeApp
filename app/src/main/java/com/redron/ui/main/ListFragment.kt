package com.redron.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redron.R
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentListBinding
import com.redron.ui.JokeViewModelFactory
import com.redron.ui.main.recycler.JokesListAdapter

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
        JokeViewModelFactory(JokesGenerator)
    }
    private val adapter = JokesListAdapter {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToJokeDetailsFragment(it)
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
        viewModel.jokes.observe(this) {
            adapter.submitList(it)
        }
        viewModel.error.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}