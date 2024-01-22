package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.examen.db.AppDatabase
import com.example.examen.db.Lugares
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch( Dispatchers.IO){
            val empleadosDao = AppDatabase.getInstance(this@MainActivity).lugaresDao()
        }

        val crear1 = "Crear"
        setContent {
            ListarLugaresUI(crear1)
        }
    }
}

@Composable

fun ListarLugaresUI(crear:String){
    var contexto = LocalContext.current
    val(lugares, setLugares) = remember { mutableStateOf(emptyList<Lugares>())}

    LaunchedEffect(Unit){
        val dao = AppDatabase.getInstance(contexto).lugaresDao()
        setLugares(dao.findAll())
    }


    if (lugares.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(text = "No hay elementos disponibles")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(onClick ={
                    val intent = Intent(contexto, Crear::class.java)
                    contexto.startActivity(intent)
                },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text("Crear")
                }
            }
        }


    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(lugares) { lugar ->
                LugaresItemUI(lugar){
                    setLugares(emptyList<Lugares>())
                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(onClick ={
                val intent = Intent(contexto, Crear::class.java)
                contexto.startActivity(intent)
            },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Crear")
            }
        }
    }
}

@Composable
fun LugaresItemUI(lugares: Lugares, onSave:()->Unit={} ){
    val contexto = LocalContext.current
    val alcanceCorrutina = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier.widthIn(min=100.dp, max=100.dp),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = lugares.urlIMG,
                contentDescription ="Porta Lugar: ${lugares.nombre}",
                modifier = Modifier.width(200.dp)
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column() {
            Text(lugares.nombre, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text("Costo  x nombre: ${lugares.costoAlojamiento}")
            Text("Traslado: ${lugares.costoAlojamiento}")
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Detalle Lugar",
                modifier = Modifier.clickable {
                    val intent = Intent(contexto, Detalle::class.java)
                    contexto.startActivity(intent)
                }
            )
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Eliminar Lugar",
                modifier = Modifier.clickable {
                    alcanceCorrutina.launch ( Dispatchers.IO ){
                        val dao = AppDatabase.getInstance(contexto).lugaresDao()
                        dao.eliminar( lugares )
                        onSave()
                    }
                }
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun LugaresItemUIPreview(){
    var lugares = Lugares(1,
        "Termas de Chillan",
        1,
        "https://www.chillanhouse.cl/wp-content/uploads/2020/01/termas-de-chillan.jpg",
        -36.904551,
        -71.5478645,
        50000,
        3000,
        "")
    LugaresItemUI(lugares)
}



