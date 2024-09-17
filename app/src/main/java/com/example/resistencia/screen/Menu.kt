package com.example.resistencia.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import com.example.resistencia.R
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Interfaz() {
    val context = LocalContext.current

    var banda1 by remember { mutableStateOf<Color?>(null) }
    var banda2 by remember { mutableStateOf<Color?>(null) }
    var multiplicador by remember { mutableStateOf<Color?>(null) }
    var tolerancia by remember { mutableStateOf("±1%") }
    var resistencia by remember { mutableStateOf("0 Ω") }

    var isExpandedBanda1 by remember { mutableStateOf(false) }
    var isExpandedBanda2 by remember { mutableStateOf(false) }
    var isExpandedMultiplicador by remember { mutableStateOf(false) }
    var isExpandedTolerancia by remember { mutableStateOf(false) }

    // Lista de colores y tolerancias
    val colores = listOf(
        Color.Black to "Negro - 0",
        Color(0xFF6F4F28) to "Marrón - 1",
        Color.Red to "Rojo - 2",
        Color(0xFFFFA500) to "Naranja - 3",
        Color.Yellow to "Amarillo - 4",
        Color.Green to "Verde - 5",
        Color.Blue to "Azul - 6",
        Color(0xFF8A2BE2) to "Violeta - 7",
        Color.Gray to "Gris - 8",
        Color.White to "Blanco - 9"
    )
    val coloresMultiplicador = listOf(
        Color.Black to "Negro - ×1",
        Color(0xFF6F4F28) to "Marrón - ×10",
        Color.Red to "Rojo - ×100",
        Color(0xFFFFA500) to "Naranja - ×1,000",
        Color.Yellow to "Amarillo - ×10,000",
        Color.Green to "Verde - ×100,000",
        Color.Blue to "Azul - ×1,000,000",
        Color(0xFFD4AF37) to "Dorado - ×0.1",
        Color(0xFFC0C0C0) to "Plateado - ×0.01"
    )
    val tolerancias = listOf(
        Color(0xFF6F4F28) to "±1%",
        Color.Red to "±2%",
        Color.Green to "±0.5%",
        Color.Blue to "±0.25%",
        Color(0xFF8A2BE2) to "±0.1%",
        Color.Gray to "±0.05%",
        Color(0xFFD4AF37) to "±5%",
        Color(0xFFC0C0C0) to "±10%"
    )

    // Función para calcular la resistencia
    fun calcularResistencia() {
        val valorBanda1 = colores.indexOfFirst { it.first == banda1 } * 10
        val valorBanda2 = colores.indexOfFirst { it.first == banda2 }
        val valorMultiplicador = 10.0.pow(coloresMultiplicador.indexOfFirst { it.first == multiplicador }.toDouble())
        val resultado = (valorBanda1 + valorBanda2) * valorMultiplicador
        resistencia = "$resultado Ω $tolerancia"
    }

    // Función para limpiar las selecciones
    fun limpiarSelecciones() {
        banda1 = null
        banda2 = null
        multiplicador = null
        tolerancia = "±1%"
        resistencia = "0 Ω"
    }

    // Caja general que contiene todos los elementos
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.imagen), // Reemplaza con tu imagen
            contentDescription = "Fondo de pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna para alinear los menús uno debajo del otro
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
                .shadow(8.dp, RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Calculadora de Resistencias",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Selector de Banda 1
            DropdownSelector(
                label = "Selecciona Banda 1",
                selectedValue = banda1?.let { colorToLabel(it, colores) } ?: "Selecciona Banda 1",
                options = colores.map { it.second },
                isExpanded = isExpandedBanda1,
                onExpandedChange = { isExpandedBanda1 = it },
                onValueChange = { label ->
                    banda1 = labelToColor(label, colores)
                    calcularResistencia()
                },
                colorList = colores
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Banda 2
            DropdownSelector(
                label = "Selecciona Banda 2",
                selectedValue = banda2?.let { colorToLabel(it, colores) } ?: "Selecciona Banda 2",
                options = colores.map { it.second },
                isExpanded = isExpandedBanda2,
                onExpandedChange = { isExpandedBanda2 = it },
                onValueChange = { label ->
                    banda2 = labelToColor(label, colores)
                    calcularResistencia()
                },
                colorList = colores
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Multiplicador
            DropdownSelector(
                label = "Selecciona Multiplicador",
                selectedValue = multiplicador?.let { colorToLabel(it, coloresMultiplicador) } ?: "Selecciona Multiplicador",
                options = coloresMultiplicador.map { it.second },
                isExpanded = isExpandedMultiplicador,
                onExpandedChange = { isExpandedMultiplicador = it },
                onValueChange = { label ->
                    multiplicador = labelToColor(label, coloresMultiplicador)
                    calcularResistencia()
                },
                colorList = coloresMultiplicador
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Tolerancia
            DropdownSelector(
                label = "Selecciona Tolerancia",
                selectedValue = tolerancia,
                options = tolerancias.map { it.second },
                isExpanded = isExpandedTolerancia,
                onExpandedChange = { isExpandedTolerancia = it },
                onValueChange = { selectedTolerancia ->
                    tolerancia = selectedTolerancia
                    calcularResistencia()
                },
                colorList = tolerancias.map { it.first to it.second } // Invertir el par
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el resultado solo si todas las opciones han sido seleccionadas
            if (banda1 != null && banda2 != null && multiplicador != null && tolerancia.isNotEmpty()) {
                Text("Resistencia: $resistencia", style = MaterialTheme.typography.headlineMedium)
            } else {
                Text("Por favor, selecciona todas las opciones.", style = MaterialTheme.typography.labelMedium, color = Color.Blue)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para limpiar las selecciones
            Button(
                onClick = { limpiarSelecciones() },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(Color(0xFF6200EE), RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                Text("Limpiar Selecciones", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    selectedValue: String,
    options: List<String>,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    colorList: List<Pair<Color, String>> // Lista de pares Color y String
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange(!isExpanded) }
    ) {
        TextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .background(Color(0xFFEDEDED), RoundedCornerShape(12.dp))
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .padding(8.dp)
                .height(56.dp)
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val color = labelToColor(option, colorList)
                            if (color != Color.Transparent) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(color, shape = RoundedCornerShape(4.dp))
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option)
                        }
                    },
                    onClick = {
                        onValueChange(option)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

// Función para convertir una etiqueta en un color
fun labelToColor(label: String, colorList: List<Pair<Color, String>>): Color {
    return colorList.firstOrNull { it.second == label }?.first ?: Color.Transparent
}

// Función para convertir un color en una etiqueta
fun colorToLabel(color: Color, colorList: List<Pair<Color, String>>): String {
    return colorList.firstOrNull { it.first == color }?.second ?: "Desconocido"
}
