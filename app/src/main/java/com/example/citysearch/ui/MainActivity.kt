package com.example.citysearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.citysearch.R
import com.example.citysearch.ui.city.CityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CityFragment())
                .commit()
        }
    }
}
