package com.nmt.nguyen.nasamarsphotos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nmt.nguyen.nasamarsphotos.databinding.FragmentPhotoDetailBinding
import com.nmt.nguyen.nasamarsphotos.databinding.PhotoSingleBinding
import com.nmt.nguyen.nasamarsphotos.network.MarsPhoto

class PhotoListAdapter(
    private val onItemClicked: (MarsPhoto) -> Unit
) : ListAdapter<MarsPhoto, PhotoListAdapter.ItemViewHolder>(DiffCallback) {

    object DiffCallback: DiffUtil.ItemCallback<MarsPhoto>() {

        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }

    class ItemViewHolder(var binding: PhotoSingleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: MarsPhoto) {
            binding.photo = item
            binding.executePendingBindings() // review
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(PhotoSingleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val selectedItem = getItem(position)
        holder.itemView.setOnClickListener() { onItemClicked(selectedItem) }
        holder.bindItem(selectedItem)
    }
}