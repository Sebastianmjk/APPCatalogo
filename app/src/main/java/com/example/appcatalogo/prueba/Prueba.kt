package com.example.appcatalogo.prueba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcatalogo.apiConection.apiUsuario.ApiService
import com.example.appcatalogo.databinding.ActivityPruebaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Prueba : AppCompatActivity() {

    private lateinit var binding: ActivityPruebaBinding
    private lateinit var adapter: GameAdapter
    private lateinit var gameImage: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        loadGame()

    }



    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequestBuilder = originalRequest.newBuilder()
                newRequestBuilder.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0NTAwODQzLCJpYXQiOjE3MTQ0OTcyNDMsImp0aSI6IjUyMzk5M2RlY2EwZDQyYTZiYWY1YjI0NjgxYWZiMTExIiwidXNlcl9pZCI6M30.03BGf1v7waMYmZodLxnpO9yMC7qFe8to0KUoze16J_M")
                val newRequest = newRequestBuilder.build()
                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://apicatalogojuegos.onrender.com/GameVault/Usuario/profile/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    private fun loadGame(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getGame("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0NTAwODQzLCJpYXQiOjE3MTQ0OTcyNDMsImp0aSI6IjUyMzk5M2RlY2EwZDQyYTZiYWY1YjI0NjgxYWZiMTExIiwidXNlcl9pZCI6M30.03BGf1v7waMYmZodLxnpO9yMC7qFe8to0KUoze16J_M")
            val cuerpo = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    val image = cuerpo?.Imagen
                    if (image != null) {
                        gameImage = image
                        adapter = GameAdapter(gameImage) // Pasamos la única imagen al adaptador
                        binding.rvGame.adapter = adapter
                    }
                }else {
                    showError()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvGame.layoutManager = LinearLayoutManager(this)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}