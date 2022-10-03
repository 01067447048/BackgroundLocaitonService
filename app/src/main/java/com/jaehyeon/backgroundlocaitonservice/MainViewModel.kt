package com.jaehyeon.backgroundlocaitonservice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Created by Jaehyeon on 2022/10/03.
 */
class MainViewModel: ViewModel() {

    var lat by mutableStateOf("")
        private set

    var lon by mutableStateOf("")
        private set

    fun setLatLon(lat: String, lon: String) {
        this.lat = lat
        this.lon = lon
    }
}