package com.protectly.pruebasllamadohttp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.protectly.pruebasllamadohttp.models.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ejecutamos el hilo de prueba! (comentada para hacer el ejemplo de corrutinas)
        //ejecucionHilo()

        ejemploCorrutinas()


    }



    private fun ejemploCorrutinas() {

        //creamos una solicitud a llamada a servicio y
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Iniciar una coroutine en el hilo principal
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Realizar una solicitud HTTP en segundo plano
                val user = withContext(Dispatchers.IO) {
                    //aqui va el nombre que solicitamos!
                    apiService.getUser("pvjaime")
                }

                // Actualizar la interfaz de usuario con el resultado

                Toast.makeText(this@MainActivity, "Nombre de usuario: ${user.login}\n" +
                        "Nombre: ${user.name}", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                // Manejar errores, como problemas de red
                Toast.makeText(this@MainActivity,"Error: ${e.message}", Toast.LENGTH_LONG).show()

            }
        }


    }

    private fun ejecucionHilo() {
        val  url = "https://www.google.com"

        //creamos un cliente que se va a encargar de haver la conexion al sitio web
        val client = OkHttpClient()
        //es la solicitud de la llamada al sitio web
        val request = okhttp3.Request.Builder().url(url).build()
        //ejecutamos la solicitud
        val response = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object :Callback {
            override fun onFailure(call: Call, e: IOException) {
                //Manejar errores de conexion
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    // Procesar los datos de la respuesta (responseData)
                    runOnUiThread {
                        // Actualizar la interfaz de usuario con los datos recibidos
                        Toast.makeText(this@MainActivity, "Carge la pagina", Toast.LENGTH_LONG).show()
                    }
                } else {
                    //maneja errores en la respuesta
                }
            }
        })
    }
}