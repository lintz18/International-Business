package net.jgarcia.internationalbusiness

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.jgarcia.internationalbusiness.utils.Prefs

@HiltAndroidApp
class InternationalBusinessApplication: Application() {

    companion object {
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        Prefs.with(this)
    }
}