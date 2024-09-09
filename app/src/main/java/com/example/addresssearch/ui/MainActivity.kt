package com.example.addresssearch.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.addresssearch.R
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.addresssearch.api.HereApiService
import com.example.addresssearch.data.HereRepository
import com.example.addresssearch.data.HereViewModelFactory
import com.example.addresssearch.viewmodel.HereViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var searchBar: EditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var close: ImageView

    private lateinit var adapter: PlacesAdapter
    private lateinit var viewModel: HereViewModel

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://autosuggest.search.hereapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Khởi tạo HereApiService
        val apiService = retrofit.create(HereApiService::class.java)

        // Khởi tạo HereRepository với HereApiService
        val repository = HereRepository(apiService)

        // Khởi tạo ViewModel với Factory
        viewModel = ViewModelProvider(this, HereViewModelFactory(repository))
            .get(HereViewModel::class.java)

        // Khởi tạo UI components
        searchBar = findViewById(R.id.searchBar)
        recyclerView = findViewById(R.id.recyclerView)
        close = findViewById(R.id.close)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PlacesAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.cities.observe(this) { places ->
            places?.let { adapter.updatePlaces(it) }
        }

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    if (query.isNotEmpty()) {
                        recyclerView.visibility = View.VISIBLE
                        viewModel.searchCities(query, "rzP4m_J55DAxdFOa0X5JkCLiq4r5KB3zU2sV9mu5LSY")
                        adapter.updateSearchText(query)
                        close.visibility = View.VISIBLE
                    } else {
                        viewModel.clearPlaces()
                        recyclerView.visibility = View.GONE
                        adapter.updateSearchText("")
                        close.visibility = View.GONE
                    }
                }
                handler.postDelayed(searchRunnable!!, 1000)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        close.setOnClickListener {
            viewModel.clearPlaces()
            searchBar.text.clear()
            recyclerView.visibility = View.GONE

        }

    }
}
