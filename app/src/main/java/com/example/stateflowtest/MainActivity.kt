package com.example.stateflowtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import com.example.stateflowtest.ui.theme.StateflowTestTheme
import com.example.stateflowtest.ui.components.Radio

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateflowTestTheme {
                val uiState = mainViewModel.state.collectAsState(initial = UIState())

                Surface(color = MaterialTheme.colors.background) {
                    Column() {
                        uiState.value.articles.map {
                            Column() {
                                Text(text = it.title)
                                // Radio button is subscribed to it's value via state
                                // Whenever it's pressed, handlePressed changes it's value in state
                                // In theory, this should trigger a re-compose, but it does not.
                                Radio(
                                    article = it,
                                    onClick = mainViewModel::handlePressed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
