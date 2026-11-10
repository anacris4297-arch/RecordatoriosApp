package com.example.recordatoriosapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Posibles estados de la pantalla de listado
sealed interface ListadoUiState {
    data object Cargando : ListadoUiState
    data object Vacio : ListadoUiState

    data class ConDatos(
        val recordatorios: List<Recordatorio>
    ) : ListadoUiState

    data class Error(
        val mensaje: String
    ) : ListadoUiState
}

// Administra el estado del listado de recordatorios
class ListadoViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow<ListadoUiState>(ListadoUiState.Cargando)

    val uiState: StateFlow<ListadoUiState> =
        _uiState.asStateFlow()

    init {
        // Observa los cambios del repositorio
        viewModelScope.launch {
            RepositorioEnMemoria.recordatorios.collect { lista ->
                if (_uiState.value !is ListadoUiState.Error) {
                    _uiState.value = if (lista.isEmpty()) {
                        ListadoUiState.Vacio
                    } else {
                        ListadoUiState.ConDatos(lista)
                    }
                }
            }
        }
    }

    // Vuelve a consultar los recordatorios
    fun reintentar() {
        _uiState.value = ListadoUiState.Cargando

        val lista = RepositorioEnMemoria.recordatorios.value

        _uiState.value = if (lista.isEmpty()) {
            ListadoUiState.Vacio
        } else {
            ListadoUiState.ConDatos(lista)
        }
    }

    // Permite probar temporalmente la pantalla de error
    fun simularError() {
        _uiState.value = ListadoUiState.Error(
            "No se pudieron cargar los recordatorios"
        )
    }
}
