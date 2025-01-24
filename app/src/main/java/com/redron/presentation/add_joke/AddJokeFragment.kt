package com.redron.presentation.add_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.redron.App
import com.redron.R
import com.redron.domain.entity.Joke
import com.redron.databinding.FragmentAddJokeBinding
import com.redron.presentation.main.JokesListViewModelFactory
import com.redron.presentation.main.JokesListViewModel
import javax.inject.Inject


class AddJokeFragment : Fragment(R.layout.fragment_add_joke) {

    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: JokesListViewModelFactory

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

        _binding = FragmentAddJokeBinding.bind(view)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}