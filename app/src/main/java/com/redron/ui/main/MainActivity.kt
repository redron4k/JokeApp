package com.redron.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivityMainBinding
import com.redron.ui.JokeViewModelFactory
import com.redron.ui.main.recycler.JokesListAdapter
import com.redron.ui.main.recycler.util.JokeItemCallback
import com.redron.ui.single_joke.SingleJokeActivity

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: JokesListViewModel

    private val itemCallback = JokeItemCallback()
    private val adapter = JokesListAdapter(itemCallback) {
        startActivity(SingleJokeActivity.getInstance(this, it))
    }
    private val generator = JokesGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        binding.recyclerView.adapter = adapter
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
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}
