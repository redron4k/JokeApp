package com.redron.ui.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redron.data.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.ui.main.recycler.util.JokeItemCallback

class JokesListAdapter(
    private val clickListener: (Int) -> Unit
) :
    ListAdapter<Joke, JokesViewHolder>(JokeItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)
        return JokesViewHolder(binding).apply {
            binding.root.setOnClickListener {
                handlePersonClick(bindingAdapterPosition)
            }
        }
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun handlePersonClick(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            clickListener(position)
        }
    }
}