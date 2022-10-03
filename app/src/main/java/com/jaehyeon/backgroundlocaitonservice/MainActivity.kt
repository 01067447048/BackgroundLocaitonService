package com.jaehyeon.backgroundlocaitonservice

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.jaehyeon.backgroundlocaitonservice.ui.theme.BackgroundLocaitonServiceTheme

class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )

        setContent {
            BackgroundLocaitonServiceTheme {
                
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Button(onClick = {
                            Intent(applicationContext, LocationService::class.java).apply {
                                action = LocationService.ACTION_START
                                startService(this)
                            }
                        }) {
                            Text(text = "Start")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            Intent(applicationContext, LocationService::class.java).apply {
                                action = LocationService.ACTION_STOP
                                startService(this)
                            }
                        }) {
                            Text(text = "Stop")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                fontSize = 16.sp,
                                text = "lat : ${viewModel.lat}",
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                fontSize = 16.sp,
                                text = "lon : ${viewModel.lon}"
                            )
                        }

                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            viewModel.setLatLon(it.getStringExtra("lat") ?: "0", it.getStringExtra("lon") ?: "0")
        }
    }
}

