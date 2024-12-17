package com.redron.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val uuid: String,
    @ColumnInfo("setup")
    val setup: String,
    @ColumnInfo("delivery")
    val delivery: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("isFromNet")
    val isFromNet: Boolean = true,
    @ColumnInfo("isFavorite")
    val isFavorite: Boolean = false
)
