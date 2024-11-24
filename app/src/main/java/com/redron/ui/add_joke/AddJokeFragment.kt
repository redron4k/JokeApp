package com.redron.ui.add_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.findNavController
import com.redron.R
import com.redron.data.Joke
import com.redron.data.JokesGenerator
import com.redron.databinding.FragmentAddJokeBinding
import com.redron.ui.main.JokesListViewModel


class AddJokeFragment : Fragment(R.layout.fragment_add_joke) {

    private lateinit var binding: FragmentAddJokeBinding

    private val viewModel: JokesListViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = {
            ViewModelProvider.Factory.from(
                ViewModelInitializer(
                    clazz = JokesListViewModel::class.java,
                    initializer = {
                        JokesListViewModel(
                            JokesGenerator
                        )
                    }
                )
            )
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddJokeBinding.bind(view)
        binding.button.setOnClickListener {
            val category = binding.editTextCategory.text.toString()
            val question = binding.editTextQuestion.text.toString()
            val answer = binding.editTextAnswer.text.toString()

            if (category.isNotBlank() and question.isNotBlank() and answer.isNotBlank()) {
                viewModel.addJoke(Joke(question, answer, category, fromNet = false))
                Toast.makeText(activity, "Анекдот добавлен", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}