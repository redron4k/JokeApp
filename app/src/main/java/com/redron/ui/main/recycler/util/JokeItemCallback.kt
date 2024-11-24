package com.redron.ui.main.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.redron.data.Joke

object JokeItemCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}