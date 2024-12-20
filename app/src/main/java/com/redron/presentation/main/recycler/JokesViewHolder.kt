package com.redron.presentation.main.recycler

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.redron.domain.entity.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.R

class JokesViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (String) -> Unit,
    private val favClickListener: (Joke) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.textViewQuestion.text = joke.jokeQuestion
        binding.textViewAnswer.text = joke.jokeAnswer
        binding.textViewCategory.text = joke.category
        binding.textViewFromNet.text = if (joke.isFromNet) {
            binding.root.context.getString(R.string.is_from_net_true)
        } else {
            binding.root.context.getString(R.string.is_from_net_false)
        }
        setImageRes(joke)

        binding.root.setOnClickListener {
            clickListener(joke.uuid)
        }

        binding.imageButton.setOnClickListener {
            setImageRes(joke)
            favClickListener(joke)
        }
    }

    private fun setImageRes(joke: Joke) {
        if (joke.isFavorite) {
            binding.imageButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    binding.root.context, R.mipmap.ic_btn_star_on
                )
            )
        } else {
            binding.imageButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    binding.root.context, R.mipmap.ic_btn_star_off
                )
            )
        }
    }
}
