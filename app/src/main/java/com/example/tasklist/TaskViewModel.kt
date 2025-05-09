package com.example.tasklist

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.filter
import kotlin.collections.map

class TaskViewModel : ViewModel() {
    private val _taskListUiState = MutableStateFlow(TaskListUiState())
    val taskListUiState: StateFlow<TaskListUiState> = _taskListUiState.asStateFlow()

    private val _insertEditUiState = MutableStateFlow(InsertEditTaskUiState())
    val insertEditUiState: StateFlow<InsertEditTaskUiState> = _insertEditUiState.asStateFlow()

    private val _currentRoute = MutableStateFlow(AppScreens.TaskList.name)
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    fun onTaskDoneChange(task: Task, checked: Boolean) {
        _taskListUiState.value = _taskListUiState.value.copy(tasks = _taskListUiState.value.tasks.map {
            if (it == task) it.copy(isCompleted = checked) else it
        }.toMutableList())
    }

    fun onTaskDelete(task: Task) {
        _taskListUiState.value = _taskListUiState.value.copy(
            tasks = _taskListUiState.value.tasks.filter { it != task }.toMutableList()
        )
    }

    fun insertTask(task: Task) {
        _taskListUiState.value = _taskListUiState.value.copy(
            tasks = _taskListUiState.value.tasks + task
        )
    }

    fun updateTask(task: Task) {
        _taskListUiState.value = _taskListUiState.value.copy(
            tasks = _taskListUiState.value.tasks.map {
                if (it == _insertEditUiState.value.taskToEdit) task else it
            }
        )
    }

    fun onNameFilterChange(nameFilter: String) {
        _taskListUiState.value = _taskListUiState.value.copy(nameFilter = nameFilter)
        Log.d("TaskViewModel", "onNameFilterChange: ${_taskListUiState.value.filteredTasks}")
    }

    fun startInsertTask() {
        _currentRoute.value = AppScreens.InsertEditTask.name
        _insertEditUiState.value = InsertEditTaskUiState()
    }

    fun startEditTask(task: Task) {
        _currentRoute.value = AppScreens.InsertEditTask.name
        _insertEditUiState.value = InsertEditTaskUiState(
            name = task.name,
            description = task.description,
            icon = task.icon,
            taskToEdit = task
        )
    }

    fun onTaskNameChange(name: String) {
        _insertEditUiState.value = _insertEditUiState.value.copy(name = name)
    }

    fun onTaskDescriptionChange(description: String) {
        _insertEditUiState.value = _insertEditUiState.value.copy(description = description)
    }

    fun onTaskCategoryIconChange(@DrawableRes icon: Int) {
        _insertEditUiState.value = _insertEditUiState.value.copy(icon = icon)
    }

    fun onBackClick() {
        _currentRoute.value = AppScreens.TaskList.name
    }

    fun saveTask() {
        _currentRoute.value = AppScreens.TaskList.name
        val state = _insertEditUiState.value
        val task = state.taskToEdit?.copy(
            name = state.name,
            description = state.description,
            icon = state.icon
        ) ?: Task(
            name = state.name,
            description = state.description,
            icon = state.icon
        )

        if (state.taskToEdit != null) {
            updateTask(task)
        } else {
            insertTask(task)
        }
        _insertEditUiState.value = InsertEditTaskUiState() // reset
    }
}