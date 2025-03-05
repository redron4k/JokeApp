package com.redron.presentation.single_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.redron.App
import com.redron.R
import com.redron.domain.entity.Joke
import com.redron.databinding.FragmentJokeDetailsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeDetailsFragment : Fragment() {

    private var _binding: FragmentJokeDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: JokeDetailsViewModelFactory

    private val viewModel: JokeDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory },
    )

    private val args: JokeDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_joke_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentJokeDetailsBinding.bind(view)

        collectViewModelState()

        viewModel.loadSingleJoke(args.jokeID)
    }

    private fun collectViewModelState() {
        lifecycleScope.launch {
            viewModel.joke.collect {
                it?.let { setUpJoke(it) }
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

    private fun setUpJoke(item: Joke?) {
        with(binding) {
            textViewQuestion.text = item?.jokeQuestion
            textViewAnswer.text = item?.jokeAnswer
            textViewCategory.text = item?.category
            if (item != null) {
                textViewFromNet.text = if (item.isFromNet)
                    activity?.getString(R.string.is_from_net_true)
                else
                    activity?.getString(R.string.is_from_net_false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}