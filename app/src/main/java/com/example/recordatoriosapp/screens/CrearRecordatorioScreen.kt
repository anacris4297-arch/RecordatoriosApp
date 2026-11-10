package com.example.recordatoriosapp.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment
import com.example.recordatoriosapp.Categoria
import com.example.recordatoriosapp.Prioridad
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.error

// Pantalla para ingresar los datos de un nuevo recordatorio
@Composable

fun CrearRecordatorioScreen(
titulo: String,
descripcion: String,
categoria: Categoria?,
prioridad: Prioridad?,
onTituloChange: (String) -> Unit,
onDescripcionChange: (String) -> Unit,
onCategoriaChange: (Categoria) -> Unit,
onPrioridadChange: (Prioridad) -> Unit,
puedeGuardar: Boolean,
guardando: Boolean,
onGuardar: () -> Unit,
onCancelar: () -> Unit,
modifier: Modifier = Modifier,
errorTitulo: String? = null,
errorDescripcion: String? = null,
errorCategoria: String? = null,
errorPrioridad: String? = null,

) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(text = "Nuevo recordatorio")

        // Campo reutilizable para el título
        CampoTextoRecordatorio(
            etiqueta = "Título",
            valor = titulo,
            onValorChange = onTituloChange,
            mensajeError = errorTitulo,
            unaLinea = true
        )

        // Campo reutilizable para la descripción
        CampoTextoRecordatorio(
            etiqueta = "Descripción",
            valor = descripcion,
            onValorChange = onDescripcionChange,
            mensajeError = errorDescripcion,
            lineasMinimas = 3
        )

// Selección de categoría
        Text(text = "Categoría")

        listOf(
            Categoria.Personal,
            Categoria.Trabajo,
            Categoria.Salud,
            Categoria.Estudio
        ).forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = categoria == opcion,
                    onClick = { onCategoriaChange(opcion) }
                )
                Text(text = opcion.etiqueta)
            }
        }

        if (errorCategoria != null) {
            Text(
                text = errorCategoria,
                color = MaterialTheme.colorScheme.error
            )
        }



// Selección de prioridad
        Text(text = "Prioridad")

        listOf(
            Prioridad.Baja,
            Prioridad.Media,
            Prioridad.Alta
        ).forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = prioridad == opcion,
                    onClick = { onPrioridadChange(opcion) }
                )
                Text(text = opcion.etiqueta)
            }
        }

        // Muestra un mensaje si falta seleccionar la prioridad
        if (errorPrioridad != null) {
            Text(
                text = errorPrioridad,
                color = MaterialTheme.colorScheme.error
            )
        }


// Botones para guardar o cancelar el recordatorio
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onGuardar,
                enabled = puedeGuardar && !guardando
            ) {
                Text(
                    text = if (guardando) "Guardando..." else "Guardar"
                )
            }

            OutlinedButton(
                onClick = onCancelar,
                enabled = !guardando
            ) {
                Text(text = "Cancelar")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun CrearRecordatorioPreview() {
    CrearRecordatorioScreen(
        titulo = "Estudiar Programación Mobile",
        descripcion = "Completar la API 2",
        categoria = Categoria.Estudio,
        prioridad = Prioridad.Alta,
        onTituloChange = {},
        onDescripcionChange = {},
        onCategoriaChange = {},
        onPrioridadChange = {},
        puedeGuardar = true,
        guardando = false,
        onGuardar = {},
        onCancelar = {}
    )


}

/**
 * Campo de texto reutilizable para el formulario.
 * Muestra el mensaje de validación cuando existe un error.
 */
@Composable
fun CampoTextoRecordatorio(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    mensajeError: String?,
    modifier: Modifier = Modifier,
    unaLinea: Boolean = false,
    lineasMinimas: Int = 1
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                mensajeError?.let { error(it) }
            },
        singleLine = unaLinea,
        minLines = lineasMinimas,
        isError = mensajeError != null
    )

    if (mensajeError != null) {
        Text(
            text = mensajeError,
            color = MaterialTheme.colorScheme.error
        )
    }
}


