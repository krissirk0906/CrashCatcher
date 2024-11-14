package com.junenine.crashcatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.junenine.crashcatcher.ui.theme.CrashCatcherTheme
import kotlinx.coroutines.launch

class RestartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coroutine = rememberCoroutineScope()
            CrashCatcherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement =  Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(
                            name = "Restart activity",
                            modifier = Modifier.padding(innerPadding)
                        )

                        Button(
                            onClick = {
                                throw RuntimeException("Test Crash")
                            }
                        ) {
                            Text(text = "Crash!")
                        }
                        Button(
                            onClick = {
                                coroutine.launch {
                                    throw RuntimeException("Test bg Crash")
                                }
                            }
                        ) {
                            Text(text = "Bg Crash!")
                        }
                    }

                }
            }
        }
    }
}