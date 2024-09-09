package com.example.addresssearch.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.addresssearch.R
import com.example.addresssearch.data.Place

class PlacesAdapter(private var places: MutableList<Place>) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    private var searchText: String = ""

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeTitle: TextView = itemView.findViewById(R.id.placeTitle)
        val placeAddress: TextView = itemView.findViewById(R.id.placeAddress)
        val directive: ImageView = itemView.findViewById(R.id.directive)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.placeTitle.text = highlightText(place.title, searchText)
        holder.placeAddress.text = highlightText(place.address.label, searchText)
        holder.directive.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(place.address.label)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(holder.itemView.context.packageManager) != null) {
                holder.itemView.context.startActivity(mapIntent)
            }
        }
    }

    override fun getItemCount(): Int = places.size

    fun updatePlaces(newPlaces: List<Place>) {
        places.clear()
        places.addAll(newPlaces)
        notifyDataSetChanged()
    }

    fun updateSearchText(newSearchText: String) {
        searchText = newSearchText
        notifyDataSetChanged()
    }

    private fun highlightText(text: String, searchText: String): Spannable {
        val spannable = SpannableString(text)
        val start = text.lowercase().indexOf(searchText.lowercase())
        if (start >= 0) {
            val end = start + searchText.length
            spannable.setSpan(
                BackgroundColorSpan(Color.YELLOW),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
}