package com.example.keystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.keystore.ui.theme.KeyStoreTheme
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManager = CryptoManager()
         setContent {
            KeyStoreTheme {
            var messageToEncrypt by remember {
                mutableStateOf("")
            }
                var messageToDecrypt by remember {
                    mutableStateOf("")
                }
                Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
                    TextField(value = messageToEncrypt, onValueChange = {messageToEncrypt = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {Text(text = "Encrypt string")}

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = {
                            val bytes = messageToEncrypt.encodeToByteArray()
                            val file = File(filesDir, "secret.txt")
                            if (!file.exists()){
                                file.createNewFile()
                            }
                            val fos =  FileOutputStream(file)
                            messageToDecrypt = cryptoManager.encrypt(
                                bytes = bytes,
                                outputStream = fos
                            ).decodeToString()
                        }) {
                            Text(text = "Encrypt")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val file = File(filesDir, "secret.txt")
                            messageToDecrypt = cryptoManager.decrypt(
                                inputStream = fileImputStream(file)
                            ).decodeToString()
                        }) {
                            Text(text = "Decrypt")
                        }
                    }
                    Text(text = messageToDecrypt)
                }
            }
        }
    }
}

