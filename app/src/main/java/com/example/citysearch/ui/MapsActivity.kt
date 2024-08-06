package com.example.citysearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.citysearch.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, getSharedPreferences(
            "osmdroid", MODE_PRIVATE))

        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.map)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(10.0)

        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        if (latitude != 0.0 && longitude != 0.0) {
            val location = GeoPoint(latitude, longitude)
            mapController.setCenter(location)
            val marker = Marker(mapView)
            marker.position = location
            marker.title = "City Location"
            mapView.overlays.add(marker)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
