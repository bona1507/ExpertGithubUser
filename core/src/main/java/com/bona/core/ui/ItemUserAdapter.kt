package com.bona.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bona.core.databinding.ItemUserBinding
import com.bona.core.utils.ItemUser

class ItemUserAdapter : ListAdapter<ItemUser, ItemUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((ItemUser) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemUser) {
            with(binding) {
                profilePict.load(user.avatarUrl)
                userName.text = user.login
            }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(getItem(position))
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemUser>() {
            override fun areItemsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean = oldItem.login == newItem.login
        }
    }
}
