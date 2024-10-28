package com.redron.recycler

import androidx.recyclerview.widget.RecyclerView
import com.redron.data.Joke
import com.redron.databinding.JokeItemBinding

class JokesViewHolder(val binding: JokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.textViewQuestion.text = joke.jokeQuestion
        binding.textViewAnswer.text = joke.jokeAnswer
        binding.textViewCategory.text = joke.category
    }
}