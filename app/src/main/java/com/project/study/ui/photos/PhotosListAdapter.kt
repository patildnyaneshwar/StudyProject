package com.project.study.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.study.BR
import com.project.study.R
import com.project.study.data.database.tables.PhotosTable
import com.project.study.databinding.ItemPhotosBinding
import com.project.study.utils.DiffCallback
import org.jetbrains.annotations.NotNull

/**
 * @see PhotosAdapter for some info
 * */
class PhotosListAdapter(
    val onItemClickListener: OnItemClickListener
) : ListAdapter<PhotosTable, PhotosListAdapter.PhotosViewHolder>(DiffCallback<PhotosTable>()) {

    interface OnItemClickListener {
        fun onItemClick(photos: PhotosTable)
    }

    inner class PhotosViewHolder(@NotNull val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photos: PhotosTable) {
            binding.setVariable(BR.photos, photos)
            binding.root.rootView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(photos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photos, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}