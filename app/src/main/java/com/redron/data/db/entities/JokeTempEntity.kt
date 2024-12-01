package com.redron.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokesTemp")
data class JokeTempEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("setup")
    val setup: String,
    @ColumnInfo("delivery")
    val delivery: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("dumpTime")
    val dumpTime: Long
)
