package com.redron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivityMainBinding
import com.redron.recycler.JokesAdapter
import com.redron.recycler.JokesListAdapter
import com.redron.recycler.util.JokeItemCallback

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val itemCallback = JokeItemCallback()
    private val adapter = JokesListAdapter(itemCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val generator = JokesGenerator()
        val jokes = generator.generate()

        adapter.submitList(jokes)

        binding.recyclerView.adapter = adapter
    }
}
