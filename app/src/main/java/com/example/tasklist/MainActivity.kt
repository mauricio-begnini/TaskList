package com.example.tasklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasklist.ui.theme.TaskListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskListTheme {
                TaskApp()
            }
        }
    }
}

fun getTasks(): List<Task> {
    return listOf(
        Task("Assistir a um filme", "Ver um filme da lista de favoritos", R.drawable.leisure),
        Task(
            "Beber 2 litros de água",
            "Meta diária de hidratação",
            R.drawable.health,
            isCompleted = true
        ),
        Task("Passar roupa", "Passar as roupas limpas da semana", R.drawable.household),
        Task(
            "Comprar presente de aniversário",
            "Escolher e comprar presente para amigo(a)",
            R.drawable.shopping
        ),
        Task("Corrigir atividades", "Avaliar trabalhos enviados pelos alunos", R.drawable.work),
        Task(
            "Tomar remédio",
            "Tomar o medicamento prescrito pela manhã",
            R.drawable.health,
            isCompleted = true
        ),
        Task(
            "Organizar os armários",
            "Reorganizar os armários da cozinha e do quarto",
            R.drawable.household,
            isCompleted = true
        ),
        Task("Jogar videogame", "Relaxar jogando um pouco", R.drawable.leisure),
        Task(
            "Preparar apresentação",
            "Montar slides para a próxima reunião",
            R.drawable.work,
            isCompleted = true
        ),
        Task("Lavar a louça", "Lavar os pratos e talheres após as refeições", R.drawable.household),
        Task(
            "Comprar ração para o pet",
            "Comprar ração para o cachorro ou gato",
            R.drawable.shopping
        ),
        Task(
            "Responder e-mails",
            "Responder pendências e comunicações importantes",
            R.drawable.work
        ),
        Task(
            "Caminhar 30 minutos",
            "Fazer uma caminhada leve para manter a saúde",
            R.drawable.health
        ),
        Task(
            "Tocar violão",
            "Praticar 20 minutos de violão",
            R.drawable.leisure,
            isCompleted = true
        ),
        Task(
            "Repor itens de limpeza",
            "Comprar detergente, desinfetante e esponjas",
            R.drawable.shopping
        ),
        Task(
            "Limpar o banheiro",
            "Higienizar vaso sanitário, pia e chuveiro",
            R.drawable.household
        ),
        Task(
            "Fazer compras de supermercado",
            "Lista de itens para reabastecer a despensa",
            R.drawable.shopping
        ),
        Task("Participar de reunião online", "Reunião com equipe às 14h", R.drawable.reminder),
        Task(
            "Meditar por 10 minutos",
            "Sessão rápida de meditação e respiração",
            R.drawable.health
        ),
        Task("Ler um livro", "Avançar na leitura do livro atual", R.drawable.leisure)
    )
}

fun generateRandomTask(): Task {
    val names = listOf(
        "Caminhar 15 minutos",
        "Lavar o carro",
        "Fazer backup do celular",
        "Ver um documentário",
        "Comprar frutas",
        "Organizar documentos",
        "Enviar currículo",
        "Revisar anotações",
        "Fazer alongamento",
        "Tocar uma música nova"
    )

    val descriptions = listOf(
        "Tarefa simples e rápida",
        "Importante fazer ainda hoje",
        "Não esquecer!",
        "Boa para o final do dia",
        "Pode ser feita em 30 minutos",
        "Recomendada para manter a rotina"
    )

    val icons = listOf(
        R.drawable.health,
        R.drawable.household,
        R.drawable.leisure,
        R.drawable.shopping,
        R.drawable.work,
        R.drawable.reminder
    )

    return Task(
        name = names.random(),
        description = descriptions.random(),
        icon = icons.random(),
        isCompleted = listOf(true, false).random()
    )
}

@Composable
fun TaskApp(modifier: Modifier = Modifier) {
    var tasks by remember {
        mutableStateOf(getTasks().toMutableList())
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val newTask = generateRandomTask()
                tasks = (tasks + newTask).toMutableList()
            }) {
                Text(text = "+")
            }
        }
    ) { innerPadding ->
        TaskList(
            modifier = Modifier.padding(innerPadding),
            tasks = tasks,
            onTaskCheckedChange = { task, checked ->
                tasks = tasks.map {
                    if (it == task) it.copy(isCompleted = checked) else it
                }.toMutableList()
            },
            onTaskDelete = { task ->
                tasks = tasks.filter { it != task }.toMutableList()
            },
            onTaskEdit = { }
        )
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit,
    onTaskEdit: (Task) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            TaskEntry(
                task = task,
                onCheckedChange = { onTaskCheckedChange(task, !task.isCompleted) },
                onDeleteClick = { onTaskDelete(task) },
                onEditClick = { onTaskEdit(task) },
            )
        }
    }
}

@Composable
fun TaskEntry(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Icon(
            painter = painterResource(id = task.icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
        )

        IconButton(onClick = onDeleteClick) {
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = "Excluir tarefa",
                tint = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    TaskApp()
}