package com.example.citysearch.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.citysearch.data.model.Coord

@BindingAdapter("formattedCoordinates")
fun setFormattedCoordinates(view: TextView, coord: Coord?) {
    coord?.let {
        view.text = "${it.lat}, ${it.lon}"
    } ?: run {
        view.text = ""
    }
}
