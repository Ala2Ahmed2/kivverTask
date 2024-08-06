package com.example.citysearch.data.repository

import android.content.Context
import com.example.citysearch.data.model.City
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private var cities: List<City> = emptyList()

    init {
        loadCitiesFromAsset()
    }

    private fun loadCitiesFromAsset() {
        val json = loadJSONFromAsset()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val adapter = moshi.adapter<List<City>>(type)
        cities = adapter.fromJson(json) ?: emptyList()
    }

    private fun loadJSONFromAsset(): String {
        return context.assets.open("cities.json").bufferedReader().use { it.readText() }
    }


    fun filterCities(prefix: String): List<City> {
        return cities.filter { it.name.startsWith(prefix, ignoreCase = true) }
    }

    fun updateCities(json: String) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val adapter = moshi.adapter<List<City>>(type)
        cities = adapter.fromJson(json) ?: emptyList()
    }
}
