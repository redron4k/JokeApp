package com.redron.presentation.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.redron.domain.entity.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.presentation.main.recycler.util.JokeItemCallback

class JokesListAdapter(
    private val clickListener: (String) -> Unit,
    private val favClickListener: (Joke) -> Unit
) :
    ListAdapter<Joke, JokesViewHolder>(JokeItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)
        return JokesViewHolder(binding, clickListener, favClickListener)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}