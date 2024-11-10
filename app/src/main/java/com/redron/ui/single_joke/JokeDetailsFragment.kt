package com.redron.ui.single_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.redron.R
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentJokeDetailsBinding
import com.redron.ui.JokeViewModelFactory

class JokeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentJokeDetailsBinding
    private lateinit var viewModel: JokeDetailsViewModel
    private val args: JokeDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_joke_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentJokeDetailsBinding.bind(view)
        viewModel.loadSingleJoke(args.jokeID)
    }

    private fun initViewModel() {
        val factory = JokeViewModelFactory(JokesGenerator)
        viewModel = ViewModelProvider(this, factory)[JokeDetailsViewModel::class.java]
        viewModel.jokes.observe(this, ::setUpJoke)
        viewModel.error.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpJoke(item: Joke) {
        with(binding) {
            textViewQuestion.text = item.jokeQuestion
            textViewAnswer.text = item.jokeAnswer
            textViewCategory.text = item.category
        }
    }
}