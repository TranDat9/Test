package com.example.addresssearch.data

import android.util.Log
import com.example.addresssearch.api.HereApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
class HereRepository(private val service: HereApiService) {

    suspend fun getVietnamCities(query: String,apiKey: String): List<Place>? {
        val at = "21.028511,105.804817"
        val limit = 10
        val lang = "vi"
       // val query = "thành phố"

        return try {
            val response = service.getVietnamCities(at, limit, lang, query, apiKey)
            if (response.isSuccessful) {
                response.body()?.items
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}
