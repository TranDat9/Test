package com.example.addresssearch.data


data class PlaceResponse(
    val items: List<Place>
)

data class Place(
    val title: String,
    val address: Address
)

data class Address(
    val label: String
)
