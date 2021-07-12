package com.project.study.ui.photos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.project.study.BR
import com.project.study.R
import com.project.study.data.database.tables.PhotosTable
import com.project.study.data.model.PhotosDataClass
import com.project.study.data.model.Urls
import com.project.study.databinding.ItemPhotosBinding
import org.jetbrains.annotations.NotNull

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    private val TAG = "PhotosAdapter"

    private val diffCallback = object : DiffUtil.ItemCallback<PhotosTable>() {
        override fun areItemsTheSame(oldItem: PhotosTable, newItem: PhotosTable): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotosTable,
            newItem: PhotosTable
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PhotosTable>?) = differ.submitList(list)

    inner class PhotosViewHolder(@NotNull val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photos: PhotosTable) {
            Log.d(TAG, "bind:photos ${photos.urls}")
            binding.setVariable(BR.photos, photos)
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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}