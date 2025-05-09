package com.example.tasklist

import androidx.annotation.DrawableRes

data class InsertEditTaskUiState(
    val name: String = "",
    val description: String = "",
    @DrawableRes val icon: Int = R.drawable.reminder,
    val taskToEdit: Task? = null
)