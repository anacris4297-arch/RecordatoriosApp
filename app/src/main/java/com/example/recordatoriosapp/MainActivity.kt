
package com.example.recordatoriosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recordatoriosapp.screens.ListadoScreen
import com.example.recordatoriosapp.screens.CrearRecordatorioScreen
import com.example.recordatoriosapp.ui.theme.RecordatoriosAppTheme
import com.example.recordatoriosapp.screens.DetalleScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecordatoriosAppTheme {
                AplicacionRecordatorios()
            }
        }
    }
}

// Conecta las pantallas de nuestra aplicación
@Composable

fun AplicacionRecordatorios(
    formularioViewModel: FormularioViewModel = viewModel(),
    listadoViewModel: ListadoViewModel = viewModel()
)
 {
    // Controla la navegación entre pantallas
    val navController = rememberNavController()

    // Observa los datos actuales del formulario
    val estado by formularioViewModel.uiState
        .collectAsStateWithLifecycle()


     // Observa el estado del listado
     val estadoListado by listadoViewModel.uiState
         .collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "listado",
        modifier = Modifier.fillMaxSize()
    ) {


        // Primera pantalla: listado de recordatorios
        composable("listado") {


            when (val pantalla = estadoListado) {

                // Muestra un indicador mientras carga
                ListadoUiState.Cargando -> {
                    CircularProgressIndicator()
                }

                // Muestra el error y permite reintentar
                is ListadoUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(pantalla.mensaje)

                        Button(
                            onClick = {
                                listadoViewModel.reintentar()
                            }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                // Muestra el listado o el estado vacío
                else -> {
                    val lista = (
                            pantalla as? ListadoUiState.ConDatos
                            )?.recordatorios.orEmpty()

                    ListadoScreen(
                        recordatorios = lista,
                        onCrearRecordatorio = {
                            navController.navigate("crear")
                        },
                        onRecordatorioClick = { id ->
                            navController.navigate("detalle/$id")
                        }
                    )
                }
            }
        }

        // Tercera pantalla: detalle de un recordatorio
        composable("detalle/{id}") { entrada ->

            // Obtiene el identificador del recordatorio seleccionado
            val id = entrada.arguments
                ?.getString("id")
                ?.toIntOrNull()

            // Busca el recordatorio en nuestro repositorio
            val recordatorio = id?.let {
                RepositorioEnMemoria.buscarPorId(it)
            }

            // Muestra la pantalla de detalle
            DetalleScreen(
                recordatorio = recordatorio,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }


        // Segunda pantalla: crear un recordatorio
        composable("crear") {


            // Regresa al listado cuando el guardado fue exitoso
            LaunchedEffect(estado.guardadoExitoso) {
                if (estado.guardadoExitoso) {
                    formularioViewModel.reiniciarFormulario()
                    navController.popBackStack()
                }
            }

            Scaffold { innerPadding ->

                CrearRecordatorioScreen(
                    titulo = estado.titulo,
                    descripcion = estado.descripcion,
                    categoria = estado.categoria,
                    prioridad = estado.prioridad,

                    onTituloChange =
                        formularioViewModel::onTituloChange,

                    onDescripcionChange =
                        formularioViewModel::onDescripcionChange,

                    onCategoriaChange =
                        formularioViewModel::onCategoriaChange,

                    onPrioridadChange =
                        formularioViewModel::onPrioridadChange,

                    // Conecta el botón con la función de guardado
                    puedeGuardar = estado.puedeGuardar,
                    guardando = estado.guardando,
                    onGuardar = {
                        formularioViewModel.guardar()
                    },

                    onCancelar = {
                        formularioViewModel.reiniciarFormulario()
                        navController.popBackStack()
                    },


                    errorTitulo = estado.errorTitulo,
                    errorDescripcion = estado.errorDescripcion,
                    errorCategoria = estado.errorCategoria,
                    errorPrioridad = estado.errorPrioridad,

                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
