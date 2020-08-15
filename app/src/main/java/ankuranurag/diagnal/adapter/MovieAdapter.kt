package ankuranurag.diagnal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ankuranurag.diagnal.databinding.ItemMovieBinding
import ankuranurag.diagnal.model.MovieData

/**
 * created by ankur on 15/8/20
 */
class MovieAdapter : ListAdapter<MovieData, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class MovieViewHolder(private val itemBinding: ItemMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(data: MovieData) {
            itemBinding.apply {
                movieData = data
                executePendingBindings()
            }
        }
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.name == newItem.name
    }
}