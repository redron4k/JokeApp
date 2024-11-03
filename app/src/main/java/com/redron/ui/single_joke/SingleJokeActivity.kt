package com.redron.ui.single_joke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.redron.R
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivitySingleJokeBinding

class SingleJokeActivity : ComponentActivity() {

    private lateinit var binding: ActivitySingleJokeBinding
    private val generator = JokesGenerator
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

        handleExtra()
    }

    private fun handleExtra() {
        position = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)
        if (position == -1) {
            handleError()
        } else {
            val item = generator.jokes[position]
            setUpJoke(item)
        }
    }

    private fun setUpJoke(item: Joke) {
        with (binding) {
            textViewQuestion.text = item.jokeQuestion
            textViewAnswer.text = item.jokeAnswer
            textViewCategory.text = item.category
        }
    }

    private fun handleError() {
        Toast.makeText(this, "Unexpected position error", Toast.LENGTH_SHORT).show()
        finish()
    }

}