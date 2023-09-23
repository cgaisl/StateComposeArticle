package at.cgaisl.statecomposearticle.android

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

private suspend fun loadUsernameFromNetwork(): String {
    // pretend we're loading the username from a network
    delay(10)
    return "Christian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneDialerScreenBaseline() {
    val username: String by flow { emit(loadUsernameFromNetwork()) }.collectAsState(initial = "")
    var phoneNumber by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Hello, $username"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                onValueChange = {
                    // simple input validation
                    if (it.length <= 12) {
                        phoneNumber = it
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = dialPhoneNumber(phoneNumber),
            ) {
                Text("Call")
            }
        }
    }
}

@Composable
private fun dialPhoneNumber(phoneNumber: String): () -> Unit =
    with(LocalContext.current) {
        { startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))) }
    }