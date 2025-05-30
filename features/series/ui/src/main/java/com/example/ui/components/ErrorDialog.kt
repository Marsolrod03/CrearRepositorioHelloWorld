package com.example.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.domain.AppError

@Composable
fun ErrorDialog(error: AppError, onDismiss: () -> Unit, onNavigateHome: () -> Unit) {
    if (error == null) return

    val message = when (error) {
        is AppError.NoInternet -> "No hay conexión a internet"
        is AppError.Unauthorized -> "No autorizado"
        is AppError.Forbidden -> "Prohibido"
        is AppError.BadRequest -> "Solicitud incorrecta"
        is AppError.NotFound -> "No encontrado"
        is AppError.ServerError -> "Error del servidor"
        is AppError.UnknownError -> error.messageError ?: "Error desconocido"
        else -> "Error desconocido"
    }
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Error", color = Color.Black) },
        text = { Text(message, color = Color.Black) },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                onNavigateHome()
            }) {
                Text("Volver al Inicio", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            })
            {
                Text("Aceptar", color = Color.Black)
            }
        }
    )
}


