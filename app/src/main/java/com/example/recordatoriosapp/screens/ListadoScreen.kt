package com.example.recordatoriosapp.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.Recordatorio

// Pantalla que muestra todos los recordatorios
@Composable
fun ListadoScreen(
    recordatorios: List<Recordatorio>,
    onCrearRecordatorio: () -> Unit,
    onRecordatorioClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearRecordatorio) {
                Text("+")
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item {
                Text(
                    text = "Mis recordatorios",
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Muestra un mensaje cuando no existen recordatorios
            if (recordatorios.isEmpty()) {
                item {
                    Text(
                        text = "Todavía no hay recordatorios. Tocá + para crear el primero.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            items(recordatorios, key = { it.id }) { recordatorio ->
                Card(
                    onClick = { onRecordatorioClick(recordatorio.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = recordatorio.titulo)
                        Text(text = "Categoría: ${recordatorio.categoria.etiqueta}")
                        Text(text = "Prioridad: ${recordatorio.prioridad.etiqueta}")
                        Text(text = "Estado: ${recordatorio.estado.etiqueta}")
                    }
                }
            }
        }
    }
}
