package com.redron.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivityMainBinding
import com.redron.ui.main.recycler.JokesListAdapter
import com.redron.ui.main.recycler.util.JokeItemCallback
import com.redron.ui.single_joke.SingleJokeActivity

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val itemCallback = JokeItemCallback()
    private val adapter = JokesListAdapter(itemCallback) {
        startActivity(SingleJokeActivity.getInstance(this, it))
    }
    private val generator = JokesGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        savedInstanceState ?: run { generator.generate() }
        setupData()
    }

    private fun setupData() {
        adapter.submitList(generator.jokes)
    }
}
