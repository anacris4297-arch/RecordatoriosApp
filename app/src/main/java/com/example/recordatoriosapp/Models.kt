package com.example.recordatoriosapp
import java.time.LocalDateTime

sealed class Categoria(val etiqueta: String) {
    object Personal : Categoria("Personal")
    object Trabajo : Categoria("Trabajo")
    object Salud : Categoria("Salud")
    object Estudio : Categoria("Estudio")
}

sealed class Prioridad(val etiqueta: String) {
    object Baja : Prioridad("Baja")
    object Media : Prioridad("Media")
    object Alta : Prioridad("Alta")
}

sealed class EstadoRecordatorio(val etiqueta: String) {
    object Pendiente : EstadoRecordatorio("Pendiente")
    object EnCurso : EstadoRecordatorio("En curso")
    object Completado : EstadoRecordatorio("Completado")
}

sealed class EstadoAplicacion {
    object Cargando : EstadoAplicacion()
    object Lista : EstadoAplicacion()
    data class Error(val mensaje: String) : EstadoAplicacion()
}

data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val descripcion: String? = null
)
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio,
    val fechaVencimiento: LocalDateTime,
    val ubicacion: Ubicacion? = null,
    val aviso: LocalDateTime? = null,
    val sincronizado: Boolean = false
)