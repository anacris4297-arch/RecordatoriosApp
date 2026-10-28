package com.example.recordatoriosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recordatoriosapp.ui.theme.RecordatoriosAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecordatoriosAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Organiza los elementos verticalmente
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    )  {
        Text(text = "Mis recordatorios")

        // Resumen de las tareas
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Total de tareas: ${recordatoriosSimulados.size}")
                Text(text = "Pendientes: ${obtenerPendientes().size}")
            }
        }


        // Recorre y muestra los recordatorios simulados

        recordatoriosSimulados.forEach { recordatorio ->
            // Creamos una tarjeta para cada recordatorio
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = recordatorio.titulo)
                    Text(text = "Categoría: ${recordatorio.categoria.etiqueta}")

                    Text(
                        text = "Prioridad: ${recordatorio.prioridad.etiqueta}",
                        color = when (recordatorio.prioridad) {
                            Prioridad.Alta -> Color(0xFFC62828)
                            Prioridad.Media -> Color(0xFFB26A00)
                            Prioridad.Baja -> Color(0xFF2E7D32)
                        }
                    )


                    Text(
                        text = "Estado: ${recordatorio.estado.etiqueta}",
                        color = when (recordatorio.estado) {
                            EstadoRecordatorio.Pendiente -> Color(0xFFE67E22)
                            EstadoRecordatorio.EnCurso -> Color(0xFF1565C0)
                            EstadoRecordatorio.Completado -> Color(0xFF2E7D32)
                        }
                    )

                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecordatoriosAppTheme {
        Greeting("Android")
    }
}