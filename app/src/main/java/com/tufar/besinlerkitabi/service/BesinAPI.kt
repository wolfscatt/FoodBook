package com.tufar.besinlerkitabi.service

import com.tufar.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {
    // Get isteği

    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // https://raw.githubusercontent.com/wolfscatt/BTK20-JSONVeriSeti/master/besinler.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("wolfscatt/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin(): Single<List<Besin>>



}