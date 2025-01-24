package com.redron.presentation.main.recycler

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.redron.R
import com.redron.databinding.JokeItemBinding
import com.redron.domain.entity.Joke

class JokesViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (String) -> Unit,
    private val favClickListener: (Joke) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        with(binding) {
            textViewQuestion.text = joke.jokeQuestion
            textViewAnswer.text = joke.jokeAnswer
            textViewCategory.text = joke.category
            textViewFromNet.text = if (joke.isFromNet) {
                root.context.getString(R.string.is_from_net_true)
            } else {
                root.context.getString(R.string.is_from_net_false)
            }
            setImageRes(joke)

            root.setOnClickListener {
                clickListener(joke.uuid)
            }

            imageButton.setOnClickListener {
                setImageRes(joke)
                favClickListener(joke)
            }
        }

    }

    private fun JokeItemBinding.setImageRes(joke: Joke) {
        if (joke.isFavorite) {
            imageButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    root.context, R.drawable.ic_btn_favorite_on_green_100
                )
            )
        } else {
            imageButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    root.context, R.drawable.ic_btn_favorite_off_green_100
                )
            )
        }
    }
}
