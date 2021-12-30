package com.example.project2

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.example.project2.api.ApiRequest
import com.example.project2.api.BASE_URL
import com.example.project2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit



class MainActivity : AppCompatActivity() {

    private var breedText: String = ""
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        backgroundAnimation()

        makeApiRequest()

        binding.ivRandomDog.setOnClickListener {
            makeApiRequest()
        }
    }

    private fun backgroundAnimation() {
        val animatonDrawable: AnimationDrawable = binding.rlLayout.background as AnimationDrawable
        animatonDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getRandomDog()
                Log.d("Main", "Size: ${response.status}")

                withContext(Dispatchers.Main) {
                    Glide.with(applicationContext).load(response.message).into(binding.ivRandomDog)
                }

            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }


            findViewById<Button>(R.id.btnBreedImg).setOnClickListener {
                breedText = findViewById<EditText>(R.id.etBreed).text.toString()
                findViewById<EditText>(R.id.etBreed).setText("")
            }

        }
    }
}
