package com.example.recordatoriosapp

import java.time.LocalDateTime
import androidx.compose.ui.graphics.Color
val recordatoriosSimulados = listOf(
    Recordatorio(
        id = 1,
        titulo = "Estudiar Programación Mobile",
        descripcion = "Repasar la API 1",
        categoria = Categoria.Estudio,
        prioridad = Prioridad.Alta,
        estado = EstadoRecordatorio.Pendiente,
        fechaVencimiento = LocalDateTime.of(2026, 11, 5, 18, 0)
    ),

    Recordatorio(
        id = 2,
        titulo = "Comprar medicamentos",
        descripcion = "Pasar por la farmacia",
        categoria = Categoria.Salud,
        prioridad = Prioridad.Media,
        estado = EstadoRecordatorio.Pendiente,
        fechaVencimiento = LocalDateTime.of(2026, 11, 6, 10, 0)
    ),
    Recordatorio(
        id = 3,
        titulo = "Preparar presupuesto",
        descripcion = "Enviar presupuesto al cliente",
        categoria = Categoria.Trabajo,
        prioridad = Prioridad.Alta,
        estado = EstadoRecordatorio.EnCurso,
        fechaVencimiento = LocalDateTime.of(2026, 11, 7, 15, 0)
    ),

    Recordatorio(
        id = 4,
        titulo = "Organizar documentos",
        descripcion = "Ordenar los documentos personales",
        categoria = Categoria.Personal,
        prioridad = Prioridad.Baja,
        estado = EstadoRecordatorio.Completado,
        fechaVencimiento = LocalDateTime.of(2026, 11, 8, 12, 0)
    ),


    Recordatorio(
        id = 5,
        titulo = "Repasar Kotlin",
        descripcion = "Estudiar funciones y clases",
        categoria = Categoria.Estudio,
        prioridad = Prioridad.Media,
        estado = EstadoRecordatorio.Pendiente,
        fechaVencimiento = LocalDateTime.of(2026, 11, 9, 17, 0)
    )

)



// Obtener las categorías sin repetir
val categoriasSinRepetir = recordatoriosSimulados
    .map { it.categoria }
    .toSet()

// Asociar cada categoría con un color
val coloresCategorias = mapOf(
    Categoria.Personal to Color(0xFF4CAF50),
    Categoria.Trabajo to Color(0xFF9C27B0),
    Categoria.Salud to Color(0xFF009688),
    Categoria.Estudio to Color(0xFFFF9800)
)


// Filtrar recordatorios por estado
fun filtrarPorEstado(estado: EstadoRecordatorio): List<Recordatorio> {
    return recordatoriosSimulados.filter {
        it.estado == estado
    }
}

// Filtrar recordatorios por prioridad
fun filtrarPorPrioridad(prioridad: Prioridad): List<Recordatorio> {
    return recordatoriosSimulados.filter {
        it.prioridad == prioridad
    }
}

// Obtener solamente los recordatorios pendientes
fun obtenerPendientes(): List<Recordatorio> {
    return filtrarPorEstado(EstadoRecordatorio.Pendiente)
}
