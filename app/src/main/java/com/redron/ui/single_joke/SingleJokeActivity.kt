package com.redron.ui.single_joke
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.lifecycle.ViewModelProvider
//import com.redron.data.Joke
//import com.redron.data.JokesGenerator
//import com.redron.databinding.FragmentSingleJokeBinding
//import com.redron.ui.JokeViewModelFactory
//
//class SingleJokeActivity : ComponentActivity() {
//
//    private lateinit var binding: FragmentSingleJokeBinding
//    private lateinit var viewModel: JokeDetailsViewModel
//    private var jokeId: Int = -1
//
//    companion object {
//
//        private const val JOKE_ID_EXTRA = "JOKE_ID"
//
//        fun getInstance(context: Context, jokeId: Int): Intent {
//            return Intent(context, SingleJokeActivity::class.java).apply {
//                putExtra(JOKE_ID_EXTRA, jokeId)
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentSingleJokeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initViewModel()
//        handleExtra()
//    }
//
//    private fun initViewModel() {
//        val factory = JokeViewModelFactory(JokesGenerator)
//        viewModel = ViewModelProvider(this, factory)[JokeDetailsViewModel::class.java]
//        viewModel.jokes.observe(this, ::setUpJoke)
//        viewModel.error.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    private fun handleExtra() {
//        jokeId = intent.getIntExtra(JOKE_ID_EXTRA, -1)
//        viewModel.loadSingleJoke(jokeId)
//    }
//
//    private fun setUpJoke(item: Joke) {
//        with (binding) {
//            textViewQuestion.text = item.jokeQuestion
//            textViewAnswer.text = item.jokeAnswer
//            textViewCategory.text = item.category
//        }
//    }
//}