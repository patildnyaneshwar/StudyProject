package com.project.study.data.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class PhotosTable(
    @NotNull @PrimaryKey val id: String,
    val created_at: String,
    val updated_at: String,
    val categories: String,
    val likes: Int,
    val urls: String
)