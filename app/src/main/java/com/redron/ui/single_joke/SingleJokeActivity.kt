package com.redron.ui.single_joke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivitySingleJokeBinding
import com.redron.ui.JokeViewModelFactory

class SingleJokeActivity : ComponentActivity() {

    private lateinit var binding: ActivitySingleJokeBinding
    private lateinit var viewModel: SingleJokeViewModel
    private var position: Int = -1

    companion object {

        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun getInstance(context: Context, position: Int): Intent {
            return Intent(context, SingleJokeActivity::class.java).apply {
                putExtra(JOKE_POSITION_EXTRA, position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        handleExtra()
    }

    private fun initViewModel() {
        val factory = JokeViewModelFactory(JokesGenerator)
        viewModel = ViewModelProvider(this, factory)[SingleJokeViewModel::class.java]
        viewModel.jokes.observe(this, ::setUpJoke)
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleExtra() {
        position = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)
        viewModel.loadSingleJoke(position)
    }

    private fun setUpJoke(item: Joke) {
        with (binding) {
            textViewQuestion.text = item.jokeQuestion
            textViewAnswer.text = item.jokeAnswer
            textViewCategory.text = item.category
        }
    }
}