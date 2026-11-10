package com.example.recordatoriosapp


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime


// Administra el estado del formulario de recordatorios
class FormularioViewModel : ViewModel() {

    // Estado interno que puede modificarse
    private val _uiState = MutableStateFlow(FormularioUiState())

    // Estado de solo lectura que observará la pantalla
    val uiState: StateFlow<FormularioUiState> = _uiState.asStateFlow()


    // Actualiza y valida el título
    fun onTituloChange(nuevoTitulo: String) {
        _uiState.update { estadoActual ->
            validarFormulario(
                estadoActual.copy(titulo = nuevoTitulo)
            )
        }
    }

    // Actualiza y valida la descripción
    fun onDescripcionChange(nuevaDescripcion: String) {
        _uiState.update { estadoActual ->
            validarFormulario(
                estadoActual.copy(descripcion = nuevaDescripcion)
            )
        }
    }

    // Actualiza y valida la categoría seleccionada
    fun onCategoriaChange(nuevaCategoria: Categoria) {
        _uiState.update { estadoActual ->
            validarFormulario(
                estadoActual.copy(categoria = nuevaCategoria)
            )
        }
    }

    // Actualiza y valida la prioridad seleccionada
    fun onPrioridadChange(nuevaPrioridad: Prioridad) {
        _uiState.update { estadoActual ->
            validarFormulario(
                estadoActual.copy(prioridad = nuevaPrioridad)
            )
        }
    }

    // Comprueba los datos y guarda un nuevo recordatorio
    fun guardar() {
        val estadoValidado = validarFormulario(_uiState.value)
        _uiState.value = estadoValidado

        // Evita guardar datos incorrectos
        if (!estadoValidado.puedeGuardar) return

        _uiState.update {
            it.copy(guardando = true, puedeGuardar = false)
        }

        viewModelScope.launch {
            try {
                // Genera un identificador nuevo
                val nuevoId = (
                        RepositorioEnMemoria.recordatorios.value
                            .maxOfOrNull { it.id } ?: 0
                        ) + 1

                // Construye el recordatorio
                val nuevoRecordatorio = Recordatorio(
                    id = nuevoId,
                    titulo = estadoValidado.titulo.trim(),
                    descripcion = estadoValidado.descripcion,
                    categoria = requireNotNull(estadoValidado.categoria),
                    prioridad = requireNotNull(estadoValidado.prioridad),
                    estado = EstadoRecordatorio.Pendiente,
                    fechaVencimiento = LocalDateTime.now()
                )

                // Lo agrega al repositorio en memoria
                RepositorioEnMemoria.agregar(nuevoRecordatorio)

                _uiState.update {
                    it.copy(
                        guardando = false,
                        guardadoExitoso = true
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        guardando = false,
                        errorGeneral = "No se pudo guardar el recordatorio"
                    )
                }
            }
        }
    }
    // Limpia el formulario para crear otro recordatorio
    fun reiniciarFormulario() {
        _uiState.value = FormularioUiState()
    }

// Comprueba si los datos del formulario son válidos
private fun validarFormulario(estado: FormularioUiState): FormularioUiState {

        val tituloValido = estado.titulo.trim().length >= 4
        val descripcionValida = estado.descripcion.length <= 200
        val categoriaValida = estado.categoria != null
        val prioridadValida = estado.prioridad != null

        return estado.copy(
            errorTitulo = if (tituloValido) null
            else "El título debe tener al menos 4 caracteres",

            errorDescripcion = if (descripcionValida) null
            else "La descripción no puede superar los 200 caracteres",

            errorCategoria = if (categoriaValida) null
            else "Seleccioná una categoría",

            errorPrioridad = if (prioridadValida) null
            else "Seleccioná una prioridad",

            puedeGuardar = tituloValido &&
                    descripcionValida &&
                    categoriaValida &&
                    prioridadValida &&
                    !estado.guardando
        )
    }


}
