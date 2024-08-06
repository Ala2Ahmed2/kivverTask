package com.example.citysearch.ui.city

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citysearch.databinding.FragmentCityBinding
import com.example.citysearch.ui.MapsActivity
import com.example.citysearch.ui.city.adapter.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

    private val cityViewModel: CityViewModel by viewModels()
    private lateinit var binding: FragmentCityBinding
    private val cityAdapter = CityAdapter(emptyList()) { city ->

        val intent = Intent(requireContext(), MapsActivity::class.java).apply {
            putExtra("LATITUDE", city.coord.lat)
            putExtra("LONGITUDE", city.coord.lon)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        binding.cityViewModel = cityViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = cityAdapter

        cityViewModel.cities.observe(viewLifecycleOwner) { cities ->
            cityAdapter.updateCities(cities)
        }

        cityViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.searchInput.addTextChangedListener { text ->
            cityViewModel.filterCities(text.toString())
        }

        cityViewModel.loadCities(loadJSONFromAsset())
    }

    private fun loadJSONFromAsset(): String {
        return requireContext().assets.open("cities.json").bufferedReader().use { it.readText() }
    }
}
