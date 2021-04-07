package net.jgarcia.internationalbusiness.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object Prefs  {

    lateinit var preferences: SharedPreferences

    val PREFS_NAME = "com.ejemplo.sharedpreferences"


    fun with(application: Application) {
        preferences = application.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE)
    }

    val RATES_KEY = "shared_rates"


    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }
}