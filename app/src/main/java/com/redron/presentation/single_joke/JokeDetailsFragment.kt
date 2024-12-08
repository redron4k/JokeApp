package com.redron.presentation.single_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.navArgs
import com.redron.R
import com.redron.domain.entity.Joke
import com.redron.data.datasource.remote.RetrofitInstance
import com.redron.data.datasource.local.LocalJokesDataSourceImpl
import com.redron.data.datasource.remote.RemoteJokesDataSourceImpl
import com.redron.data.datasource.local.JokesDatabase
import com.redron.data.repository.JokesRepository
import com.redron.databinding.FragmentJokeDetailsBinding
import com.redron.domain.usecases.GetJokeUseCase
import kotlinx.coroutines.launch

class JokeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentJokeDetailsBinding

    private val viewModel: JokeDetailsViewModel by viewModels {
        ViewModelProvider.Factory.from(
            ViewModelInitializer(
                clazz = JokeDetailsViewModel::class.java,
                initializer = {
                    val repository = JokesRepository(
                        RemoteJokesDataSourceImpl(RetrofitInstance.retrofitClient),
                        LocalJokesDataSourceImpl(JokesDatabase.INSTANCE)
                    )
                    JokeDetailsViewModel(
                        GetJokeUseCase(repository)
                    )
                }
            )
        )
    }
    private val args: JokeDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_joke_details, container, false)
        binding = FragmentJokeDetailsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadSingleJoke(args.jokeID)
    }

    private fun initViewModel() {
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
}