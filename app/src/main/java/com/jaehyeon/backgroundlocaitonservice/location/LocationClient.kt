package com.jaehyeon.backgroundlocaitonservice.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jaehyeon on 2022/10/03.
 */
interface LocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>

    class LocationException(message: String): Exception()
}