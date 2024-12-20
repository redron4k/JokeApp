package com.redron.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokesTemp")
data class JokeTempEntity(
    @PrimaryKey()
    @ColumnInfo("id")
    val uuid: String,
    @ColumnInfo("setup")
    val setup: String,
    @ColumnInfo("delivery")
    val delivery: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("dumpTime")
    val dumpTime: Long,
)
