package com.example.decomposesample.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.decomposesample.presentation.root.component.DefaultRootComponent
import com.example.decomposesample.presentation.root.component.RootComponent
import com.example.decomposesample.ui.theme.DecomposeSampleTheme

@Composable
fun RootContent(
    component: DefaultRootComponent
) {
    DecomposeSampleTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            content = {
                Children(
                    stack = component.stack,
                    content = {
                        when (val instance = it.instance) {

                            is RootComponent.Child.AddContact -> {
                                AddContact(component = instance.component)
                            }

                            is RootComponent.Child.ContactList -> {
                                Contacts(component = instance.component)
                            }

                            is RootComponent.Child.EditContact -> {
                                EditContact(component = instance.component)
                            }
                        }
                    }
                )
            }
        )
    }
}