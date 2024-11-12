package com.redron.ui.main.recycler

import androidx.recyclerview.widget.RecyclerView
import com.redron.data.Joke
import com.redron.databinding.JokeItemBinding

class JokesViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.textViewQuestion.text = joke.jokeQuestion
        binding.textViewAnswer.text = joke.jokeAnswer
        binding.textViewCategory.text = joke.category
        binding.root.setOnClickListener {
            handlePersonClick(joke.id)
        }
    }


    private fun handlePersonClick(id: Int) {
        if (id != -1) {
            clickListener(id)
        }
    }
}
