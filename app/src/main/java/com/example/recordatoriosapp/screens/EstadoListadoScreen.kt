package com.example.recordatoriosapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.ListadoUiState

// Componente reutilizable para comunicar estados del listado
@Composable
fun EstadoListadoScreen(
    estado: ListadoUiState,
    onReintentar: () -> Unit,
    onCrear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (estado) {
            ListadoUiState.Cargando -> {
                CircularProgressIndicator()
                Text("Cargando recordatorios...")
            }

            ListadoUiState.Vacio -> {
                Text("Todavía no hay recordatorios")
                Button(onClick = onCrear) {
                    Text("Crear recordatorio")
                }
            }

            is ListadoUiState.Error -> {
                Text(estado.mensaje)
                Button(onClick = onReintentar) {
                    Text("Reintentar")
                }
            }

            is ListadoUiState.ConDatos -> Unit
        }
    }
}
