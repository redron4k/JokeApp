package com.redron.presentation.main.recycler

import androidx.recyclerview.widget.RecyclerView
import com.redron.domain.entity.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.R

class JokesViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.textViewQuestion.text = joke.jokeQuestion
        binding.textViewAnswer.text = joke.jokeAnswer
        binding.textViewCategory.text = joke.category
        binding.textViewFromNet.text = if (joke.isFromNet)
            binding.root.context.getString(R.string.is_from_net_true)
        else
            binding.root.context.getString(R.string.is_from_net_false)
        binding.root.setOnClickListener {
            clickListener(joke.uuid)
        }
    }
}
