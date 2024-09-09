package com.example.addresssearch.api

import com.example.addresssearch.data.PlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HereApiService {
    @GET("v1/autosuggest")
    suspend fun getVietnamCities(
        @Query("at") at: String, // Tọa độ địa lý
        @Query("limit") limit: Int, // Số lượng kết quả tối đa
        @Query("lang") lang: String, // Ngôn ngữ
        @Query("q") query: String, // Từ khóa tìm kiếm
        @Query("apiKey") apiKey: String // Khóa API
    ): Response<PlaceResponse>
}

