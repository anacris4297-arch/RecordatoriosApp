package com.example.recordatoriosapp


data class FormularioUiState(
    // Datos ingresados por el usuario
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,
    val prioridad: Prioridad? = null,

    // Mensajes de validación
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorCategoria: String? = null,
    val errorPrioridad: String? = null,

    // Estado del proceso de guardado
    val puedeGuardar: Boolean = false,
    val guardando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val errorGeneral: String? = null
)
