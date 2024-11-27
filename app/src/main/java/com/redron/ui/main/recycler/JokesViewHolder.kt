package com.redron.ui.main.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.redron.data.Joke
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
            "Из сети" // context.getString(R.string.is_from_net_true)
        else
            "Локально" // context.getString(R.string.is_from_net_false)
        binding.root.setOnClickListener {
            clickListener(joke.uuid)
        }
    }
}
