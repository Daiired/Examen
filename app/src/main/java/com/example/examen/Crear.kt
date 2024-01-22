package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.db.AppDatabase
import com.example.examen.db.Lugares
import com.example.examen.ui.theme.ExamenTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Crear : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crear2 = "Crear"
        setContent {
            CrearLugarUI(crear2)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearLugarUI(crear:String){
    var nombre by remember { mutableStateOf("") }
    var orden by remember { mutableStateOf("") }
    var urlIMG by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf("") }
    var longitud by remember { mutableStateOf("") }
    var costoAlojamiento by remember { mutableStateOf("") }
    var costoTransporte by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    val contexto = LocalContext.current
    val alcanceCorrutina = rememberCoroutineScope()

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lugar", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Orden", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = orden,
            onValueChange = {orden = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Imagen Ref.", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = urlIMG,
            onValueChange = {urlIMG = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Latitud", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = latitud,
            onValueChange = {latitud = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Longitud", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = longitud,
            onValueChange = {longitud = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Costo Alojamiento", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = costoAlojamiento,
            onValueChange = {costoAlojamiento = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Costo Tralados", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = costoTransporte,
            onValueChange = {costoTransporte = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier= Modifier.height(20.dp))

        Text("Comentario", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        TextField(
            value = comentario,
            onValueChange = {comentario = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier= Modifier.height(20.dp))
        Button(onClick = {
            alcanceCorrutina.launch(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(contexto).lugaresDao()
                val nuevoLugar = Lugares(nombre = nombre, orden = orden.toInt(), urlIMG = urlIMG, latitud = latitud.toDouble(), longitud = longitud.toDouble(), costoAlojamiento = costoAlojamiento.toInt(), costoTransporte = costoTransporte.toInt(), comentario = comentario)
                val idGenerada = dao.insertar(nuevoLugar)
                val intent = Intent(contexto, MainActivity::class.java)
                contexto.startActivity(intent)
            }
        }) {
            Text("Guardar")
        }
    }
}