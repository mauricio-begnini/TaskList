package com.example.tasklist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp(
    modifier: Modifier = Modifier,
    appViewModel: TaskViewModel = viewModel()
) {

    val uiState = appViewModel.taskListUiState.collectAsState()
    val currentRoute = appViewModel.currentRoute.collectAsState()

    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {},
                actions = {
                    if (currentRoute.value == AppScreens.TaskList.name)
                        TextField(
                            value = uiState.value.nameFilter,
                            onValueChange = appViewModel::onNameFilterChange,
                            label = { Text(text = "Filtrar tarefas") },
                        )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (currentRoute.value == AppScreens.TaskList.name) {
                        appViewModel.startInsertTask()
                        navController.navigate(AppScreens.InsertEditTask.name)
                    } else if (currentRoute.value == AppScreens.InsertEditTask.name) {
                        appViewModel.saveTask()
                        appViewModel.onBackClick()
                        navController.popBackStack()
                    }
            }) {
                if (currentRoute.value == AppScreens.TaskList.name)
                    Text(text = "+")
                else if (currentRoute.value == AppScreens.InsertEditTask.name)
                    Text(text = "Salvar")
            }
        }
    ) { innerPadding ->
        navHost(
            modifier = Modifier.padding(innerPadding),
            viewModel = appViewModel,
            navController = navController
        )
    }
}

@Composable
fun navHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: TaskViewModel,
) {
    NavHost(navController = navController, startDestination = AppScreens.TaskList.name) {
        composable(AppScreens.TaskList.name) {
            val taskListUiState = viewModel.taskListUiState.collectAsState()
            TaskListScreen(
                modifier = modifier,
                uiState = taskListUiState.value,
                onCheckedChange = viewModel::onTaskDoneChange,
                onDelete = viewModel::onTaskDelete,
                onEditTask = { task: Task ->
                    viewModel.startEditTask(task)
                    navController.navigate(AppScreens.InsertEditTask.name)
                }
            )
        }

        composable(AppScreens.InsertEditTask.name) {
            val insertEditTaskUiState = viewModel.insertEditUiState.collectAsState()
            InsertOrEditTaskScreen(
                modifier = modifier,
                uiState = insertEditTaskUiState.value,
                onNameChange = viewModel::onTaskNameChange,
                onDescriptionChange = viewModel::onTaskDescriptionChange,
                onCategoryIconChange = viewModel::onTaskCategoryIconChange,
                onCancel = {
                    viewModel.onBackClick()
                    navController.popBackStack()
                }
            )
        }
    }
}

enum class AppScreens() {
    TaskList,
    InsertEditTask
}