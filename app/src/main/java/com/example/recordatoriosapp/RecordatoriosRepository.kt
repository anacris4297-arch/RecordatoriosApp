package com.example.recordatoriosapp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Define las operaciones disponibles para los recordatorios
interface RecordatoriosRepository {
    val recordatorios: StateFlow<List<Recordatorio>>

    fun agregar(recordatorio: Recordatorio)

    fun buscarPorId(id: Int): Recordatorio?
}

// Guarda los recordatorios temporalmente en memoria
object RepositorioEnMemoria : RecordatoriosRepository {

    // Comenzamos con los recordatorios de la API 1
    private val _recordatorios =
        MutableStateFlow(recordatoriosSimulados.toList())

    override val recordatorios: StateFlow<List<Recordatorio>> =
        _recordatorios.asStateFlow()

    // Agrega un nuevo recordatorio al listado
    override fun agregar(recordatorio: Recordatorio) {
        _recordatorios.update { actuales ->
            actuales + recordatorio
        }
    }

    // Busca un recordatorio utilizando su identificador
    override fun buscarPorId(id: Int): Recordatorio? {
        return _recordatorios.value.firstOrNull {
            it.id == id
        }
    }
}
