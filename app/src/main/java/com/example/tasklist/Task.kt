package com.example.tasklist

import androidx.annotation.DrawableRes

data class Task(
    val name: String,
    val description: String,
    @DrawableRes val icon: Int,
    val isCompleted: Boolean = false,
)
