package net.jgarcia.internationalbusiness.api

import net.jgarcia.internationalbusiness.data.Rates
import net.jgarcia.internationalbusiness.data.Transaction
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApi {


    companion object{
        const val BASE_URL = "http://quiet-stone-2094.herokuapp.com/"
    }

//    private fun getRetrofit(): Retrofit {
//        return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//    }

    @GET("rates.json")
    suspend fun getRates(
    ): Response<List<Rates>>

    @GET("transactions.json")
    suspend fun getTransactions(
    ): List<Transaction>


}