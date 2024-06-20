package com.example.kotlinapiter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapiter.model.Feature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var earthquakesList = mutableListOf<Feature>()
    val job = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recEarthquakes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter
        getListEarthquakes()
    }

    private fun getListEarthquakes() {
        CoroutineScope(Dispatchers.IO + job).launch {
            val call = getRetrofit().create(ApiService::class.java).getSignificantQuakesByMonth()
            val response = call.body()
            runOnUiThread {
                if (call.isSuccessful) {

                    val responseQuakes = response?.features ?: emptyList()
                    earthquakesList.clear()
                    earthquakesList.addAll(responseQuakes)
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_EARTHQUAKES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val URL_EARTHQUAKES = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"
    }
}


