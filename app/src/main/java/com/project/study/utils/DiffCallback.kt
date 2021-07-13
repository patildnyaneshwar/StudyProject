package com.project.study.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.study.data.database.tables.PhotosTable

/**
 * DiffCallback is a Generic utility class that calculates the difference between two lists
 * and outputs a list of update operations that converts the first list into the second one.
 * */
class DiffCallback<T>: DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem is PhotosTable && newItem is PhotosTable){
            return (oldItem as PhotosTable).id == (newItem as PhotosTable).id
        }
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}