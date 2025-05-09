package com.example.tasklist

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

data class TaskListUiState(
    val nameFilter: String = "",
    val tasks: List<Task> = getTasks(),
){
    val filteredTasks: List<Task>
        get() = tasks.filter { it.name.contains(nameFilter, ignoreCase = true) }
}
