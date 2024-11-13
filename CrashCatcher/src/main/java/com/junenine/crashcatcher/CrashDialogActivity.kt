package com.junenine.crashcatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class CrashDialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullScreenCrashDialog(
                modifier = Modifier.fillMaxSize(),
                onRestart = {
                    this.finish()
                    CrashCallback.onRestartActivity.invoke()
                }
            )
        }
    }
}

@Composable
fun FullScreenCrashDialog(modifier: Modifier = Modifier, onRestart: () -> Unit) {
    Column(
        modifier = modifier
            .background(
                color = Color.White
            ), verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.mark
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(
                    Alignment.CenterHorizontally
                ),
            contentScale = ContentScale.Fit
        )
        Spacer(
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Something went wrong",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(
            modifier = Modifier.size(10.dp)
        )

        Text(
            text = "We're working on the problem right away.\n Please click refresh app to try again",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
        Spacer(
            modifier = Modifier.size(20.dp)
        )
        Button(
            onClick = onRestart,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(
                8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "Refresh App",
                style = TextStyle(
                    fontWeight = FontWeight.Normal
                ),
            )
        }
    }
}

@Preview
@Composable
fun FullScreenCrashDialogPreview() {
    FullScreenCrashDialog(onRestart = {})
}
