package com.example.decomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.decomposesample.presentation.root.component.DefaultRootComponent
import com.example.decomposesample.ui.content.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootContent(component = DefaultRootComponent(
                componentContext = defaultComponentContext()
            )
            )
        }
    }
}