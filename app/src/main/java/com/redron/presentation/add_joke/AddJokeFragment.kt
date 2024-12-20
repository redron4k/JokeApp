package com.redron.presentation.add_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.findNavController
import com.redron.App
import com.redron.R
import com.redron.data.datasource.local.CacheJokesDataSourceImpl
import com.redron.domain.entity.Joke
import com.redron.data.datasource.remote.RetrofitInstance
import com.redron.data.datasource.local.LocalJokesDataSourceImpl
import com.redron.data.datasource.remote.RemoteJokesDataSourceImpl
import com.redron.data.datasource.local.JokesDatabase
import com.redron.data.repository.JokesRepositoryImpl
import com.redron.databinding.FragmentAddJokeBinding
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.LoadJokesFromCacheUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import com.redron.presentation.main.JokeListViewModelFactory
import com.redron.presentation.main.JokesListViewModel
import javax.inject.Inject


class AddJokeFragment : Fragment(R.layout.fragment_add_joke) {

    private lateinit var binding: FragmentAddJokeBinding

    @Inject
    lateinit var viewModelFactory: JokeListViewModelFactory

    private val viewModel: JokesListViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { viewModelFactory },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddJokeBinding.bind(view)
        binding.button.setOnClickListener {
            val category = binding.editTextCategory.text.toString()
            val question = binding.editTextQuestion.text.toString()
            val answer = binding.editTextAnswer.text.toString()

            if (category.isNotBlank() and question.isNotBlank() and answer.isNotBlank()) {
                viewModel.addNewJoke(Joke(question, answer, category, isFromNet = false))
                Toast.makeText(activity, "Анекдот добавлен", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}