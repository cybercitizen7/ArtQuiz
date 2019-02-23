package com.dk.artquiz.network

import com.dk.artquiz.model.ArtworkModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ArtworkApi {
    @GET("artworks")
    fun getArtwork(@Query("artist_id") artistId: String, @Header("X-Xapp-Token") authHeader: String): Observable<ArtworkModel>
}