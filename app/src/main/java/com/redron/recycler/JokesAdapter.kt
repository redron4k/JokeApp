package com.redron.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.redron.data.Joke
import com.redron.databinding.JokeItemBinding
import com.redron.recycler.util.JokeDiffUtilCallback

class JokesAdapter : RecyclerView.Adapter<JokesViewHolder>() {

    private var data = emptyList<Joke>()

    fun setNewData(newData: List<Joke>) {
        val diffUtilCallback = JokeDiffUtilCallback(data, newData)
        val calculatedDiff = DiffUtil.calculateDiff(diffUtilCallback)
        data = newData
        calculatedDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)

        return JokesViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(data[position])
    }
}