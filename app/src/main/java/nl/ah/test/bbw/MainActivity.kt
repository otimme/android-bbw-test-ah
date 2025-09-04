package nl.ah.test.bbw

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativeShorts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ScreenContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "BlueBillyWig Testing Grounds",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Shorts",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        BbwShorts()
        Text(
            text = "Full screen video activity",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                context.startActivity(Intent(context, BlueBillyWigVideoActivity::class.java))
            }
        ) {
            Text(text = "Play video")
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, BlueBillyWigVideoActivityFullScreen::class.java))
            }
        ) {
            Text(text = "Play video Full Screen")
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, BlueBillyWigVideoActivityModal::class.java))
            }
        ) {
            Text(text = "Play video Modal")
        }
        Button(
            onClick = {
                val playerView = BBNativePlayer.createModalPlayerView(
                    activity = context as AppCompatActivity,
                    jsonUrl = "https://allerhande.bbvms.com/p/home_app_playout_nl/c/6177239.json"
                )
            }
        ) {
            Text(text = "Play video Modal Directly")
        }
    }
}

@Composable
private fun BbwShorts() {
    AndroidView(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        factory = { context ->
            BBNativeShorts.createShortsView(
                context = context,
                jsonUrl = "https://allerhande.bbvms.com/sh/128.json",
                options = mapOf("displayFormat" to "list"),
            )
        }
    )
}