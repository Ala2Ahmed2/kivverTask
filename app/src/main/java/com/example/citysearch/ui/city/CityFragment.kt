package com.example.citysearch.ui.city

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citysearch.databinding.FragmentCityBinding
import com.example.citysearch.ui.city.adapter.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

    private val cityViewModel: CityViewModel by viewModels()
    private lateinit var binding: FragmentCityBinding
    private val cityAdapter = CityAdapter(emptyList()) { city ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${city.coord.lat},${city.coord.lon}"))
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
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
            Log.d("CityFragment", "Loading state: $isLoading")
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
