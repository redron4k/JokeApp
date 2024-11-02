package com.redron.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.redron.data.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.recycler.util.JokeItemCallback

class JokesListAdapter(itemCallback: JokeItemCallback) :
    ListAdapter<Joke, JokesViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)

        return JokesViewHolder(binding)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}