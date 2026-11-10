package com.example.recordatoriosapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.Recordatorio
import androidx.compose.foundation.layout.statusBarsPadding


// Muestra la información completa de un recordatorio
@Composable
fun DetalleScreen(
    recordatorio: Recordatorio?,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (recordatorio == null) {
            Text("No se encontró el recordatorio")
        } else {
            Text("Detalle del recordatorio")
            Text("Título: ${recordatorio.titulo}")
            Text("Descripción: ${recordatorio.descripcion}")
            Text("Categoría: ${recordatorio.categoria.etiqueta}")
            Text("Prioridad: ${recordatorio.prioridad.etiqueta}")
            Text("Estado: ${recordatorio.estado.etiqueta}")
        }

        Button(onClick = onVolver) {
            Text("Volver al listado")
        }
    }
}
