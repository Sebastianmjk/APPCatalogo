package com.example.appcatalogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcatalogo.apiJuegos.ApiService
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
                newRequestBuilder.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0NDE5NjM1LCJpYXQiOjE3MTQ0MTYwMzUsImp0aSI6Ijk3NjY2ZDM5YzU1MTQ4NWM4NDE3NTRmNmZkNWI3ZTBhIiwidXNlcl9pZCI6N30.xu2CEI488YzsBCRMR3Yqfy0JuUmjtxxdnNu3YGQoAZ0")
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
            val call = getRetrofit().create(ApiService::class.java).getGame("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0NDE5NjM1LCJpYXQiOjE3MTQ0MTYwMzUsImp0aSI6Ijk3NjY2ZDM5YzU1MTQ4NWM4NDE3NTRmNmZkNWI3ZTBhIiwidXNlcl9pZCI6N30.xu2CEI488YzsBCRMR3Yqfy0JuUmjtxxdnNu3YGQoAZ0")
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